package edu.epam;

import edu.epam.entities.User;
import edu.epam.util.JDBCDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("user1");
        user.setSurname("user1");
        user.setMail("user1@gmail.com");
        user.setPassword("user123");
        user.setPhoneNumber("1231231231");

        String sql = new String("insert into user (email, first_name, last_name, password, phone) values (?,?,?,?,?)");
        Connection con = null;

        try{
            con = JDBCDataSource.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1,user.getMail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.executeUpdate();
            con.commit();
            pstmt.close();
        } catch (Exception e) {
//            try {
//                    con.rollback();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
//                }
//                throw new DatabaseException("Exception : Exception in add User");
                System.out.println(e.getMessage());

        } finally{
            JDBCDataSource.closeConnection(con);
        }

    }
}
