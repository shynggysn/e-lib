package edu.epam.dao;

import edu.epam.constants.SQL;
import edu.epam.entities.Book;
import edu.epam.entities.BookOnSite;
import edu.epam.entities.Request;
import edu.epam.exceptions.DatabaseException;
import edu.epam.exceptions.DuplicateRecordException;
import edu.epam.util.JDBCDataSource;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    /*
    TODO
    5. admin sees suggested books +
    6. admin updates a book +
    7. admin deletes a book +
    8. user rates a book (simple update()? think about it, dry)
    9. user adds book to want to read +-
    10. user clicks have read button (update?)
    11. currently reading increases if user downloads a book +-
    12. show downloaded books by user (new table due to m to n rel-ship)
     */

    /* insertion into tables with many-to-many rel

    INSERT INTO persons (firstname,lastname) VALUES ('John','Doe');
    SET @person_id = LAST_INSERT_ID();

    INSERT IGNORE INTO properties (property) VALUES ('property_A');
    SET @property_id = LAST_INSERT_ID();
    INSERT INTO has_property (person_id,property_id) VALUES(@person_id, @property_id);
    */
    private static final Logger LOGGER = Logger.getLogger(BookDAO.class);
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static BookOnSite bookOnSite;
    private static Book book;
    private static ResultSet resultSet;
    private static List<Book> list;
    private static List<BookOnSite> onSiteList;

    public static BookOnSite generateBookOnSite(ResultSet resultSet) {
        bookOnSite = new BookOnSite();
        try {
            bookOnSite.setID(resultSet.getInt("book_id"));
            bookOnSite.setName(resultSet.getString("name"));
            bookOnSite.setAuthor(resultSet.getString("author"));
            bookOnSite.setFileName(resultSet.getString("file_name"));
            bookOnSite.setFilePath(resultSet.getString("file_path"));
            bookOnSite.setPictureName(resultSet.getString("picture_name"));
            bookOnSite.setPicturePath(resultSet.getString("picture_path"));
            bookOnSite.setCurrentlyReading(resultSet.getInt("currently_reading"));
            bookOnSite.setHaveRead(resultSet.getInt("have_read"));
            bookOnSite.setWantToRead(resultSet.getInt("want_to_read"));
            bookOnSite.setVotesNum(resultSet.getInt("votes_num"));
            bookOnSite.setVotesSum(resultSet.getInt("votes_sum"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return bookOnSite;
    }

    public static Book generateBook(ResultSet resultSet) {
        book = new Book();
        try {
            book.setID(resultSet.getInt("book_id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setFileName(resultSet.getString("file_name"));
            book.setFilePath(resultSet.getString("file_path"));
            book.setPictureName(resultSet.getString("picture_name"));
            book.setPicturePath(resultSet.getString("picture_path"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return book;
    }

    public static BookOnSite getBookByID(int ID) throws DatabaseException {
        bookOnSite = null;
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL.BY_BOOK_ID);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                bookOnSite = generateBookOnSite(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return bookOnSite;
    }

    public static List getBooksByCategoryID(int ID) throws DatabaseException {
        connection = null;
        onSiteList = null;
        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.BOOKS_BY_CATEGORY_ID);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (onSiteList == null)
                    onSiteList = new ArrayList<>();
                bookOnSite = generateBookOnSite(resultSet);
                onSiteList.add(bookOnSite);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return onSiteList;
    }

    public static List list() throws DatabaseException {
        connection = null;
        onSiteList = null;

        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.BOOK_LIST);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (onSiteList == null)
                    onSiteList = new ArrayList<>();
                bookOnSite = generateBookOnSite(resultSet);
                onSiteList.add(bookOnSite);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return onSiteList;
    }

    public static List list(String query) throws DatabaseException {
        onSiteList = null;
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.BOOKS_BY_SEARCH);
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (onSiteList == null)
                    onSiteList = new ArrayList<>();
                bookOnSite = generateBookOnSite(resultSet);
                onSiteList.add(bookOnSite);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return onSiteList;
    }
    public static int create(Book book) throws DatabaseException, DuplicateRecordException {
        List<BookOnSite> existingBooks = list(book.getName());
        int status = 0;

        if (existingBooks != null)
            for (BookOnSite bookMatching: existingBooks)
                if (bookMatching.getName().equals(book.getName()) && bookMatching.getAuthor().equals(book.getAuthor()))
                    throw new DuplicateRecordException("Book already exists");

        try {
            connection = JDBCDataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL.CREATE_BOOK);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getFileName());
            preparedStatement.setString(4, book.getFilePath());
            preparedStatement.setString(5, book.getPictureName());
            preparedStatement.setString(6, book.getPicturePath());
            preparedStatement.setString(7, book.getSuggestedBy());
            if (book.getSuggestedBy() == null)
                preparedStatement.setInt(8, 1);
            else preparedStatement.setInt(8, 0);
            status = preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
           JDBCDataSource.closeConnection(connection);
        }
        return status;
    }

    public static List getUserBooks(int userID, boolean hasDownloaded) throws DatabaseException {
        onSiteList = null;
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = (hasDownloaded) ? connection.prepareStatement(SQL.MY_DOWNLOADS) : connection.prepareStatement(SQL.MY_WISH_LIST);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (onSiteList == null)
                    onSiteList = new ArrayList<>();
                bookOnSite = generateBookOnSite(resultSet);
                onSiteList.add(bookOnSite);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return onSiteList;
    }

    public static List getSuggestedBooks() throws DatabaseException {
        list = null;
        connection = null;
        try {
            connection = JDBCDataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL.USER_BOOK_UPLOADS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (list == null)
                    list = new ArrayList<>();
                book = generateBook(resultSet);
                list.add(book);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
        return list;
    }
    public static void acceptBook(int bookID,boolean accepted) throws DatabaseException {
        try {
            connection = JDBCDataSource.getConnection();
            connection.setAutoCommit(false);
            if (accepted)
                preparedStatement = connection.prepareStatement(SQL.ACCEPT_BOOK);
            else preparedStatement = connection.prepareStatement(SQL.DECLINE_BOOK);
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(connection);
        }
    }
}
