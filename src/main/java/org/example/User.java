package org.example;

import javafx.scene.image.Image;

/**
 * Klasa reprezentująca użytkownika.
 */
public class User {
    private static User instance;
    static String imie;
    static String nazwisko;
    static int id;
    static String czy_admin;
    static String czy_zablokowany;
    Image image;

    /**
     * Pobiera instancję użytkownika.
     *
     * @return Instancja użytkownika.
     */
    public static User getInstance() {
        if (instance == null)
            instance = new User();
        return instance;
    }

    /**
     * Zwraca imię użytkownika.
     *
     * @return Imię użytkownika.
     */
    public static String getImie() {
        return imie;
    }

    /**
     * Ustawia imię użytkownika.
     *
     * @param imie Imię użytkownika do ustawienia.
     */
    public void setImie(String imie) {
        User.imie = imie;
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return Nazwisko użytkownika.
     */
    public static String getNazwisko() {
        return nazwisko;
    }

    /**
     * Ustawia nazwisko użytkownika.
     *
     * @param nazwisko Nazwisko użytkownika do ustawienia.
     */
    public void setNazwisko(String nazwisko) {
        User.nazwisko = nazwisko;
    }

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return Identyfikator użytkownika.
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id Identyfikator użytkownika do ustawienia.
     */
    public void setId(int id) {
        User.id = id;
    }

    /**
     * Zwraca informację czy użytkownik ma uprawnienia administratora.
     *
     * @return Informacja czy użytkownik ma uprawnienia administratora.
     */
    public String getCzy_admin() {
        return czy_admin;
    }

    /**
     * Ustawia informację czy użytkownik ma uprawnienia administratora.
     *
     * @param czy_admin Informacja czy użytkownik ma uprawnienia administratora do ustawienia.
     */
    public void setCzy_admin(String czy_admin) {
        User.czy_admin = czy_admin;
    }

    /**
     * Zwraca avatar użytkownika.
     *
     * @return Avatar użytkownika.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Ustawia avatar użytkownika.
     *
     * @param image Avatar użytkownika do ustawienia.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Ustawia instancję użytkownika.
     *
     * @param instance Instancja użytkownika do ustawienia.
     */
    public static void setInstance(User instance) {
        User.instance = instance;
    }

    /**
     * Zwraca informację czy użytkownik jest zablokowany.
     *
     * @return Informacja czy użytkownik jest zablokowany.
     */
    public String getCzy_zablokowany() {
        return czy_zablokowany;
    }

    /**
     * Ustawia informację czy użytkownik jest zablokowany.
     *
     * @param czy_zablokowany Informacja czy użytkownik jest zablokowany do ustawienia.
     */
    public void setCzy_zablokowany(String czy_zablokowany) {
        User.czy_zablokowany = czy_zablokowany;
    }
}
