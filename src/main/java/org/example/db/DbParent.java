package org.example.db;

import javafx.scene.image.Image;
import org.example.Main;
import org.example.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


public class DbParent {
    public ArrayList<String[]> books = new ArrayList<>();
    public ArrayList<String[]> top = new ArrayList<>();
    public ArrayList<String[]> categories = new ArrayList<>();
    public ArrayList<String[]> rental = new ArrayList<>();
    public ArrayList<String[]> users = new ArrayList<>();
    public ArrayList<String[]> copies = new ArrayList<>();
    private Connection connection;

    /**
     * Metoda nawiązująca połączenie z bazą danych.
     */
    public void connectToDatabase() {
        String url = "jdbc:sqlite:database.db";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Metoda zamykająca połączenie z bazą danych.
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.exit(1);
        }
    }

    /**
     * Metoda sprawdzająca dostępność podanego loginu podczas rejestracji.
     *
     * @param login login do sprawdzenia
     * @return true, jeśli login jest dostępny; false w przeciwnym razie
     */
    public boolean testRegister(String login) {
        connectToDatabase();
        String testUserSQL = "SELECT count(id_uzytkownicy) as cnt from uzytkownicy where login=?";
        try (PreparedStatement statement = connection.prepareStatement(testUserSQL)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("cnt");
                    return id == 0;
                } else {
                    System.out.println("Błąd logowania");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Metoda próbująca zalogować użytkownika.
     *
     * @param login    login użytkownika
     * @param password hasło użytkownika
     * @return true, jeśli logowanie powiodło się; false w przeciwnym razie
     */
    public boolean tryLogin(String login, String password) {
        connectToDatabase();
        String selectUserSQL = "SELECT * FROM uzytkownicy WHERE login = ? AND haslo = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectUserSQL)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_uzytkownicy");
                    final String name = resultSet.getString("imie");
                    final String last_name = resultSet.getString("nazwisko");
                    final String czy_admin = resultSet.getString("czy_admin");
                    final String czy_zablokowany = resultSet.getString("czy_zablokowany");
                    User.getInstance().setImie(name);
                    User.getInstance().setNazwisko(last_name);
                    User.getInstance().setCzy_admin(czy_admin);
                    User.getInstance().setId(id);
                    User.getInstance().setCzy_zablokowany(czy_zablokowany);
                    getImage(id);
                    return true;
                } else {
                    System.out.println("Błąd logowania");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Metoda próbująca zarejestrować nowego użytkownika.
     *
     * @param imie     imię użytkownika
     * @param nazwisko nazwisko użytkownika
     * @param login    login użytkownika
     * @param password hasło użytkownika
     * @return true, jeśli rejestracja powiodła się; false w przeciwnym razie
     */
    public boolean tryRegister(String imie, String nazwisko, String login, String password) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO uzytkownicy (imie,nazwisko,login,haslo,czy_admin) values(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertUserSQL)) {
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, "N");
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            /* Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje.
             * Catch jest pusty, poniewaz dalej funkcja zamknie polaczenie i zwroci false,
             * a nastepnie funkcja z registercontroller pokaze monit.
             */
        } finally {
            closeConnection();
        }
        return false;
    }


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
     * Metoda pobiera i ustawia avatar dla użytkownika na podstawie jego ID.
     * Następnie metoda przerabia zdjęcie z formy bitowej na obiekt klasy Image i umieszcza je w instancji User.
     *
     * @param id ID użytkownika
     *
     */
    public void getImage(int id) {
        connectToDatabase();
        String print = "SELECT avatar FROM uzytkownicy WHERE id_uzytkownicy = ?";
        Image image;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                InputStream binaryStream = resultSet.getBinaryStream("avatar");
                if (binaryStream != null) {
                    byte[] imageBytes = binaryStream.readAllBytes();
                    image = new Image(new ByteArrayInputStream(imageBytes));
                    binaryStream.close();
                    User.getInstance().setImage(image);
                } else {
                    Image def = new Image("/res/icons/dark/avatar.png");
                    User.getInstance().setImage(def);
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
     * Metoda pobiera okładkę dla danej książki na podstawie jej ID z tabeli katalog.
     * Jeżeli w bazie okładki nie ma, zastępuje ją odpowiednim obrazem.
     * @param id ID książki
     *
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
     * Metoda do stworzenia nowego wypożyczenia egzemplarza książki przez danego użytkownika.
     *
     * @param egz numer egzemplarza
     * @param id  identyfikator użytkownika
     */
    public void setRent(int egz, int id) {
        connectToDatabase();
        String insert = "INSERT INTO rezerwacje(data_rezerwacji,data_konca,egzemplarze_id_egzemplarze,uzytkownicy_id_uzytkownicy) VALUES (date('now'),date('now', '+7 day'),?,?);\n";
        String update = "UPDATE egzemplarze SET czy_dostepne=\"N\" where id_egzemplarze=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, egz);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            PreparedStatement statement2 = connection.prepareStatement(update);
            statement2.setInt(1, egz);
            statement2.executeUpdate();
            statement2.close();
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda do aktualizacji danych logowania dla danego użytkownika.
     *
     * @param new_login nowy login
     * @param id        identyfikator użytkownika
     * @param login     aktualny login
     * @return true, jeśli aktualizacja powiodła się; false w przeciwnym przypadku
     */
    public boolean loginUpdate(String new_login, int id, String login) {
        connectToDatabase();
        String print = "UPDATE uzytkownicy SET login = ?  where id_uzytkownicy=? AND login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setString(1, new_login);
            statement.setInt(2, id);
            statement.setString(3, login);
            int result = statement.executeUpdate();
            statement.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
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

    /**
     * Metoda aktualizująca hasło użytkownika.
     *
     * @param new_password nowe hasło
     * @param id           ID użytkownika
     * @param password     aktualne hasło
     * @return true, jeśli hasło zostało zaktualizowane, false w przeciwnym przypadku
     */
    public boolean updatePassword(String new_password, int id, String password) {
        connectToDatabase();
        String query = "UPDATE uzytkownicy SET haslo = ? WHERE id_uzytkownicy = ? AND haslo = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, new_password);
            statement.setInt(2, id);
            statement.setString(3, password);
            int result = statement.executeUpdate();
            statement.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Metoda usuwająca profil użytkownika.
     *
     * @param password hasło użytkownika
     * @param id       ID użytkownika
     * @return true, jeśli profil został usunięty, false w przeciwnym przypadku
     */
    public boolean deleteProfile(String password, int id) {
        connectToDatabase();
        String query = "DELETE FROM uzytkownicy WHERE id_uzytkownicy = ? AND haslo = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, password);
            int result = statement.executeUpdate();
            statement.close();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
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
     * Metoda zmieniająca avatar użytkownika.
     *
     * @param imageData ciąg bitowy nowego obrazu
     * @param id ID użytkownika
     * @throws IOException w przypadku problemów z zapisem danych obrazu
     */
    public void changeAvatar(byte[] imageData, int id) throws IOException {
        connectToDatabase();
        String query = "UPDATE uzytkownicy SET avatar = ? WHERE id_uzytkownicy = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBytes(1, imageData);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error while updating avatar: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    /**
     * Metoda przedluza rezerwacje danego egzemplarza o 7 dni.
     *
     * @param id_egzemplarz id egzemplarza danej książki
     * @return ilość rekordów w bazie, która uległa zmianie
     */
    public int updateReservation(int id_egzemplarz) {
        connectToDatabase();
        String query = "UPDATE rezerwacje SET data_konca = DATE(data_konca, '+7 day'), ilosc_przedluzen = ilosc_przedluzen + 1 WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy = ?";
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id_egzemplarz);
            statement.setInt(2, User.getInstance().getId());
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) {
            closeConnection();
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        return resultSet;
    }

    /**
     * Metoda przedłuża wypożyczenie danego egzemplarza o 30 dni.
     * Aby metoda przez przypadek nie przedłużyła przypadkowych rekordów w przypadku gdyby użytkownik
     * wypożyczył ten sam egzemplarz pare razy, podajemy datę końca danego wypożyczenia.
     *
     * @param id_egzemplarz id egzemplarza danej książki
     * @param id_uzytkownik id użytkownika
     * @param rentalDate data końca wypożyczenia
     * @return true, jeśli przedłużenie się powiodło, false w przeciwnym przypadku
     */
    public boolean updateRentalDate(int id_egzemplarz, int id_uzytkownik, String rentalDate) {
        connectToDatabase();
        String query = "UPDATE wypozyczenia SET data_zwrotu = DATE(data_zwrotu, '+30 day'), ilosc_przedluzen = ilosc_przedluzen + 1 WHERE uzytkownicy_id_uzytkownicy = ? AND egzemplarze_id_egzemplarze = ? AND data_wypozyczenia = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id_uzytkownik);
            statement.setInt(2, id_egzemplarz);
            statement.setString(3, rentalDate);
            int result = statement.executeUpdate();
            statement.close();
            closeConnection();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    /**
     * Metoda usuwa rezerwację danej książki dla danego użytkownika.
     * Możliwa jest tylko jedna rezerwacja danego egzemplarza w danym czasie, więc nie tworzymy dodatkowych mechanizmów
     * sprawdzających poprawność wypożyczenia.
     *
     * @param id_uzytkownika - id użytkownika dokonującego wypożyczenia
     * @param id_egzemplarz - id egzemplarza danej książki
     * @return zwraca ilość rekordów usuniętych przez zapytanie
     */
    public int deleteReservation(int id_egzemplarz, int id_uzytkownika) {
        connectToDatabase();
        String query1 = "DELETE FROM rezerwacje WHERE uzytkownicy_id_uzytkownicy = ? AND egzemplarze_id_egzemplarze = ?";
        String query2 = "UPDATE egzemplarze SET czy_dostepne = 'T' WHERE id_egzemplarze = ?";
        int resultSet;
        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1, id_uzytkownika);
            statement.setInt(2, id_egzemplarz);
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) {
            closeConnection();
            System.out.println("Error while downloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        try {
            if (connection.isClosed()) {
                connectToDatabase();
            }
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setInt(1, id_egzemplarz);
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
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
     * Metoda która pobiera dane odnośnie egzemplarzy danej książki i umieszcza je w kontenerze copies.
     * Metoda składa się z głównego zapytania query, które pobiera wszystkie rekordy odnośnie egzemplarzy danej
     * książki, jakie się znajdują w bazie.
     * Następnie, bazując na pobranej danej "czy_dostepne", funkcja w zależności od parametru sprawdza warunki.
     *
     * Jeżeli "czy_dostępne" = "T", to takie pozostaje, jeżeli "N" to sprawdzane jest kolejno czy została ona wypożyczona,
     * bądź zarezerwowana. W każdej możliwości sprawdzane jest czy zrobił to uzytkownik o danym idUzytkownika, czy inny.
     * Jeżeli żadna z możliwości nie jest spełniona, parametr pozostaje równy "N".
     *
     * @param idKatalog      identyfikator katalogu
     * @param idUzytkownika  identyfikator użytkownika
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
     * Funkcja dodaje nowy egzemplarz do bazy danych.
     *
     * @param czy_dostepne  status dostępności egzemplarza
     * @param lokalizacja   lokalizacja egzemplarza
     * @param katalog       nazwa katalogu, do którego należy egzemplarz
     * @return liczba wierszy, które zostały dodane do bazy danych
     */
    public int setNewCopy(String czy_dostepne, String lokalizacja, String katalog) {
        connectToDatabase();
        String insertUserSQL = """
            INSERT INTO egzemplarze (czy_dostepne, lokalizacja, katalog_id_katalog)
            SELECT  ?, ?, k.id_katalog
            FROM katalog k
            WHERE k.nazwa = ?;
            """;
        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, czy_dostepne);
            statement.setString(2, lokalizacja);
            statement.setString(3, katalog);
            int ret = statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda pozwala dodać nie tylko nową książkę, ale też nowego autora, wydawnictwo i gatunek, który będzie mógł być
     * potem stosowany dla innych książek.
     * Najpierw przeprowadzamy dodanie książki do katalogu. Jeżeli się to powiedzie to zwracamy tablice [false,false,false].
     * W przeciwnym wypadku sprawdzamy kolejno czy istnieje dany autor, wydawnictwo i gatunek. Jeżeli nie istnieje to w odpowiednim
     * polu tabeli dajemy wartość true.
     * Następnie zwracamy tą tabelę do programu.
     *
     * @param  nazwa  nazwa ksiazki
     * @param rok_wydania rok wydania
     * @param wydanie wydanie
     * @param isbn kod ISBN
     * @param jezyk język książki
     * @param uwagi uwagi odnośnie książki
     * @param imie_autora   imie autora
     * @param nazwisko_autora nazwisko autora
     * @param nazwa_gatunku nazwa gatunku książki
     * @param nazwa_wydawnictwa nazwa wydawnictwa wydającego książkę
     * @return zwraca tabelę 3 elementową, typu boolean; pierwszy element oznacza czy nie istnieje taki autor, drugi - czy nie istnieje taki gatunek,
     * trzeci - czy nie istnieje takie wydawnictwo
     */
    public boolean[] add_one_record_from_catalog(String nazwa, String rok_wydania, String wydanie, String isbn, String jezyk, String uwagi, String imie_autora, String nazwisko_autora, String nazwa_gatunku, String nazwa_wydawnictwa, boolean[] data) {
        connectToDatabase();
        String insertUserSQL = """
                INSERT INTO katalog (nazwa, rok_wydania, wydanie, isbn, jezyk, uwagi, autor_id_autor, gatunek_id_gatunek, wydawnictwo_id_wydawnictwo)
                SELECT ?, ?, ?, ?, ?,  ?, a.id_autor, g.id_gatunek, w.id_wydawnictwo
                FROM (
                    SELECT id_autor
                    FROM autor  
                    WHERE imie_autora = ? AND nazwisko_autora = ?
                ) a
                CROSS JOIN (
                    SELECT id_gatunek
                    FROM gatunek
                    WHERE nazwa_gatunku = ?
                ) g
                CROSS JOIN (
                    SELECT id_wydawnictwo
                    FROM wydawnictwo
                    WHERE nazwa_wydawnictwa = ?
                ) w;

                """;
        boolean[] tab = data;
        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, nazwa);
            statement.setString(2, rok_wydania);
            statement.setString(3, wydanie);
            statement.setString(4, isbn);
            statement.setString(5, jezyk);
            statement.setString(6, uwagi);
            statement.setString(7, imie_autora);
            statement.setString(8, nazwisko_autora);
            statement.setString(9, nazwa_gatunku);
            statement.setString(10, nazwa_wydawnictwa);
            int ret = statement.executeUpdate();

            if (ret == 0) {   //suma bledu: 4 jezeli brak autora, 2 jezeli brak gatunku, 1 jezeli brak wydawnictwa

                int size = 0;
                String insert = "SELECT * FROM autor where imie_autora = ? AND nazwisko_autora=?";
                PreparedStatement statementAutor = connection.prepareStatement(insert);
                statementAutor.setString(1, imie_autora);
                statementAutor.setString(2, nazwisko_autora);
                ResultSet resultSetAutor = statementAutor.executeQuery();

                if (resultSetAutor.next()) {
                    // resultSet zawiera co najmniej jeden wiersz, ustawiamy tab[0] na false
                    tab[0] = false;
                } else {
                    // resultSet nie zawiera żadnych wierszy, ustawiamy tab[0] na true
                    tab[0] = true;
                }

                size = 0;
                insert = "SELECT * FROM gatunek where nazwa_gatunku = ?";
                PreparedStatement statementGatunek = connection.prepareStatement(insert);
                statementGatunek.setString(1, nazwa_gatunku);
                ResultSet resultSetGatunek = statementGatunek.executeQuery();

                if (resultSetGatunek.next()) {
                    // resultSet zawiera co najmniej jeden wiersz, ustawiamy tab[1] na false
                    tab[1] = false;
                } else {
                    // resultSet nie zawiera żadnych wierszy, ustawiamy tab[1] na true
                    tab[1] = true;
                }

                size = 0;
                insert = "SELECT * FROM wydawnictwo where nazwa_wydawnictwa = ?";
                PreparedStatement statementWydawnictwo = connection.prepareStatement(insert);
                statementWydawnictwo.setString(1, nazwa_wydawnictwa);
                ResultSet resultSetWydawnictwo = statementWydawnictwo.executeQuery();

                if (resultSetWydawnictwo.next()) {
                    // resultSet zawiera co najmniej jeden wiersz, ustawiamy tab[2] na false
                    tab[2] = false;
                } else {
                    // resultSet nie zawiera żadnych wierszy, ustawiamy tab[2] na true
                    tab[2] = true;
                }

                closeConnection();
                return tab;
            }
            closeConnection();
            return tab;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return tab;
    }

    /**
     * Metoda usuwa wszystkie rekordy z tabeli egzemplarze o danej nazwie książki i id egzemplarza
     *
     * @param katalog nazwa książki
     * @param id_egzemplarz id_egzemplarza
     * @return
     */
    public int deleteCopyFromDatabase(String katalog, String id_egzemplarz) {
        connectToDatabase();
        String deleteRecordSQL = "DELETE FROM egzemplarze " +
                "WHERE katalog_id_katalog IN (SELECT id_katalog FROM katalog WHERE nazwa = ?) " +
                "AND id_egzemplarze = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(deleteRecordSQL);
            statement.setString(1, katalog);
            statement.setString(2, id_egzemplarz);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda usuwa książkę o danych parametrach z bazy danych. Baza danych sama usunie egzemplarze dla danej książki.
     *
     * @param nazwa nazwa książki
     * @param isbn unikalny kod ISBN
     * @param nazwaGatunku nazwa gatunku
     * @param nazwaWydawnictwa nazwa wydawnictwa
     * @return zwróci ilość usuniętych rekordów (książek)
     */
    public int deleteBookFromDatabase(String nazwa, String isbn, String nazwaGatunku, String nazwaWydawnictwa) {
        connectToDatabase();
        String deletePositionSQL = "DELETE FROM katalog " +
                "WHERE nazwa = ? " +
                "AND isbn = ? " +
                "AND gatunek_id_gatunek = (SELECT id_gatunek FROM gatunek WHERE nazwa_gatunku = ?) " +
                "AND wydawnictwo_id_wydawnictwo = (SELECT id_wydawnictwo FROM wydawnictwo WHERE nazwa_wydawnictwa = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(deletePositionSQL);
            statement.setString(1, nazwa);
            statement.setString(2, isbn);
            statement.setString(3, nazwaGatunku);
            statement.setString(4, nazwaWydawnictwa);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda zmienia dane danego egzemplarza.
     *
     * @param czyDostepne czy książka jest dostępna (parametr wykorzystywany przy wyświetlaniu stanu książki)
     * @param lokalizacja lokalizacja, gdzie dana książka ma swoje miejsce w bibliotece
     * @param katalog nazwa pozycji
     * @param idEgzemplarze id egzemplarza danej pozycji
     * @return zwraca ilość zmodyfikowanych rekordów
     */
    public int modifyCopy(String czyDostepne, String lokalizacja, String katalog, String idEgzemplarze) {
        connectToDatabase();
        String updateEgzemplarzSQL = "UPDATE egzemplarze " +
                "SET czy_dostepne = ?, " +
                "lokalizacja = ? " +
                "WHERE katalog_id_katalog IN (SELECT k.id_katalog FROM katalog k WHERE k.nazwa = ?) " +
                "AND id_egzemplarze = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(updateEgzemplarzSQL);
            statement.setString(1, czyDostepne);
            statement.setString(2, lokalizacja);
            statement.setString(3, katalog);
            statement.setString(4, idEgzemplarze);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda pozwala na modyfikację danych, dla książki o danym id katalogu.
     *
     * @param rokWydania rok wydania
     * @param wydanie wydanie
     * @param isbn kod ISBN
     * @param jezyk język w którym książka jest napisana
     * @param uwagi uwagi
     * @param idKatalog id danej książki w katalogu
     * @return zwraca ilość zmodyfikowanych rekordów
     */
    public int modifyBook(String rokWydania, String wydanie, String isbn, String jezyk, String uwagi, String idKatalog) {
        connectToDatabase();
        String updatePositionSQL = "UPDATE katalog " +
                "SET rok_wydania = ?, " +
                "wydanie = ?, " +
                "isbn = ?, " +
                "jezyk = ?, " +
                "uwagi = ? " +
                "WHERE id_katalog = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(updatePositionSQL);
            statement.setString(1, rokWydania);
            statement.setString(2, wydanie);
            statement.setString(3, isbn);
            statement.setString(4, jezyk);
            statement.setString(5, uwagi);
            statement.setString(6, idKatalog);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda dodaje nowego użytkownika do bazy. Dodawany tak użytkownik nie musi spełniać żadnych warunków,
     * w przeciwieństwie do funkcji tryRegister, gdzie tam sprawdzanych jest kilka warunków.
     *
     * @param imie imie użytkownika
     * @param nazwisko nazwisko użytkownika
     * @param login login użytkownika
     * @param haslo hasło użytkownika
     * @param czy_admin pole w bazie przyjmuje wartości "T" i "N". Inne są odrzucane.
     * @return zwraca ilość dodanych rekordów do bazy
     */
    public int addUser(String imie, String nazwisko, String login, String haslo, String czy_admin) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO uzytkownicy (imie, nazwisko, login, haslo, czy_admin) " +
                "SELECT ?, ?, ?, ?, ?";

        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, login);
            statement.setString(4, haslo);
            statement.setString(5, czy_admin.toUpperCase(Locale.ROOT));
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda modyfikuje dane użytkownika, o podanym id_uzytkownicy.
     *
     * @param imie imie użytkownika
     * @param nazwisko nazwisko użytkownika
     * @param login login użytkownika
     * @param haslo hasło użytkownika
     * @param id_uzytkownicy id użytkownika
     * @return zwraca ilość zmodyfikowanych rekordów
     */
    public int changeUser(String imie, String nazwisko, String login, String haslo, String id_uzytkownicy) {
        connectToDatabase();
        String updateUserSQL = "UPDATE uzytkownicy " +
                "SET imie = ?, nazwisko = ?, login = ?, haslo = ? " +
                "WHERE id_uzytkownicy = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(updateUserSQL);
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, login);
            statement.setString(4, haslo);
            statement.setString(5, id_uzytkownicy);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda zmienia użytkownika na admina i odwrotnie. Dzięki tej metodzie admin może zostać zwykłym
     * użytkownikiem i korzystać w zwykły sposób z biblioteki.
     *
     * @param czy_admin pole w bazie przyjmuje wartości "T" i "N". Inne są odrzucane.
     * @param id_uzytkownicy id użytkownika
     * @return
     */
    public int modifyUprawnienia(String czy_admin, String id_uzytkownicy) {
        connectToDatabase();
        String updateUprawnieniaSQL = "UPDATE uzytkownicy " +
                "SET czy_admin = ? " +
                "WHERE id_uzytkownicy = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(updateUprawnieniaSQL);
            statement.setString(1, czy_admin);
            statement.setString(2, id_uzytkownicy);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda pozwala na usunięcie użytkownika. Baza danych automatycznie usunie jego wszystkie wypożyczenia i rezerwacje.
     *
     * @param id_user id użytkownika
     * @return zwraca ilość usuniętych rekordów (użytkowników)
     */
    public int deleteUser(String id_user) {
        connectToDatabase();
        String deleteUserSQL = "DELETE FROM uzytkownicy WHERE id_uzytkownicy = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(deleteUserSQL);
            statement.setString(1, id_user);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda zmienia status użytkownika dając bądź odbierając możliwość zalogowania się użytkownika do programu.
     *
     * @param id_user id użytkownika
     * @param block parametr "T" oznacza ustawienie blokady, "N" zdjęcie jej.
     * @return zwraca ilość zmodyfikowanych rekordów w bazie (ilość użytkowników)
     */
    public int blockUser(String id_user, String block) {
        connectToDatabase();
        String blockUserSQL = "UPDATE uzytkownicy SET czy_zablokowany = ? WHERE id_uzytkownicy = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(blockUserSQL);
            statement.setString(1, block);
            statement.setString(2, id_user);
            int result = statement.executeUpdate();
            closeConnection();
            return result;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return 0;
    }

    /**
     * Metoda dodająca autora do tabeli autor.
     *
     * @param imie imie autora
     * @param nazwisko nazwisko autora
     * @return true jeżeli udało się dodać autora do bazy, false jeżeli nie udało się dodać rekord
     */
    public boolean addAuthor(String imie, String nazwisko) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO autor (imie_autora, nazwisko_autora) values (?, ?);";

        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            int result = statement.executeUpdate();
            closeConnection();
            if(result == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Metoda dodająca gatunek do tabeli gatunków.
     *
     * @param nazwa_gatunku
     * @return true jeżeli udało się dodać gatunek do bazy, false jeżeli się nie udało
     */
    public boolean addGenre(String nazwa_gatunku) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO gatunek (nazwa_gatunku) values (?);";

        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, nazwa_gatunku);
            int result = statement.executeUpdate();
            closeConnection();
            if(result == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Metoda dodająca wydawnictwo do tabeli wydawnictw.
     *
     * @param nazwa_wydawnictwa nazwa wydawnictwa
     * @return true jeżeli udało się dodać wydawnictwo do bazy, false jeżeli się nie udało się
     */
    public boolean addPublisher(String nazwa_wydawnictwa) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO autor (nazwa_wydawnictwa) values (?);";

        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, nazwa_wydawnictwa);
            int result = statement.executeUpdate();
            closeConnection();
            if(result == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            // Handle exception or log error
        } finally {
            closeConnection();
        }
        return false;
    }
}



