package edu.epam.dao;

import edu.epam.constants.SQL;
import edu.epam.entities.User;
import edu.epam.exceptions.DatabaseException;
import edu.epam.exceptions.DuplicateRecordException;
import edu.epam.util.JDBCDataSource;
import edu.epam.util.SaltedMD5Hashing;
import org.apache.log4j.Logger;
import java.sql.*;

public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(BookDAO.class);
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static User user;
    private static ResultSet resultSet;

    public static User generateUser(ResultSet resultSet) {
        user = new User();
        try {
            user.setID(resultSet.getInt("user_id"));
            user.setMail(resultSet.getString("email"));
            user.setName(resultSet.getString("first_name"));
            user.setSurname(resultSet.getString("last_name"));
            user.setPassword(resultSet.getString("password"));
            user.setPhoneNumber(resultSet.getString("phone"));
            user.setPictureName(resultSet.getString("picture_name"));
            user.setPicturePath(resultSet.getString("picture_path"));
            user.setAdmin(resultSet.getBoolean("is_admin"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    public static User checkLogin(String email, String password) throws DatabaseException {
        connection = null;
        user = null;
        user = findByLogin(email);
        if (user != null) {
            String hashedPass = user.getPassword();
            String[] hashedPassSplitted = hashedPass.split(":");
            if (hashedPassSplitted[1].equals(SaltedMD5Hashing.getSecurePassword(password, hashedPassSplitted[0])))
                return user;
        }
        return null;
    }

    public static void registerUser(User user) throws DatabaseException, DuplicateRecordException {
        connection = null;
        User existingUser = findByLogin(user.getMail());
        if (existingUser != null)
            throw new DuplicateRecordException("Login email already exists");

        try {
            connection = JDBCDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL.REGISTER_USER);

            preparedStatement.setString(1,user.getMail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                try {
                    connection.rollback();
                } catch (Exception ex) {
                    LOGGER.error("rollback exception");
                }
            throw new DatabaseException(e.getMessage());
        } finally{
                JDBCDataSource.closeConnection(connection);
            }
    }

    public static User findByLogin(String login) throws DatabaseException {
        connection = null;
        User user = null;

        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.CHECK_USER);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                user = generateUser(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return user;
    }

    public static void updateUser(User user) throws DatabaseException {
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL.UPDATE_USER);

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getMail());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            try {
                connection.rollback();
            } catch (Exception ex) {
                LOGGER.error("rollback exception");
            }
            throw new DatabaseException(e.getMessage());
        } finally{
            JDBCDataSource.closeConnection(connection);
        }
    }

}
