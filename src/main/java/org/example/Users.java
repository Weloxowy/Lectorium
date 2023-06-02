package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;

public class Users {
     SimpleStringProperty imie_katalog;
    SimpleStringProperty nazwisko_katalog;

    ReadOnlyIntegerWrapper id_katalog;
     SimpleStringProperty czy_admin_katalog;
    public Users(String imie, String nazwisko, int id, String czy_admin)
    {
        this.imie_katalog = new SimpleStringProperty(imie);
        this.nazwisko_katalog = new SimpleStringProperty(nazwisko);
        this.id_katalog = new ReadOnlyIntegerWrapper(id);
        this.czy_admin_katalog = new SimpleStringProperty(czy_admin);
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

}
