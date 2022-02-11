package edu.epam.entities;

import java.util.Objects;

public class Book {
    private int ID;
    private String name;
    private String author;
    private String fileName;
    private String filePath;
    private String pictureName;
    private String picturePath;
    private String suggestedBy;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Book() {}

    public Book(int ID, String name, String author, String suggestedBy) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.suggestedBy = suggestedBy;
    }

    public Book(String name, String author, String fileName, String filePath, String pictureName, String picturePath, String suggestedBy) {
        this.name = name;
        this.author = author;
        this.fileName = fileName;
        this.filePath = filePath;
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.suggestedBy = suggestedBy;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getSuggestedBy() {
        return suggestedBy;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSuggestedBy(String suggestedBy) {
        this.suggestedBy = suggestedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return name.equals(book.name) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}