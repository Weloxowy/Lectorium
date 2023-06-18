package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;


public class Egzemplarze {
    ReadOnlyIntegerWrapper id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty lokalizacja;
    SimpleStringProperty czy_dostepne;
    SimpleStringProperty data_zwrotu;

    // Konstruktor do inicjalizacji obiektu Egzemplarze
    public Egzemplarze(String nazwa, Integer id_egzemplarze, String lokalizacja, String czy_dostepne, String data_zwrotu) {
        this.nazwa = new SimpleStringProperty(nazwa);
        this.id_egzemplarze = new ReadOnlyIntegerWrapper(id_egzemplarze);
        this.lokalizacja = new SimpleStringProperty(lokalizacja);
        this.czy_dostepne = new SimpleStringProperty(czy_dostepne);
        this.data_zwrotu = new SimpleStringProperty(data_zwrotu);
    }

    // Getter dla id_egzemplarze
    public int getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    // Getter dla właściwości id_egzemplarze
    public ReadOnlyIntegerWrapper id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    // Setter dla id_egzemplarze
    public void setId_egzemplarze(int id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    // Getter dla nazwa
    public String getNazwa() {
        return nazwa.get();
    }

    // Getter dla właściwości nazwa
    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    // Setter dla nazwa
    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    // Getter dla lokalizacja
    public String getLokalizacja() {
        return lokalizacja.get();
    }

    // Getter dla właściwości lokalizacja
    public SimpleStringProperty lokalizacjaProperty() {
        return lokalizacja;
    }

    // Setter dla lokalizacja
    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja.set(lokalizacja);
    }

    // Getter dla czy_dostepne
    public String getCzy_dostepne() {
        return czy_dostepne.get();
    }

    // Getter dla właściwości czy_dostepne
    public SimpleStringProperty czy_dostepneProperty() {
        return czy_dostepne;
    }

    // Setter dla czy_dostepne
    public void setCzy_dostepne(String czy_dostepne) {
        this.czy_dostepne.set(czy_dostepne);
    }

    // Getter dla data_zwrotu
    public String getData_zwrotu() {
        return data_zwrotu.get();
    }

    // Getter dla właściwości data_zwrotu
    public SimpleStringProperty data_zwrotuProperty() {
        return data_zwrotu;
    }

    // Setter dla data_zwrotu
    public void setData_zwrotu(String data_zwrotu) {
        this.data_zwrotu.set(data_zwrotu);
    }
}