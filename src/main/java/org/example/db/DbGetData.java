package org.example.db;

import javafx.scene.image.Image;
import org.example.Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Klasa DbGetData to klasa dziedziczącej po klasie DbParent.
 * Posiada ona metody, które pobierają dane z bazy danych.
 *
 * @see org.example.db.DbParent
 */
public class DbGetData extends DbParent {

    /**
     * Pole {@code books} reprezentuje kolekcję książek.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje informacje o konkretnej książce.
     */
    public ArrayList<String[]> books = new ArrayList<>();

    /**
     * Pole {@code categories} reprezentuje kolekcję kategorii.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje konkretną kategorię i ilość należących do niej książek.
     */
    public ArrayList<String[]> categories = new ArrayList<>();

    /**
     * Pole {@code rental} reprezentuje kolekcję książek.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje informacje o konkretnym wypożyczeniu, bądź rezerwacji.
     */
    public ArrayList<String[]> rental = new ArrayList<>();

    /**
     * Pole {@code users} reprezentuje kolekcję użytkowników.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje informacje o konkretnym użytkowniku.
     */
    public ArrayList<String[]> users = new ArrayList<>();

    /**
     * Pole {@code top} reprezentuje kolekcję najlepszych pozycji.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje informacje o konkretnej najlepszej pozycji.
     */
    public ArrayList<String[]> top = new ArrayList<>();
    /**
     * Pole {@code copies} reprezentuje kolekcję kopii książek.
     * Jest to lista przechowująca tablice łańcuchów znaków, gdzie każda tablica reprezentuje informacje o konkretnej kopii książki.
     */
    public ArrayList<String[]> copies = new ArrayList<>();

    /**
     * Metoda pobiera informacje o wszystkich książkach, będących w tabeli "katalog" bazy danych.
     * Dane o każdej książce zapisuje w postaci tablicy Stringów do kontenera
     *
     * @see #books
     */
    public void getBooks() {
        connectToDatabase();
        String print = "SELECT katalog.id_katalog,autor.imie_autora, autor.nazwisko_autora, katalog.nazwa, katalog.rok_wydania, "
                + "katalog.wydanie, katalog.isbn, katalog.jezyk, katalog.uwagi, wydawnictwo.nazwa_wydawnictwa, gatunek.nazwa_gatunku "
                + "FROM katalog, autor, wydawnictwo, gatunek WHERE katalog.autor_id_autor = autor.id_autor "
                + "AND katalog.wydawnictwo_id_wydawnictwo = wydawnictwo.id_wydawnictwo AND gatunek.id_gatunek = katalog.gatunek_id_gatunek;";
        try {
            books.clear();
            PreparedStatement statement = connection.prepareStatement(print);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_katalog = resultSet.getInt("id_katalog");
                final String nazwa = resultSet.getString("nazwa");
                String nazwa_autora = resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora");
                final String rok_wydania = resultSet.getString("rok_wydania");
                final String wydanie = resultSet.getString("wydanie");
                final String isbn = resultSet.getString("isbn");
                final String jezyk = resultSet.getString("jezyk");
                final String uwagi = resultSet.getString("uwagi");
                final String nazwa_wydawnictwa = resultSet.getString("nazwa_wydawnictwa");
                final String nazwa_gatunku = resultSet.getString("nazwa_gatunku");
                String[] row = {String.valueOf(id_katalog), nazwa, nazwa_autora, rok_wydania, wydanie, isbn, jezyk, uwagi, nazwa_wydawnictwa, nazwa_gatunku};
                books.add(row);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }


    /**
     * Metoda pobiera okładkę dla danej książki na podstawie jej ID z tabeli katalog.
     * Jeżeli w bazie okładki nie ma, zastępuje ją odpowiednim obrazem.
     *
     * @param id ID książki
     */
    public void getCover(int id) {
        connectToDatabase();
        String print = "SELECT okladka FROM katalog WHERE id_katalog = ?";
        Image image;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                InputStream binaryStream = resultSet.getBinaryStream("okladka");
                if (binaryStream != null) {
                    byte[] imageBytes = binaryStream.readAllBytes();
                    image = new Image(new ByteArrayInputStream(imageBytes));
                    binaryStream.close();
                    Main.kat.setOkladka(image);
                } else {
                    Image def = new Image("res/media/Brakokładki.jpg");
                    Main.kat.setOkladka(def);
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda do pobrania danych 8 najnowszych książek.
     * Dane są pobierane tylko raz i przechowywane w kontenerze top.
     * Przy następnych wywołaniach, funkcja nic nie robi.
     *
     * @see #top
     */
    public void getTop() {
        if (top.isEmpty()) {
            connectToDatabase();
            String print = "SELECT katalog.id_katalog,autor.imie_autora, autor.nazwisko_autora, katalog.nazwa\n" +
                    "FROM katalog, autor WHERE katalog.autor_id_autor = autor.id_autor ORDER BY katalog.id_katalog DESC LIMIT 8;";
            try {
                PreparedStatement statement = connection.prepareStatement(print);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final int id_katalog = resultSet.getInt("id_katalog");
                    String nazwa_autora = "";
                    nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora"));
                    final String nazwa = resultSet.getString("nazwa");
                    String[] row = {String.valueOf(id_katalog), nazwa, nazwa_autora};
                    top.add(row);
                }
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Error while downloading data from DB: " + e.getMessage());
                System.exit(100);
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * Metoda do pobrania listy kategorii i ilości książek w nich zawartych.
     * Dane są pobierane tylko raz i przechowywane w kontenerze categories.
     * Przy następnych wywołaniach, funkcja nic nie robi.
     *
     * @see #categories
     */
    public void getCategories() {
        if (categories.isEmpty()) {
            connectToDatabase();
            String print = "SELECT gatunek.nazwa_gatunku , count(katalog.gatunek_id_gatunek) as ilosc\n" +
                    "FROM gatunek, katalog WHERE gatunek.id_gatunek = katalog.gatunek_id_gatunek\n" +
                    "GROUP BY katalog.gatunek_id_gatunek;";
            try {
                PreparedStatement statement = connection.prepareStatement(print);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final String nazwa_gatunku = resultSet.getString("nazwa_gatunku");
                    final String ilosc = resultSet.getString("ilosc");
                    String[] row = {nazwa_gatunku, ilosc};
                    categories.add(row);
                }
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Error while downloading data from DB: " + e.getMessage());
                System.exit(100);
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * Metoda pobierająca informacje o wszystkich wypożyczeniach użytkownika o podanym ID.
     * Dane są zapisywane do kontenera rental.
     *
     * @param id ID użytkownika
     * @see #rental
     */
    public void getHireInformation(int id) {
        connectToDatabase();
        String query = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen " +
                "FROM katalog, egzemplarze, autor, wypozyczenia " +
                "WHERE katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze " +
                "AND wypozyczenia.uzytkownicy_id_uzytkownicy = ?";
        try {
            rental.clear();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_wypozyczenia = resultSet.getString("data_wypozyczenia");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                final int ilosc_przedluzen = resultSet.getInt("ilosc_przedluzen");
                String nazwa_autora = resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora");
                String[] row = {String.valueOf(id_egzemplarze), nazwa, nazwa_autora, data_wypozyczenia, data_zwrotu, String.valueOf(ilosc_przedluzen)};
                rental.add(row);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda zwracająca ilość rezerwacji dla użytkownika o podanym ID.
     * Metoda wykorzystywana dla ustalenia czy użytkownik przekroczył limit rezerwacji.
     *
     * @param id ID użytkownika
     * @return liczba rezerwacji egzemplarzy
     */
    public int getRentLimit(int id) {
        connectToDatabase();
        String query = "SELECT COUNT(id_rezerwacje) AS cnt FROM rezerwacje WHERE uzytkownicy_id_uzytkownicy = ?";
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("cnt");
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            closeConnection();
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        return count;
    }

    /**
     * Metoda sprawdzająca informacje o aktualnych wypożyczeniach użytkownika o podanym ID.
     * Dane zapisywane są do kontenera rental.
     *
     * @param id ID użytkownika
     * @see #rental
     */
    public void checkHireInformation(int id) {
        connectToDatabase();
        String query = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen " +
                "FROM katalog, egzemplarze, autor, wypozyczenia " +
                "WHERE katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze " +
                "AND wypozyczenia.data_zwrotu > date('now') AND wypozyczenia.uzytkownicy_id_uzytkownicy = ?";
        try {
            rental.clear();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_wypozyczenia = resultSet.getString("data_wypozyczenia");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                final String ilosc_przedluzen = resultSet.getString("ilosc_przedluzen");
                String nazwa_autora = resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora");
                String[] row = {String.valueOf(id_egzemplarze), nazwa, nazwa_autora, data_wypozyczenia, data_zwrotu, ilosc_przedluzen};
                rental.add(row);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda która pobiera dane odnośnie egzemplarzy danej książki i umieszcza je w kontenerze copies.
     * Metoda składa się z głównego zapytania query, które pobiera wszystkie rekordy odnośnie egzemplarzy danej
     * książki, jakie się znajdują w bazie.
     * Następnie, bazując na pobranej danej "czy_dostepne", funkcja w zależności od parametru sprawdza warunki.
     * <p>
     * Jeżeli "czy_dostępne" = "T", to takie pozostaje, jeżeli "N" to sprawdzane jest kolejno czy została ona wypożyczona,
     * bądź zarezerwowana. W każdej możliwości sprawdzane jest czy zrobił to uzytkownik o danym idUzytkownika, czy inny.
     * Jeżeli żadna z możliwości nie jest spełniona, parametr pozostaje równy "N".
     *
     * @param idKatalog     identyfikator katalogu
     * @param idUzytkownika identyfikator użytkownika
     * @see #copies
     */
    public void getCopies(int idKatalog, int idUzytkownika) {
        copies.clear();
        try {
            connectToDatabase();
            String query = "SELECT e.id_egzemplarze, k.nazwa, e.lokalizacja, e.czy_dostepne " +
                    "FROM egzemplarze e " +
                    "JOIN katalog k ON e.katalog_id_katalog = k.id_katalog " +
                    "WHERE e.katalog_id_katalog = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idKatalog);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idEgzemplarza = resultSet.getInt("id_egzemplarze");
                String nazwaKsiazki = resultSet.getString("nazwa");
                String lokalizacja = resultSet.getString("lokalizacja");
                String czyDostepne = resultSet.getString("czy_dostepne");

                String czyWypozyczone = czyDostepne;
                LocalDate dataZwrotu = null;

                if (czyDostepne.equals("N")) {
                    String wypozyczeniaQuery = "SELECT data_zwrotu " +
                            "FROM wypozyczenia " +
                            "WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy = ? " +
                            "AND data_zwrotu > date('now')";
                    statement = connection.prepareStatement(wypozyczeniaQuery);
                    statement.setInt(1, idEgzemplarza);
                    statement.setInt(2, idUzytkownika);
                    ResultSet wypozyczeniaResultSet = statement.executeQuery();

                    if (wypozyczeniaResultSet.next()) {
                        czyWypozyczone = "W";
                        String dataZwrotuStr = wypozyczeniaResultSet.getString("data_zwrotu");
                        if (dataZwrotuStr != null) {
                            dataZwrotu = LocalDate.parse(dataZwrotuStr);
                        }
                    } else {
                        String inniUzytkownicyQuery = "SELECT data_zwrotu " +
                                "FROM wypozyczenia " +
                                "WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy != ? " +
                                "AND data_zwrotu > date('now')";
                        statement = connection.prepareStatement(inniUzytkownicyQuery);
                        statement.setInt(1, idEgzemplarza);
                        statement.setInt(2, idUzytkownika);
                        wypozyczeniaResultSet = statement.executeQuery();

                        if (wypozyczeniaResultSet.next()) {
                            czyWypozyczone = "NW";
                            String dataZwrotuStr = wypozyczeniaResultSet.getString("data_zwrotu");
                            if (dataZwrotuStr != null) {
                                dataZwrotu = LocalDate.parse(dataZwrotuStr);
                            }
                        } else {
                            String rezerwacjeQuery = "SELECT data_konca " +
                                    "FROM rezerwacje " +
                                    "WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy = ? " +
                                    "AND data_konca > date('now')";
                            statement = connection.prepareStatement(rezerwacjeQuery);
                            statement.setInt(1, idEgzemplarza);
                            statement.setInt(2, idUzytkownika);
                            ResultSet rezerwacjeResultSet = statement.executeQuery();

                            if (rezerwacjeResultSet.next()) {
                                czyWypozyczone = "R";
                                String dataKoncaStr = rezerwacjeResultSet.getString("data_konca");
                                if (dataKoncaStr != null) {
                                    dataZwrotu = LocalDate.parse(dataKoncaStr);
                                }
                            } else {
                                String inniUzytkownicyRezerwacjeQuery = "SELECT data_konca " +
                                        "FROM rezerwacje " +
                                        "WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy != ? " +
                                        "AND data_konca > date('now')";
                                statement = connection.prepareStatement(inniUzytkownicyRezerwacjeQuery);
                                statement.setInt(1, idEgzemplarza);
                                statement.setInt(2, idUzytkownika);
                                rezerwacjeResultSet = statement.executeQuery();

                                if (rezerwacjeResultSet.next()) {
                                    czyWypozyczone = "NR";
                                    String dataKoncaStr = rezerwacjeResultSet.getString("data_konca");
                                    if (dataKoncaStr != null) {
                                        dataZwrotu = LocalDate.parse(dataKoncaStr);
                                    }
                                }
                            }
                        }
                    }
                }
                String[] row = {String.valueOf(idEgzemplarza), nazwaKsiazki, lokalizacja, czyWypozyczone, String.valueOf(dataZwrotu)};
                copies.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    /**
     * Funkcja wypisuje do kontenera users dane wszystkich użytkowników.
     *
     * @see #users
     */
    public void printUsers() {
        connectToDatabase();
        String query = "SELECT id_uzytkownicy, imie, nazwisko, czy_admin, czy_zablokowany FROM uzytkownicy";
        try {
            users.clear();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_uzytkownicy = resultSet.getInt("id_uzytkownicy");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String czy_admin = resultSet.getString("czy_admin");
                String czy_zablokowany = resultSet.getString("czy_zablokowany");
                String[] row = {imie, nazwisko, String.valueOf(id_uzytkownicy), czy_admin, czy_zablokowany};
                users.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda pobierająca informacje o rezerwacjach użytkownika o podanym ID.
     * Dane umieszczane są w kontenerze rental.
     *
     * @param id ID użytkownika
     * @see #rental
     */
    public void getReservationInformation(int id) {
        connectToDatabase();
        String query = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, rezerwacje.data_konca, rezerwacje.data_rezerwacji, rezerwacje.ilosc_przedluzen " +
                "FROM katalog, egzemplarze, autor, rezerwacje " +
                "WHERE katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = rezerwacje.egzemplarze_id_egzemplarze " +
                "AND rezerwacje.uzytkownicy_id_uzytkownicy = ?";
        try {
            rental.clear();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_konca = resultSet.getString("data_konca");
                final String data_rezerwacji = resultSet.getString("data_rezerwacji");
                final int ilosc_przedluzen = resultSet.getInt("ilosc_przedluzen");
                final String anuluj_rezerwacje = "1";
                String nazwa_autora = resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora");
                String[] row = {String.valueOf(id_egzemplarze), nazwa, nazwa_autora, data_konca, data_rezerwacji, String.valueOf(ilosc_przedluzen), anuluj_rezerwacje};
                rental.add(row);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

}
