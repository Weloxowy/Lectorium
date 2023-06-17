package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;

public class Users {
     SimpleStringProperty imie_katalog;
    SimpleStringProperty nazwisko_katalog;
    ReadOnlyIntegerWrapper id_katalog;
    SimpleStringProperty czy_admin_katalog;
    SimpleStringProperty czy_zablokowany;

    public Users(String imie_katalog, String nazwisko_katalog, int id_katalog, String czy_admin_katalog, String czy_zablokowany)
    {
        this.imie_katalog = new SimpleStringProperty(imie_katalog);
        this.nazwisko_katalog = new SimpleStringProperty(nazwisko_katalog);
        this.id_katalog = new ReadOnlyIntegerWrapper(id_katalog);
        this.czy_admin_katalog = new SimpleStringProperty(czy_admin_katalog);
        this.czy_zablokowany = new SimpleStringProperty(czy_zablokowany);
    }

    public String getImie_katalog() {
        return imie_katalog.get();
    }

    public SimpleStringProperty imie_katalogProperty() {
        return imie_katalog;
    }

    public void setImie_katalog(String imie_katalog) {
        this.imie_katalog.set(imie_katalog);
    }

    public String getNazwisko_katalog() {
        return nazwisko_katalog.get();
    }

    public SimpleStringProperty nazwisko_katalogProperty() {
        return nazwisko_katalog;
    }

    public void setNazwisko_katalog(String nazwisko_katalog) {
        this.nazwisko_katalog.set(nazwisko_katalog);
    }

    public int getId_katalog() {
        return id_katalog.get();
    }

    public ReadOnlyIntegerWrapper id_katalogProperty() {
        return id_katalog;
    }

    public void setId_katalog(int id_katalog) {
        this.id_katalog.set(id_katalog);
    }

    public String getCzy_admin_katalog() {
        return czy_admin_katalog.get();
    }

    public SimpleStringProperty czy_admin_katalogProperty() {
        return czy_admin_katalog;
    }

    public void setCzy_admin_katalog(String czy_admin_katalog) {
        this.czy_admin_katalog.set(czy_admin_katalog);
    }

    public String getCzy_zablokowany() {
        return czy_zablokowany.get();
    }

    public SimpleStringProperty czy_zablokowanyProperty() {
        return czy_zablokowany;
    }

    public void setCzy_zablokowany(String czy_zablokowany) {
        this.czy_zablokowany.set(czy_zablokowany);
    }
}
