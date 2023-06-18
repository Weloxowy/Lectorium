package org.example;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa reprezentująca użytkownika.
 */
public class Users {
    SimpleStringProperty imie_katalog;
    SimpleStringProperty nazwisko_katalog;
    ReadOnlyIntegerWrapper id_katalog;
    SimpleStringProperty czy_admin_katalog;
    SimpleStringProperty czy_zablokowany;

    /**
     * Konstruktor klasy {@code Users}.
     *
     * @param imie_katalog      Imię użytkownika.
     * @param nazwisko_katalog  Nazwisko użytkownika.
     * @param id_katalog        ID użytkownika.
     * @param czy_admin_katalog Informacja czy użytkownik ma uprawnienia administratora.
     * @param czy_zablokowany   Informacja czy użytkownik jest zablokowany.
     */
    public Users(String imie_katalog, String nazwisko_katalog, int id_katalog, String czy_admin_katalog, String czy_zablokowany) {
        this.imie_katalog = new SimpleStringProperty(imie_katalog);
        this.nazwisko_katalog = new SimpleStringProperty(nazwisko_katalog);
        this.id_katalog = new ReadOnlyIntegerWrapper(id_katalog);
        this.czy_admin_katalog = new SimpleStringProperty(czy_admin_katalog);
        this.czy_zablokowany = new SimpleStringProperty(czy_zablokowany);
    }

    /**
     * Zwraca imię użytkownika.
     *
     * @return Imię użytkownika.
     */
    public String getImie_katalog() {
        return imie_katalog.get();
    }

    /**
     * Zwraca właściwość imienia użytkownika.
     *
     * @return Właściwość imienia użytkownika.
     */
    public SimpleStringProperty imie_katalogProperty() {
        return imie_katalog;
    }

    /**
     * Ustawia imię użytkownika.
     *
     * @param imie_katalog Imię użytkownika do ustawienia.
     */
    public void setImie_katalog(String imie_katalog) {
        this.imie_katalog.set(imie_katalog);
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return Nazwisko użytkownika.
     */
    public String getNazwisko_katalog() {
        return nazwisko_katalog.get();
    }

    /**
     * Zwraca właściwość nazwiska użytkownika.
     *
     * @return Właściwość nazwiska użytkownika.
     */
    public SimpleStringProperty nazwisko_katalogProperty() {
        return nazwisko_katalog;
    }

    /**
     * Ustawia nazwisko użytkownika.
     *
     * @param nazwisko_katalog Nazwisko użytkownika do ustawienia.
     */
    public void setNazwisko_katalog(String nazwisko_katalog) {
        this.nazwisko_katalog.set(nazwisko_katalog);
    }

    /**
     * Zwraca id użytkownika.
     *
     * @return Id użytkownika
     */
    public int getId_katalog() {
        return id_katalog.get();
    }

    /**
     * Zwraca właściwość identyfikatora użytkownika.
     *
     * @return Właściwość identyfikatora użytkownika.
     */
    public ReadOnlyIntegerWrapper id_katalogProperty() {
        return id_katalog;
    }

    /**
     * Ustawia id użytkownika.
     *
     * @param id_katalog Id użytkownika do ustawienia.
     */
    public void setId_katalog(int id_katalog) {
        this.id_katalog.set(id_katalog);
    }

    /**
     * Zwraca wartość "T" lub "N" odnośnie poziomu konta użytkownika.
     *
     * @return Wartość czy użytkownik jest administratorem czy zwykłym użytkownikiem.
     */
    public String getCzy_admin_katalog() {
        return czy_admin_katalog.get();
    }

    /**
     * Zwraca właściwość poziomu konta użytkownika.
     *
     * @return Właściwość poziomu konta użytkownika.
     */
    public SimpleStringProperty czy_admin_katalogProperty() {
        return czy_admin_katalog;
    }

    /**
     * Ustawia właściwość poziomu konta użytkownika.
     *
     * @param czy_admin_katalog Imię użytkownika do ustawienia.
     */
    public void setCzy_admin_katalog(String czy_admin_katalog) {
        this.czy_admin_katalog.set(czy_admin_katalog);
    }

    /**
     * Zwraca wartość "T" lub "N" odnośnie blokady konta użytkownika.
     *
     * @return Czy użytkownik jest zablokowany.
     */
    public String getCzy_zablokowany() {
        return czy_zablokowany.get();
    }

    /**
     * Zwraca właściwość stanu blokady konta użytkownika.
     *
     * @return Właściwość stanu blokady konta użytkownika.
     */
    public SimpleStringProperty czy_zablokowanyProperty() {
        return czy_zablokowany;
    }

    /**
     * Ustawia właściwość stanu blokady konta użytkownika.
     *
     * @param czy_zablokowany Właściwość stanu blokady konta użytkownika do ustawienia.
     */
    public void setCzy_zablokowany(String czy_zablokowany) {
        this.czy_zablokowany.set(czy_zablokowany);
    }
}
