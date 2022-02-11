package edu.epam.entities;

import java.util.Objects;

public abstract class Person {
    private int id;
    private String name;
    private String surname;
    private String mail;
    private String phoneNumber;
    private boolean isAdmin;
    private String password;
    private String pictureName;
    private String picturePath;

    public Person() {
    }

    public Person(int id, String mail, String name, String surname, String password, String phoneNumber, String pictureName, String picturePath, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.password = password;
        this.pictureName = pictureName;
        this.picturePath = picturePath;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return mail.equals(person.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
