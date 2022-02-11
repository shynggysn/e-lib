package edu.epam.entities;

public class Admin extends Person{

    public Admin(int id, String mail, String name, String surname, String password, String phoneNumber, String pictureName, String picturePath, boolean isAdmin) {
        super(id, mail, name, surname, password, phoneNumber, pictureName, picturePath, true);
    }
}