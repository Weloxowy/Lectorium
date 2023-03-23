package org.example;

public class katalog {
    int id_katalog;
    String nazwa;
    String rok_wydania;
    String wydanie;
    String isbn;
    String jezyk;

    public int getId_katalog() {
        return id_katalog;
    }

    public void setId_katalog(int id_katalog) {
        this.id_katalog = id_katalog;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRok_wydania() {
        return this.rok_wydania;
    }

    public void setRok_wydania(String rok_wydania) {
        this.rok_wydania = rok_wydania;
    }

    public String getWydanie() {
        return wydanie;
    }

    public void setWydanie(String wydanie) {
        this.wydanie = wydanie;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getJezyk() {
        return this.jezyk;
    }

    public void setJezyk(String jezyk) {
        this.jezyk = jezyk;
    }
}
