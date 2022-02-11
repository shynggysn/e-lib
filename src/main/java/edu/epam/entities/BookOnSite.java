package edu.epam.entities;

import java.sql.Blob;

public class BookOnSite extends Book{
    private int currentlyReading;
    private int wantToRead;
    private int haveRead;
    private int votesNum;
    private int votesSum;

    public BookOnSite(int ID, String name, String author, Blob book_content, Blob picture, String suggestedBy) {
        super(ID, name, author, suggestedBy);
    }

    public BookOnSite(int ID, String name, String author, Blob book_content, Blob picture, String suggestedBy, int currentlyReading, int wantToRead, int haveRead, int votesNum, int votesSum) {
        super(ID, name, author, suggestedBy);
        this.currentlyReading = currentlyReading;
        this.wantToRead = wantToRead;
        this.haveRead = haveRead;
        this.votesNum = votesNum;
        this.votesSum = votesSum;
    }

    public BookOnSite() {
        super();
    }

    public int getCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(int currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public int getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(int wantToRead) {
        this.wantToRead = wantToRead;
    }

    public int getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(int haveRead) {
        this.haveRead = haveRead;
    }

    public int getVotesNum() {
        return votesNum;
    }

    public void setVotesNum(int votesNum) {
        this.votesNum = votesNum;
    }

    public int getVotesSum() {
        return votesSum;
    }

    public void setVotesSum(int votesSum) {
        this.votesSum = votesSum;
    }

}