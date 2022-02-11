package edu.epam.entities;

import java.sql.Timestamp;

public class Comment extends UserOpinion{

    public Comment(String content, Timestamp date, int userId, int bookId) {
        super(content, date, userId, bookId);
    }

    public Comment() {
        super();
    }
}
