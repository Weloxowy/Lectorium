package org.example;

import javafx.scene.image.Image;

public class User {
    String imie;
    String nazwisko;
    int id;
    String czy_admin;
    Image image;

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
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
}
