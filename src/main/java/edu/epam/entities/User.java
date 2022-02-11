package edu.epam.entities;

public class User extends Person {

    public User(){super();}

    public User(int id, String mail, String name, String surname, String password, String phoneNumber, String pictureName, String picturePath, boolean isAdmin) {
        super(id, mail, name, surname, password, phoneNumber, pictureName, picturePath, isAdmin);
    }
}