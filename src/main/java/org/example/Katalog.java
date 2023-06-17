package org.example;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

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

    public Katalog(int id_katalog, String nazwa, String nazwa_autora, String rok_wydania, String wydanie, String isbn, String jezyk,String uwagi, String nazwa_wydawnictwa, String nazwa_gatunku) {
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
    public ReadOnlyIntegerWrapper id_katalogProperty() {
        return id_katalog;
    }

    public ReadOnlyIntegerProperty getId_katalog() {
        return id_katalog.getReadOnlyProperty();
    }

    public void setId_katalog(Integer id_katalog) {
        this.id_katalog.set(id_katalog);
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

    public String getRok_wydania() {
        return rok_wydania.get();
    }

    public SimpleStringProperty rok_wydaniaProperty() {
        return rok_wydania;
    }

    public void setRok_wydania(String rok_wydania) {
        this.rok_wydania.set(rok_wydania);
    }

    public String getWydanie() {
        return wydanie.get();
    }

    public SimpleStringProperty wydanieProperty() {
        return wydanie;
    }

    public void setWydanie(String wydanie) {
        this.wydanie.set(wydanie);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getJezyk() {
        return jezyk.get();
    }

    public SimpleStringProperty jezykProperty() {
        return jezyk;
    }

    public void setJezyk(String jezyk) {
        this.jezyk.set(jezyk);
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

    public String getUwagi() {
        return uwagi.get();
    }

    public SimpleStringProperty uwagiProperty() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi.set(uwagi);
    }

    public Image okladkaProperty() {
        return okladka;
    }

    public Image getOkladka() {
        return okladka;
    }

    public void setOkladka(Image okladka) {
        this.okladka = okladka;
    }

    public String getNazwa_wydawnictwa() {
        return nazwa_wydawnictwa.get();
    }

    public SimpleStringProperty nazwa_wydawnictwaProperty() {
        return nazwa_wydawnictwa;
    }

    public void setNazwa_wydawnictwa(String nazwa_wydawnictwa) {
        this.nazwa_wydawnictwa.set(nazwa_wydawnictwa);
    }

    public String getNazwa_gatunku() {
        return nazwa_gatunku.get();
    }

    public SimpleStringProperty nazwa_gatunkuProperty() {
        return nazwa_gatunku;
    }

    public void setNazwa_gatunku(String nazwa_gatunku) {
        this.nazwa_gatunku.set(nazwa_gatunku);
    }
}
