package org.example;

import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa reprezentująca wypożyczenie egzemplarza.
 */
public class Wypozyczenia {
    SimpleStringProperty id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty data_wypozyczenia;
    SimpleStringProperty data_zwrotu;
    SimpleStringProperty nazwa_autora;
    SimpleStringProperty ilosc_przedluzen;

    /**
     * Konstruktor klasy {@code Wypozyczenia}.
     *
     * @param id_egz           ID egzemplarza.
     * @param naz              Nazwa egzemplarza.
     * @param data_wypo        Data wypożyczenia.
     * @param data_zwr         Data zwrotu.
     * @param nazwa_autor      Nazwa autora egzemplarza.
     * @param ilosc_przedluzen Ilość przedłużeń wypożyczenia.
     */
    public Wypozyczenia(String id_egz, String naz, String data_wypo, String data_zwr, String nazwa_autor, String ilosc_przedluzen) {
        this.id_egzemplarze = new SimpleStringProperty(id_egz);
        this.nazwa = new SimpleStringProperty(naz);
        this.data_wypozyczenia = new SimpleStringProperty(data_wypo);
        this.data_zwrotu = new SimpleStringProperty(data_zwr);
        this.nazwa_autora = new SimpleStringProperty(nazwa_autor);
        this.ilosc_przedluzen = new SimpleStringProperty(ilosc_przedluzen);
    }


    /**
     * Zwraca ilość przedłużeń wypożyczenia.
     *
     * @return Ilość przedłużeń.
     */
    public String getIlosc_przedluzen() {
        return ilosc_przedluzen.get();
    }

    /**
     * Zwraca właściwość ilości przedłużeń wypożyczenia.
     *
     * @return Właściwość ilości przedłużeń.
     */
    public SimpleStringProperty ilosc_przedluzenProperty() {
        return ilosc_przedluzen;
    }

    /**
     * Ustawia ilość przedłużeń wypożyczenia.
     *
     * @param ilosc_przedluzen Ilość przedłużeń do ustawienia.
     */
    public void setIlosc_przedluzen(String ilosc_przedluzen) {
        this.ilosc_przedluzen.set(ilosc_przedluzen);
    }

    /**
     * Zwraca ID egzemplarza.
     *
     * @return ID egzemplarza.
     */
    public String getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    /**
     * Zwraca właściwość ID egzemplarza.
     *
     * @return Właściwość ID egzemplarza.
     */
    public SimpleStringProperty id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    /**
     * Ustawia ID egzemplarza.
     *
     * @param id_egzemplarze ID egzemplarza do ustawienia.
     */
    public void setId_egzemplarze(String id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    /**
     * Zwraca nazwę egzemplarza.
     *
     * @return Nazwa egzemplarza.
     */
    public String getNazwa() {
        return nazwa.get();
    }

    /**
     * Zwraca właściwość nazwy egzemplarza.
     *
     * @return Właściwość nazwy egzemplarza.
     */
    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    /**
     * Ustawia nazwę egzemplarza.
     *
     * @param nazwa Nazwa egzemplarza do ustawienia.
     */
    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    /**
     * Zwraca datę wypożyczenia egzemplarza.
     *
     * @return Data wypożyczenia egzemplarza.
     */
    public String getData_wypozyczenia() {
        return data_wypozyczenia.get();
    }

    /**
     * Zwraca właściwość daty wypożyczenia egzemplarza.
     *
     * @return Właściwość daty wypożyczenia autora egzemplarza.
     */
    public SimpleStringProperty data_wypozyczeniaProperty() {
        return data_wypozyczenia;
    }

    /**
     * Ustawia datę wypożyczenia egzemplarza.
     *
     * @param data_wypozyczenia Data wypożyczenia egzemplarza do ustawienia.
     */
    public void setData_wypozyczenia(String data_wypozyczenia) {
        this.data_wypozyczenia.set(data_wypozyczenia);
    }

    /**
     * Zwraca datę zwrotu egzemplarza.
     *
     * @return Nazwa autora egzemplarza.
     */
    public String getData_zwrotu() {
        return data_zwrotu.get();
    }

    /**
     * Zwraca właściwość daty zwrotu egzemplarza.
     *
     * @return Właściwość daty zwrotu autora egzemplarza.
     */
    public SimpleStringProperty data_zwrotuProperty() {
        return data_zwrotu;
    }

    /**
     * Ustawia datę zwrotu egzemplarza.
     *
     * @param data_zwrotu Data zwrotu egzemplarza do ustawienia.
     */
    public void setData_zwrotu(String data_zwrotu) {
        this.data_zwrotu.set(data_zwrotu);
    }

    /**
     * Zwraca nazwę autora egzemplarza.
     *
     * @return Nazwa autora egzemplarza.
     */
    public String getNazwa_autora() {
        return nazwa_autora.get();
    }

    /**
     * Zwraca właściwość nazwy autora egzemplarza.
     *
     * @return Właściwość nazwy autora egzemplarza.
     */
    public SimpleStringProperty nazwa_autoraProperty() {
        return nazwa_autora;
    }

    /**
     * Ustawia nazwę autora egzemplarza.
     *
     * @param nazwa_autora Nazwa autora egzemplarza do ustawienia.
     */
    public void setNazwa_autora(String nazwa_autora) {
        this.nazwa_autora.set(nazwa_autora);
    }
}
