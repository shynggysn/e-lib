package edu.epam.constants;

public interface SQL {
    public String BY_BOOK_ID = "SELECT * FROM book WHERE book_id=?";
    public String BOOKS_BY_CATEGORY_ID = "select book.* from book inner join book_has_category on book.book_id = book_has_category.book_id where book.is_on_site = 1 and book_has_category.category_id = ?";
    public String BOOK_LIST = "select * from book where is_on_site = 1";
    public String BOOKS_BY_SEARCH = "select * from book where name like ? or author like ?";
    public String CHECK_USER = "SELECT * FROM user WHERE email = ?";
    public String REGISTER_USER = "insert into user (email, first_name, last_name, password, phone) values (?,?,?,?,?)";
    public String CREATE_BOOK = "INSERT INTO book (name, author, file_name, file_path, picture_name, picture_path, user_id, is_on_site) VALUES(?,?,?,?,?,?,?,?)";
    public String MY_DOWNLOADS = "select b.* from downloaded_and_wish_list d join book b on d.book_id = b.book_id where d.user_id = ? and d.has_downloaded = 1 and b.is_on_site = 1";
    public String MY_WISH_LIST = "select b.* from downloaded_and_wish_list d join book b on d.book_id = b.book_id where d.user_id = ? and d.has_downloaded = 0 and b.is_on_site = 1";
    public String USER_BOOK_UPLOADS = "select * from book where user_id is not null and is_on_site = 0";
    public String USER_BOOK_SUGGESTIONS = "select * from user_opinion where is_comment = 0";
    public String ADD_COMMENT = "insert into user_opinion(content, is_comment, create_time, user_id, book_id) values(?,?,?,?,?)";
    public String ADD_SUGGESTION = "insert into user_opinion(content, is_comment, create_time, user_id, book_id) values(?,?,?,?,?)";
    public String COMMENTS = "select uo.*,u.email from user_opinion uo join user u on uo.user_id = u.user_id where uo.book_id = ? and uo.is_comment =1";
    public String UPDATE_USER = "update user set first_name=?, last_name=?, password=?, phone=? where email=?";
    public String ACCEPT_BOOK = "update book set is_on_site = 1 where book_id = ?";
    public String DECLINE_BOOK = "delete from book where book_id = ?";
    public String ACCEPT_SUGGESTION = "delete from user_opinion where book_id = ? and user_id = ? and content = ? and is_comment = 0";

}
