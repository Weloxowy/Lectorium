package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class User {
    private static User instance;

    static String imie;
    static String nazwisko;
    static int id;
    static String czy_admin;
    static String czy_zablokowany;
    Image image;

    public static User getInstance() {
        if(instance==null)
            instance = new User();
        return instance;
    }



    public static String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public static String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCzy_admin() {
        return czy_admin;
    }

    public void setCzy_admin(String czy_admin) {
        this.czy_admin = czy_admin;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public String getCzy_zablokowany() {
        return czy_zablokowany;
    }

    public void setCzy_zablokowany(String czy_zablokowany) {
        User.czy_zablokowany = czy_zablokowany;
    }
}
