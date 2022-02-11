package edu.epam.entities;

public class Category {
    private int ID;
    private String categoryNameRU;
    private String categoryNameEN;

    public Category(int id, String ru, String en){
        ID = id;
        categoryNameRU = ru;
        categoryNameEN = en;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryNameRU() {
        return categoryNameRU;
    }

    public void setCategoryNameRU(String categoryNameRU) {
        this.categoryNameRU = categoryNameRU;
    }

    public String getCategoryNameEN() {
        return categoryNameEN;
    }

    public void setCategoryNameEN(String categoryNameEN) {
        this.categoryNameEN = categoryNameEN;
    }
}
