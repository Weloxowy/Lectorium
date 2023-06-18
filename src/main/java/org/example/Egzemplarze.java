package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa {@code Egzemplarze} reprezentuje egzemplarze książek.
 * Zawiera informacje takie jak nazwa, lokalizacja, dostępność i data zwrotu.
 * Klasa implementuje własności (properties) dla poszczególnych pól.
 *
 */
public class Egzemplarze {
    ReadOnlyIntegerWrapper id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty lokalizacja;
    SimpleStringProperty czy_dostepne;
    SimpleStringProperty data_zwrotu;

    /**
     * Konstruktor tworzący obiekt klasy {@code Egzemplarze} i inicjalizujący jego pola.
     *
     * @param nazwa         nazwa egzemplarza
     * @param id_egzemplarze identyfikator egzemplarza
     * @param lokalizacja   lokalizacja egzemplarza
     * @param czy_dostepne  informacja o dostępności egzemplarza
     * @param data_zwrotu   data zwrotu egzemplarza
     */
    public Egzemplarze(String nazwa, Integer id_egzemplarze, String lokalizacja, String czy_dostepne, String data_zwrotu) {
        this.nazwa = new SimpleStringProperty(nazwa);
        this.id_egzemplarze = new ReadOnlyIntegerWrapper(id_egzemplarze);
        this.lokalizacja = new SimpleStringProperty(lokalizacja);
        this.czy_dostepne = new SimpleStringProperty(czy_dostepne);
        this.data_zwrotu = new SimpleStringProperty(data_zwrotu);
    }

    /**
     * Zwraca id egzemplarza.
     *
     * @return ID egzemplarza.
     */
    public int getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    /**
     * Zwraca właściwość id egzemplarza.
     *
     * @return Właściwość id egzemplarza
     */
    public ReadOnlyIntegerWrapper id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    /**
     * Ustawia id egzemplarza.
     *
     * @param id_egzemplarze Id egzemplarza do ustawienia.
     */
    public void setId_egzemplarze(int id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    /**
     * Zwraca nazwę egzemplarza.
     *
     * @return Nazwę egzemplarza
     */
    public String getNazwa() {
        return nazwa.get();
    }

    /**
     * Zwraca właściwość nazwy egzemplarza.
     *
     * @return Właściwość nazwy egzemplarza
     */
    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    /**
     * Ustawia nazwę egzemplarza
     *
     * @param nazwa Nazwa egzemplarza do ustawienia.
     */
    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    /**
     * Zwraca lokalizację egzemplarza.
     *
     * @return Lokalizację egzemplarza.
     */
    public String getLokalizacja() {
        return lokalizacja.get();
    }

    /**
     * Zwraca właściwość lokalizacji egzemplarza.
     *
     * @return Właściwość lokalizacji egzemplarza
     */
    public SimpleStringProperty lokalizacjaProperty() {
        return lokalizacja;
    }

    /**
     * Ustawia lokalizację użytkownika.
     *
     * @param lokalizacja Lokalizacja egzemplarza do ustawienia.
     */
    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja.set(lokalizacja);
    }

    /**
     * Zwraca dostępność egzemplarza.
     *
     * @return Dostępność egzemplarza
     */
    public String getCzy_dostepne() {
        return czy_dostepne.get();
    }

    /**
     * Zwraca właściwość dostępności egzemplarza.
     *
     * @return Właściwość dostępności egzemplarza
     */
    public SimpleStringProperty czy_dostepneProperty() {
        return czy_dostepne;
    }

    /**
     * Ustawia dostępność egzemplarza.
     *
     * @param czy_dostepne Dostępność egzemplarza do ustawienia.
     */
    public void setCzy_dostepne(String czy_dostepne) {
        this.czy_dostepne.set(czy_dostepne);
    }

    /**
     * Zwraca datę zwrotu egzemplarza.
     *
     * @return Datę zwrotu egzemplarza
     */
    public String getData_zwrotu() {
        return data_zwrotu.get();
    }

    /**
     * Zwraca właściwość daty zwrotu egzemplarza.
     *
     * @return Właściwość daty zwrotu egzemplarza
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
}