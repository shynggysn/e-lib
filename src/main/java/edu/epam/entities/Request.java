package edu.epam.entities;

import java.sql.Timestamp;

public class Request extends UserOpinion{

    public Request(String content, Timestamp date, int userId, int bookId) {
        super(content, date, userId, bookId);
    }
}