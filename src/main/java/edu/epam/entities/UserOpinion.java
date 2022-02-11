package edu.epam.entities;

import java.sql.Timestamp;

public abstract class UserOpinion {
    private int ID;
    private String content;
    private boolean isComment;
    private Timestamp date;
    private int userID;
    private int bookId;
    private String user_email;

    public UserOpinion(String content, Timestamp date, int userId, int bookId) {
        this.content = content;
        this.date = date;
        this.userID = userId;
        this.bookId = bookId;
    }

    public UserOpinion() {

    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUserId() { return userID;}

    public int getBookId() {
        return bookId;
    }

    public void setBookID(int bookID) {
        this.bookId = bookID;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setUserID(int userId) {
        this.userID = userId;
    }
}