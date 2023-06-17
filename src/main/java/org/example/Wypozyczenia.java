package org.example;

import javafx.beans.property.SimpleStringProperty;

public class Wypozyczenia {
// JAK USUWA SIE GETTERY I SETTERY (zeby usunac warningi) to potem nie wyswietlaja sie wypozyczenia
    SimpleStringProperty id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty data_wypozyczenia;
    SimpleStringProperty data_zwrotu;
    SimpleStringProperty nazwa_autora;
    SimpleStringProperty ilosc_przedluzen;

    public Wypozyczenia(String id_egz, String naz, String data_wypo, String data_zwr, String nazwa_autor, String ilosc_przedluzen)
    {
        this.id_egzemplarze = new SimpleStringProperty(id_egz);
        this.nazwa = new SimpleStringProperty(naz);
        this.data_wypozyczenia = new SimpleStringProperty(data_wypo);
        this.data_zwrotu = new SimpleStringProperty(data_zwr);
        this.nazwa_autora = new SimpleStringProperty(nazwa_autor);
        this.ilosc_przedluzen = new SimpleStringProperty(ilosc_przedluzen);
    }

    public String getIlosc_przedluzen() {
        return ilosc_przedluzen.get();
    }

    public SimpleStringProperty ilosc_przedluzenProperty() {
        return ilosc_przedluzen;
    }

    public void setIlosc_przedluzen(String ilosc_przedluzen) {
        this.ilosc_przedluzen.set(ilosc_przedluzen);
    }

    public String getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    public SimpleStringProperty id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    public void setId_egzemplarze(String id_egzemplarze) {
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

    public String getData_wypozyczenia() {
        return data_wypozyczenia.get();
    }

    public SimpleStringProperty data_wypozyczeniaProperty() {
        return data_wypozyczenia;
    }

    public void setData_wypozyczenia(String data_wypozyczenia) {
        this.data_wypozyczenia.set(data_wypozyczenia);
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

    public String getNazwa_autora() {
        return nazwa_autora.get();
    }

    public SimpleStringProperty nazwa_autoraProperty() {
        return nazwa_autora;
    }

    public void setNazwa_autora(String nazwa_autora) {
        this.nazwa_autora.set(nazwa_autora);
    }
}
