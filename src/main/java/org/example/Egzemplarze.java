package org.example;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;

public class Egzemplarze {
    ReadOnlyIntegerWrapper id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty lokalizacja;
    SimpleStringProperty czy_dostepne;
    SimpleStringProperty data_zwrotu;

    public Egzemplarze(String nazwa, Integer id_egzemplarze, String lokalizacja, String czy_dostepne, String data_zwrotu) {
        this.nazwa = new SimpleStringProperty(nazwa);
        this.id_egzemplarze = new ReadOnlyIntegerWrapper(id_egzemplarze);
        this.lokalizacja = new SimpleStringProperty(lokalizacja);
        this.czy_dostepne = new SimpleStringProperty(czy_dostepne);
        this.data_zwrotu = new SimpleStringProperty(data_zwrotu);
    }

    public int getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    public ReadOnlyIntegerWrapper id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    public void setId_egzemplarze(int id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getLokalizacja() {
        return lokalizacja.get();
    }

    public SimpleStringProperty lokalizacjaProperty() {
        return lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja.set(lokalizacja);
    }

    public String getCzy_dostepne() {
        return czy_dostepne.get();
    }

    public SimpleStringProperty czy_dostepneProperty() {
        return czy_dostepne;
    }

    public void setCzy_dostepne(String czy_dostepne) {
        this.czy_dostepne.set(czy_dostepne);
    }

    public String getData_zwrotu() {
        return data_zwrotu.get();
    }

    public SimpleStringProperty data_zwrotuProperty() {
        return data_zwrotu;
    }

    public void setData_zwrotu(String data_zwrotu) {
        this.data_zwrotu.set(data_zwrotu);
    }
}
