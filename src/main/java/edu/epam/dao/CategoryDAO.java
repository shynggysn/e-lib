package edu.epam.dao;

import edu.epam.entities.Category;
import edu.epam.entities.Comment;
import edu.epam.util.JDBCDataSource;
import edu.epam.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public static List<Category> getCategories() throws DatabaseException {
        StringBuffer sql = new StringBuffer("SELECT * FROM category");
        List<Category> categories = new ArrayList<>();
        Connection conn = null;

        try{
            conn = JDBCDataSource.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from category");
            while(rs.next())
                categories.add(new Category(rs.getInt(1),  rs.getString(2), rs.getString(3)));
            rs.close();
        } catch(Exception e){
            throw new DatabaseException("Exception : Exception in getting list of Categories");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return categories;
    }

    public static List<Category> getCategoryByBookID(int ID) throws DatabaseException{
        List<Category> categories = new ArrayList<>();
        String sql = "select category.* from category inner join book_has_category on category.category_id = book_has_category.category_id where book_has_category.book_id = ?";
        Connection conn = null;
        try{
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                categories.add(new Category(rs.getInt(1),rs.getString(2),rs.getString(3)));
        } catch (Exception e){
            throw new DatabaseException("Error in getting categories by book ID");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return categories;
    }
}
