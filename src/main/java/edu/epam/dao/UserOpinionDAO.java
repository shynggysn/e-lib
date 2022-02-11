package edu.epam.dao;

import edu.epam.constants.SQL;
import edu.epam.entities.*;
import edu.epam.exceptions.DatabaseException;
import edu.epam.util.JDBCDataSource;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserOpinionDAO {
    /* TODO
    1. user leaves a comment to a book
    2. admin can delete comments
    3. user edits his own comment
     */
    private static final Logger LOGGER = Logger.getLogger(UserOpinionDAO.class);
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static List<Request> requests;
    private static Request request;

    public static List<Comment> getCommentsByBookID(int ID) throws DatabaseException{
        List<Comment> comments = new ArrayList<>();
        Comment bean = null;
        Connection conn = null;
        try{
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL.COMMENTS);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                bean = new Comment();
                bean.setBookID(rs.getInt("book_id"));
                bean.setUserID(rs.getInt("user_id"));
                bean.setContent(rs.getString("content"));
                bean.setDate(rs.getTimestamp("create_time"));
                bean.setUser_email(rs.getString("email"));
                comments.add(bean);
            }
        } catch (Exception e){
            throw new DatabaseException("Error in getting comments by book ID");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return comments;
    }

    public static List getUserRequests() throws DatabaseException {
        requests = null;
        request = null;
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.USER_BOOK_SUGGESTIONS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (requests == null)
                    requests = new ArrayList<>();
                request = new Request(resultSet.getString("content"),resultSet.getTimestamp("create_time"),resultSet.getInt("user_id"), resultSet.getInt("book_id"));
                requests.add(request);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return requests;
    }
        public static void saveRequest(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try {
                int userID = Integer.parseInt(request.getParameter("userID"));
                int bookID = Integer.parseInt(request.getParameter("bookID"));
                int isComment = Integer.parseInt(request.getParameter("isComment"));
                connection = JDBCDataSource.getConnection();
                connection.setAutoCommit(false);
                if(isComment == 1) {
                    preparedStatement = connection.prepareStatement(SQL.ADD_COMMENT);
                    preparedStatement.setString(1, request.getParameter("comment"));
                    preparedStatement.setInt(2, 1);
                    preparedStatement.setTimestamp(3, timestamp);
                    preparedStatement.setInt(4, userID);
                    preparedStatement.setInt(5, bookID);
                    preparedStatement.executeUpdate();
                    connection.commit();
                    preparedStatement.close();

                    List<Comment> list = getCommentsByBookID(bookID);
                    PrintWriter out = response.getWriter();
                    for (Comment comment: list){
                        out.print("<p><strong>" + comment.getDate() + " - " + comment.getUser_email() + "</strong>: " + comment.getContent() + "</p>");
                    }
                }
                else{
                    preparedStatement = connection.prepareStatement(SQL.ADD_SUGGESTION);
                    preparedStatement.setString(1, request.getParameter("comment"));
                    preparedStatement.setInt(2, 0);
                    preparedStatement.setTimestamp(3, timestamp);
                    preparedStatement.setInt(4, userID);
                    preparedStatement.setInt(5, bookID);
                    preparedStatement.executeUpdate();
                    connection.commit();
                    preparedStatement.close();
                }

            } catch (SQLException | IOException e) {
                LOGGER.error(e.getMessage());
                throw new DatabaseException(e.getMessage());
            } finally {
                JDBCDataSource.closeConnection(connection);
            }
    }

    public static void deleteRequest(HttpServletRequest req) throws DatabaseException{
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL.ACCEPT_SUGGESTION);
            pstmt.setString(1,req.getParameter("bookID"));
            pstmt.setString(2, req.getParameter("userID"));
            pstmt.setString(3,req.getParameter("content"));
            pstmt.execute();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException("Exception : Exception in deleteRequest");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }
}
