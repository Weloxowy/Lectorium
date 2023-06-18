package org.example;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * Klasa reprezentująca katalog w systemie.
 */
public class Katalog {
    ReadOnlyIntegerWrapper id_katalog;
    SimpleStringProperty nazwa;
    SimpleStringProperty rok_wydania;
    SimpleStringProperty wydanie;
    SimpleStringProperty isbn;
    SimpleStringProperty jezyk;
    SimpleStringProperty nazwa_autora;
    SimpleStringProperty uwagi;
    SimpleStringProperty nazwa_wydawnictwa;
    SimpleStringProperty nazwa_gatunku;
    Image okladka;

    /**
     * Konstruktor inicjalizujący katalog na podstawie przekazanych argumentów.
     *
     * @param id_katalog         Identyfikator katalogu.
     * @param nazwa              Nazwa katalogu.
     * @param nazwa_autora       Nazwa autora.
     * @param rok_wydania        Rok wydania.
     * @param wydanie            Wydanie.
     * @param isbn               Numer ISBN.
     * @param jezyk              Język.
     * @param uwagi              Uwagi.
     * @param nazwa_wydawnictwa  Nazwa wydawnictwa.
     * @param nazwa_gatunku      Nazwa gatunku.
     */
    public Katalog(int id_katalog, String nazwa, String nazwa_autora, String rok_wydania, String wydanie, String isbn, String jezyk, String uwagi, String nazwa_wydawnictwa, String nazwa_gatunku) {
        this.id_katalog = new ReadOnlyIntegerWrapper(id_katalog);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.rok_wydania = new SimpleStringProperty(rok_wydania);
        this.wydanie = new SimpleStringProperty(wydanie);
        this.isbn = new SimpleStringProperty(isbn);
        this.jezyk = new SimpleStringProperty(jezyk);
        this.nazwa_autora = new SimpleStringProperty(nazwa_autora);
        this.uwagi = new SimpleStringProperty(uwagi);
        this.nazwa_wydawnictwa = new SimpleStringProperty(nazwa_wydawnictwa);
        this.nazwa_gatunku = new SimpleStringProperty(nazwa_gatunku);
    }

    /**
     * Zwraca właściwość id_katalog.
     *
     * @return Właściwość id_katalog.
     */
    public ReadOnlyIntegerWrapper id_katalogProperty() {
        return id_katalog;
    }

    /**
     * Zwraca wartość id_katalog.
     *
     * @return Wartość id_katalog.
     */
    public ReadOnlyIntegerProperty getId_katalog() {
        return id_katalog.getReadOnlyProperty();
    }

    /**
     * Ustawia wartość id_katalog.
     *
     * @param id_katalog Identyfikator katalogu do ustawienia.
     */
    public void setId_katalog(Integer id_katalog) {
        this.id_katalog.set(id_katalog);
    }

    /**
     * Zwraca nazwę katalogu.
     *
     * @return Nazwa katalogu.
     */
    public String getNazwa() {
        return nazwa.get();
    }

    /**
     * Zwraca właściwość nazwa.
     *
     * @return Właściwość nazwa.
     */
    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    /**
     * Ustawia nazwę katalogu.
     *
     * @param nazwa Nazwa katalogu do ustawienia.
     */
    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    /**
     * Zwraca rok wydania.
     *
     * @return Rok wydania.
     */
    public String getRok_wydania() {
        return rok_wydania.get();
    }

    /**
     * Zwraca właściwość rok_wydania.
     *
     * @return Właściwość rok_wydania.
     */
    public SimpleStringProperty rok_wydaniaProperty() {
        return rok_wydania;
    }

    /**
     * Ustawia rok wydania.
     *
     * @param rok_wydania Rok wydania do ustawienia.
     */
    public void setRok_wydania(String rok_wydania) {
        this.rok_wydania.set(rok_wydania);
    }

    /**
     * Zwraca wydanie.
     *
     * @return Wydanie.
     */
    public String getWydanie() {
        return wydanie.get();
    }

    /**
     * Zwraca właściwość wydanie.
     *
     * @return Właściwość wydanie.
     */
    public SimpleStringProperty wydanieProperty() {
        return wydanie;
    }

    /**
     * Ustawia wydanie.
     *
     * @param wydanie Wydanie do ustawienia.
     */
    public void setWydanie(String wydanie) {
        this.wydanie.set(wydanie);
    }

    /**
     * Zwraca numer ISBN.
     *
     * @return Numer ISBN.
     */
    public String getIsbn() {
        return isbn.get();
    }

    /**
     * Zwraca właściwość isbn.
     *
     * @return Właściwość isbn.
     */
    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    /**
     * Ustawia numer ISBN.
     *
     * @param isbn Numer ISBN do ustawienia.
     */
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    /**
     * Zwraca język.
     *
     * @return Język.
     */
    public String getJezyk() {
        return jezyk.get();
    }

    /**
     * Zwraca właściwość jezyk.
     *
     * @return Właściwość jezyk.
     */
    public SimpleStringProperty jezykProperty() {
        return jezyk;
    }

    /**
     * Ustawia język.
     *
     * @param jezyk Język do ustawienia.
     */
    public void setJezyk(String jezyk) {
        this.jezyk.set(jezyk);
    }

    /**
     * Zwraca nazwę autora.
     *
     * @return Nazwa autora.
     */
    public String getNazwa_autora() {
        return nazwa_autora.get();
    }

    /**
     * Zwraca właściwość nazwa_autora.
     *
     * @return Właściwość nazwa_autora.
     */
    public SimpleStringProperty nazwa_autoraProperty() {
        return nazwa_autora;
    }

    /**
     * Ustawia nazwę autora.
     *
     * @param nazwa_autora Nazwa autora do ustawienia.
     */
    public void setNazwa_autora(String nazwa_autora) {
        this.nazwa_autora.set(nazwa_autora);
    }

    /**
     * Zwraca uwagi.
     *
     * @return Uwagi.
     */
    public String getUwagi() {
        return uwagi.get();
    }

    /**
     * Zwraca właściwość uwagi.
     *
     * @return Właściwość uwagi.
     */
    public SimpleStringProperty uwagiProperty() {
        return uwagi;
    }

    /**
     * Ustawia uwagi.
     *
     * @param uwagi Uwagi do ustawienia.
     */
    public void setUwagi(String uwagi) {
        this.uwagi.set(uwagi);
    }

    /**
     * Zwraca okładkę.
     *
     * @return Okładka.
     */
    public Image okladkaProperty() {
        return okladka;
    }

    /**
     * Zwraca wartość okładki.
     *
     * @return Wartość okładki.
     */
    public Image getOkladka() {
        return okladka;
    }

    /**
     * Ustawia okładkę.
     *
     * @param okladka Okładka do ustawienia.
     */
    public void setOkladka(Image okladka) {
        this.okladka = okladka;
    }

    /**
     * Zwraca nazwę wydawnictwa.
     *
     * @return Nazwa wydawnictwa.
     */
    public String getNazwa_wydawnictwa() {
        return nazwa_wydawnictwa.get();
    }

    /**
     * Zwraca właściwość nazwa_wydawnictwa.
     *
     * @return Właściwość nazwa_wydawnictwa.
     */
    public SimpleStringProperty nazwa_wydawnictwaProperty() {
        return nazwa_wydawnictwa;
    }

    /**
     * Ustawia nazwę wydawnictwa.
     *
     * @param nazwa_wydawnictwa Nazwa wydawnictwa do ustawienia.
     */
    public void setNazwa_wydawnictwa(String nazwa_wydawnictwa) {
        this.nazwa_wydawnictwa.set(nazwa_wydawnictwa);
    }

    /**
     * Zwraca nazwę gatunku.
     *
     * @return Nazwa gatunku.
     */
    public String getNazwa_gatunku() {
        return nazwa_gatunku.get();
    }

    /**
     * Zwraca właściwość nazwa_gatunku.
     *
     * @return Właściwość nazwa_gatunku.
     */
    public SimpleStringProperty nazwa_gatunkuProperty() {
        return nazwa_gatunku;
    }

    /**
     * Ustawia nazwę gatunku.
     *
     * @param nazwa_gatunku Nazwa gatunku do ustawienia.
     */
    public void setNazwa_gatunku(String nazwa_gatunku) {
        this.nazwa_gatunku.set(nazwa_gatunku);
    }
}