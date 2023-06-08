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


public class dbloader {
    private Connection connection;

    public void connectToDatabase() {
        String url = "jdbc:sqlite:database.db";
        connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }
    }

    void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.exit(1);
        }

    }

    public boolean testRegister(String login) {
        connectToDatabase();
        String testUserSQL = "SELECT count(id_uzytkownicy) as cnt from uzytkownicy where login=?";
        try {
            PreparedStatement statement = connection.prepareStatement(testUserSQL);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("cnt");
                resultSet.close();
                closeConnection();
                return id == 0;
            } else { //Wrong login or password
                System.out.println("Błąd logowania");
                resultSet.close();
                closeConnection();
                return false;
            }
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error inserting user: " + e.getMessage());
            System.exit(100);
        }
        closeConnection();
        return false;
    }

    public boolean tryLogin(String login, String password) {
        connectToDatabase();
        String selectUserSQL = "SELECT * FROM uzytkownicy WHERE login = ? AND haslo = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectUserSQL);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //Accepted login
                int id = resultSet.getInt("id_uzytkownicy");
                final String name = resultSet.getString("imie");
                final String last_name = resultSet.getString("nazwisko");
                final String czy_admin = resultSet.getString("czy_admin");
                User.getInstance().setImie(name);
                User.getInstance().setNazwisko(last_name);
                User.getInstance().setCzy_admin(czy_admin);
                User.getInstance().setId(id);
                getimage(id);
                resultSet.close();
                closeConnection();
                return true;
            } else { //Wrong login or password
                System.out.println("Błąd logowania");
                resultSet.close();
                closeConnection();
                return false;
            }
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error inserting user: " + e.getMessage());
            System.exit(100);
        }
        closeConnection();
        return false;
    }

    public boolean tryRegister(String imie, String nazwisko, String login, String password) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO uzytkownicy (imie,nazwisko,login,haslo,czy_admin) values(?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, "N");
            statement.executeUpdate();
            closeConnection();
            return true;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return false;
    }

    public ArrayList<String[]> array = new ArrayList<>();

    public void print_book() {
        connectToDatabase();
        String print = """
                SELECT katalog.id_katalog, autor.tytul_autora ,autor.imie_autora, autor.nazwisko_autora, katalog.nazwa, katalog.rok_wydania,\s
                katalog.wydanie, katalog.isbn, katalog.jezyk, katalog.uwagi, wydawnictwo.nazwa_wydawnictwa, gatunek.nazwa_gatunku\s
                FROM katalog, autor, wydawnictwo, gatunek WHERE katalog.autor_id_autor = autor.id_autor AND\s
                katalog.wydawnictwo_id_wydawnictwo = wydawnictwo.id_wydawnictwo AND gatunek.id_gatunek = katalog.gatunek_id_gatunek;""";
        try {
            array.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_katalog = resultSet.getInt("id_katalog");
                final String nazwa = resultSet.getString("nazwa");
                String nazwa_autora = "";
                nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") + " " + resultSet.getString("nazwisko_autora"));
                final String rok_wydania = resultSet.getString("rok_wydania");
                final String wydanie = resultSet.getString("wydanie");
                final String isbn = resultSet.getString("isbn");
                final String jezyk = resultSet.getString("jezyk");
                final String uwagi = resultSet.getString("uwagi");
                final String nazwa_wydawnictwa = resultSet.getString("nazwa_wydawnictwa");
                final String nazwa_gatunku = resultSet.getString("nazwa_gatunku");
                String[] row = {String.valueOf(id_katalog), nazwa, nazwa_autora, rok_wydania, wydanie, isbn, jezyk, uwagi, nazwa_wydawnictwa, nazwa_gatunku};
                array.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    public void getimage(int id) {
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
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void get_cover(int id) {
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
                    Image def = new Image("/res/media/Brakokładki.jpg");
                    Main.kat.setOkladka(def);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<String[]> copy = new ArrayList<>();

    public void print_copies(int id) { //TODO do poprawy zapytanie; potrójne zagnieżdzenie?
        connectToDatabase();
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, egzemplarze.lokalizacja,\n" +
                "       CASE\n" +
                "           WHEN egzemplarze.czy_dostepne = 'N' AND rezerwacje.egzemplarze_id_egzemplarze IS NOT NULL THEN 'R'\n" +
                "           WHEN egzemplarze.czy_dostepne = 'N' AND wypozyczenia.egzemplarze_id_egzemplarze IS NOT NULL THEN 'W'\n" +
                "           ELSE egzemplarze.czy_dostepne\n" +
                "       END AS skad,\n" +
                "       CASE\n" +
                "           WHEN (IFNULL(wypozyczenia.data_zwrotu, rezerwacje.data_konca) IS NULL OR IFNULL(wypozyczenia.data_zwrotu, rezerwacje.data_konca) > DATE('now')) THEN IFNULL(wypozyczenia.data_zwrotu, rezerwacje.data_konca)\n" +
                "           ELSE NULL\n" +
                "       END AS data_zwrotu\n" +
                "FROM katalog\n" +
                "JOIN egzemplarze ON egzemplarze.katalog_id_katalog = katalog.id_katalog\n" +
                "LEFT JOIN wypozyczenia ON wypozyczenia.egzemplarze_id_egzemplarze = egzemplarze.id_egzemplarze\n" +
                "LEFT JOIN rezerwacje ON rezerwacje.egzemplarze_id_egzemplarze = egzemplarze.id_egzemplarze\n" +
                "WHERE katalog.id_katalog = ?\n" +
                "ORDER BY data_zwrotu DESC;\n";
        try {
            copy.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final String nazwa = resultSet.getString("nazwa");
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String lokalizacja = resultSet.getString("lokalizacja");
                final String czy_dostepne = resultSet.getString("skad");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                String[] row = {nazwa, String.valueOf(id_egzemplarze), lokalizacja, czy_dostepne, data_zwrotu};
                copy.add(row);
            }
            resultSet.close();
            //closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<String[]> top = new ArrayList<>();

    public void get_top() {
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
                closeConnection();
            } catch (SQLException e) { //Error while connecting with DB
                System.out.println("Error while dowloading data from DB: " + e.getMessage());
                System.exit(100);
            } finally {
                closeConnection();
            }
        }
    }

    public ArrayList<String[]> categories = new ArrayList<>();

    public void get_categories() {
        if (categories.isEmpty()) {
            connectToDatabase();
            String print = """
                    SELECT gatunek.nazwa_gatunku , count(katalog.gatunek_id_gatunek) as ilosc\s
                    from gatunek, katalog where gatunek.id_gatunek = katalog.gatunek_id_gatunek\s
                    GROUP BY katalog.gatunek_id_gatunek;""";
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
            } catch (SQLException e) { //Error while connecting with DB
                System.out.println("Error while dowloading data from DB: " + e.getMessage());
                System.exit(100);
            } finally {
                closeConnection();
            }
        }
    }

    public void rent(int egz, int id) { //nie chce działać
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
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    public boolean login_update(String new_login, int id, String login) {
        connectToDatabase();
        String print = "UPDATE uzytkownicy SET login = ?  where id_uzytkownicy=? AND login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setString(1, new_login);
            statement.setInt(2, id);
            statement.setString(3, login);
            int result = statement.executeUpdate();
            statement.close();
            closeConnection();
            return result > 0;
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }
    public ArrayList<String[]> ListHire = new ArrayList<>();
    public void yourHireInformation(int id) {
        connectToDatabase();
        String print = """
                SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen\s
                from katalog, egzemplarze, autor, wypozyczenia where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze
                AND wypozyczenia.uzytkownicy_id_uzytkownicy = ?\s""";
        try {
            ListHire.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_wypozyczenia = resultSet.getString("data_wypozyczenia");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                final int ilosc_przedluzen = resultSet.getInt("ilosc_przedluzen");
                String nazwa_autora = "";
                nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") +" "+ resultSet.getString("nazwisko_autora"));
                String[] row = {String.valueOf(id_egzemplarze),nazwa,nazwa_autora, data_wypozyczenia, data_zwrotu, String.valueOf(ilosc_przedluzen)};
                ListHire.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }

        closeConnection();
    }


    public void check_hire_information(int id)
    {
        connectToDatabase();
        String print = """
                SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen\s
                from katalog, egzemplarze, autor, wypozyczenia where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze
                AND wypozyczenia.data_zwrotu > date('now')  AND wypozyczenia.uzytkownicy_id_uzytkownicy = ?""";
        try {
            ListHire.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_wypozyczenia = resultSet.getString("data_wypozyczenia");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                final String ilosc_przedluzen= resultSet.getString("ilosc_przedluzen");

                String nazwa_autora = "";
                nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") +" "+ resultSet.getString("nazwisko_autora"));
                String[] row = {String.valueOf(id_egzemplarze), nazwa, nazwa_autora, data_wypozyczenia, data_zwrotu, ilosc_przedluzen};
                ListHire.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }

        closeConnection();
    }




    public void yourReservationInformation(int id) {
        connectToDatabase();
        String print = """
                SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, rezerwacje.data_konca, rezerwacje.data_rezerwacji, rezerwacje.ilosc_przedluzen\s
                from katalog, egzemplarze, autor, rezerwacje where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = rezerwacje.egzemplarze_id_egzemplarze
                AND rezerwacje.uzytkownicy_id_uzytkownicy = ?""";
        try {
            ListHire.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String nazwa = resultSet.getString("nazwa");
                final String data_konca = resultSet.getString("data_konca");
                final String data_rezerwacji = resultSet.getString("data_rezerwacji");
                final int ilosc_przedluzen = resultSet.getInt("ilosc_przedluzen");
                final String anuluj_rezerwacje = "1";
                String nazwa_autora = "";
                nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") +" "+ resultSet.getString("nazwisko_autora"));
                String[] row = {String.valueOf(id_egzemplarze),nazwa,nazwa_autora, data_konca, data_rezerwacji, String.valueOf(ilosc_przedluzen), anuluj_rezerwacje};
                ListHire.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }

        closeConnection();
    }

    public boolean password_update(String new_password, int id, String password) {
        connectToDatabase();
        String print = "UPDATE uzytkownicy SET haslo = ?  where id_uzytkownicy=? AND haslo = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setString(1, new_password);
            statement.setInt(2, id);
            statement.setString(3, password);
            int result = statement.executeUpdate();
            statement.close();
            closeConnection();
            return result > 0;
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public boolean profile_delete(String password, int id) {
        connectToDatabase();
        String print = "DELETE FROM uzytkownicy where id_uzytkownicy=? AND haslo = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            statement.setString(2, password);
            int result = statement.executeUpdate();
            statement.close();
            closeConnection();
            return result > 0;
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public int rentlimit(int id){
        connectToDatabase();
        String print = "SELECT count(id_rezerwacje) as cnt from rezerwacje where uzytkownicy_id_uzytkownicy=?;";
        int ilosc = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ilosc = resultSet.getInt("cnt");
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            closeConnection();
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        return ilosc;
    }
    public void avatar_change(byte[] imageData, int id) throws IOException {
        connectToDatabase();
        String print = "UPDATE uzytkownicy SET avatar = ? WHERE id_uzytkownicy = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
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

    public void update(int id)
    {
        connectToDatabase();
        String print = "UPDATE rezerwacje SET data_konca = DATE(data_konca, '+7 day'), ilosc_przedluzen = ilosc_przedluzen + 1 where rezerwacje.egzemplarze_id_egzemplarze = ? AND rezerwacje.uzytkownicy_id_uzytkownicy = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            statement.setInt(2, User.getInstance().getId());
            statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            closeConnection();
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
    }

    public boolean rent_date_update(int egz, int id, String date) {
        connectToDatabase();
        String print = "UPDATE wypozyczenia SET data_zwrotu = DATE(data_zwrotu, '+30 day'), ilosc_przedluzen = ilosc_przedluzen + 1  where uzytkownicy_id_uzytkownicy=? AND egzemplarze_id_egzemplarze = ? AND data_wypozyczenia = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            statement.setInt(2, egz);
            statement.setString(3, date); //data zla, sprawdzic poprawnosc
            int result = statement.executeUpdate();
            statement.close();
            closeConnection();
            return result > 0;
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    public int delete(int id_egzemplarz)
    {
        connectToDatabase();
        String print = "DELETE from rezerwacje where uzytkownicy_id_uzytkownicy = ? AND egzemplarze_id_egzemplarze = ?;";
        String print2 = "UPDATE egzemplarze SET czy_dostepne = 'T' where egzemplarze.id_egzemplarze = ?;";
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, User.getInstance().getId());
            statement.setInt(2, id_egzemplarz);
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            closeConnection();
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        try{
            if(connection.isClosed()){
                connectToDatabase();
            }
            PreparedStatement statement = connection.prepareStatement(print2);
            statement.setInt(1,id_egzemplarz);
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ArrayList<String[]> lista = new ArrayList<>();
    public void print_users() {
        connectToDatabase();
        String print = "Select id_uzytkownicy, imie, nazwisko, czy_admin from uzytkownicy";
        try {
            lista.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_uzytkownicy = resultSet.getInt("id_uzytkownicy");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String czy_admin = resultSet.getString("czy_admin");
                String[] row = {imie, nazwisko,String.valueOf(id_uzytkownicy), czy_admin};
                lista.add(row);
                //System.out.println(imie + " " + nazwisko + " " + czy_admin + " " + id_uzytkownicy);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<String[]> results = new ArrayList<>();
    public void nowe_item(int idKatalog, int idUzytkownika) { //działa bardzo dobrze na malej grupie rekordow
        results.clear();
        try {
            connectToDatabase();
            // Pobranie danych dotyczących egzemplarzy dla danego katalogu
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
                    // Sprawdzenie czy egzemplarz jest wypożyczony przez danego użytkownika
                    query = "SELECT data_zwrotu FROM wypozyczenia WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy = ? " +
                            "AND data_zwrotu > date('now')";
                    statement = connection.prepareStatement(query);
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
                        // Sprawdzenie czy egzemplarz jest wypożyczony przez innego użytkownika
                        query = "SELECT data_zwrotu FROM wypozyczenia WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy != ? " +
                                "AND data_zwrotu > date('now')";
                        statement = connection.prepareStatement(query);
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
                            // Sprawdzenie czy egzemplarz jest zarezerwowany przez danego użytkownika
                            query = "SELECT data_konca FROM rezerwacje WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy = ? " +
                                    "AND data_konca > date('now')";
                            statement = connection.prepareStatement(query);
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
                                // Sprawdzenie czy egzemplarz jest zarezerwowany przez innego użytkownika
                                query = "SELECT data_konca FROM rezerwacje WHERE egzemplarze_id_egzemplarze = ? AND uzytkownicy_id_uzytkownicy != ? " +
                                        "AND data_konca > date('now')";
                                statement = connection.prepareStatement(query);
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
                String[] row = {String.valueOf(idEgzemplarza),nazwaKsiazki,lokalizacja,czyWypozyczone, String.valueOf(dataZwrotu)};
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public int add_to_database(String czy_dostepne, String lokalizacja, String katalog) {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO egzemplarze (czy_dostepne, lokalizacja, katalog_id_katalog)\n" +
                "SELECT  ?, ?, k.id_katalog\n" +
                "FROM katalog k\n" +
                "WHERE k.nazwa = ?;\n";
        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, czy_dostepne);
            statement.setString(2, lokalizacja);
            statement.setString(3, katalog);
           int ret =  statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return 0;
    }

    public int add_one_record_from_catalog(String nazwa, String rok_wydania, String wydanie, String isbn, String jezyk, String uwagi, String imie_autora, String nazwisko_autora, String nazwa_gatunku, String nazwa_wydawnictwa)
    {
        connectToDatabase();
        String insertUserSQL = "INSERT INTO katalog (nazwa, rok_wydania, wydanie, isbn, jezyk, uwagi, autor_id_autor, gatunek_id_gatunek, wydawnictwo_id_wydawnictwo)\n" +
                "SELECT ?, ?, ?, ?, ?,  ?, a.id_autor, g.id_gatunek, w.id_wydawnictwo\n" +
                "FROM (\n" +
                "    SELECT id_autor\n" +
                "    FROM autor\n" +
                "    WHERE imie_autora = ? AND nazwisko_autora = ?\n" +
                ") a\n" +
                "CROSS JOIN (\n" +
                "    SELECT id_gatunek\n" +
                "    FROM gatunek\n" +
                "    WHERE nazwa_gatunku = ?\n" +
                ") g\n" +
                "CROSS JOIN (\n" +
                "    SELECT id_wydawnictwo\n" +
                "    FROM wydawnictwo\n" +
                "    WHERE nazwa_wydawnictwa = ?\n" +
                ") w;\n" +
                "\n";
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
            int ret =  statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return 0;
    }

    public int delete_one_record_from_database(String katalog, String egzemplarz)
    {
        connectToDatabase();
        String insertUserSQL = "DELETE FROM egzemplarze\n" +
                "WHERE katalog_id_katalog IN (\n" +
                "    SELECT id_katalog\n" +
                "    FROM katalog\n" +
                "    WHERE nazwa = ?\n" +
                ")\n" +
                "AND id_egzemplarze = ?;\n";


        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, katalog);
            statement.setString(2, egzemplarz);
            int ret =  statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return 0;
    }

    public int delete_one_position_from_database(String nazwa, String isbn, String nazwa_gatunku, String nazwa_wydawnictwa)
    {
        connectToDatabase();
        String insertUserSQL = "DELETE FROM katalog\n" +
                "WHERE nazwa = ?\n" +
                "  AND isbn = ?\n" +
                "  AND gatunek_id_gatunek = (\n" +
                "    SELECT id_gatunek\n" +
                "    FROM gatunek\n" +
                "    WHERE nazwa_gatunku = ?\n" +
                "  )\n" +
                "  AND wydawnictwo_id_wydawnictwo = (\n" +
                "    SELECT id_wydawnictwo\n" +
                "    FROM wydawnictwo\n" +
                "    WHERE nazwa_wydawnictwa = ?\n" +
                "  );\n";


        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, nazwa);
            statement.setString(2, isbn);
            statement.setString(3, nazwa_gatunku);
            statement.setString(4, nazwa_wydawnictwa);
            int ret =  statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return 0;
    }


    public int modify_egzemplarz(String czy_dostepne, String lokalizacja, String katalog, String id_egzemplarze)
    {
        connectToDatabase();
        String insertUserSQL = "UPDATE egzemplarze\n" +
                "SET czy_dostepne = ?,\n" +
                "    lokalizacja = ?\n" +
                "    \n" +
                "WHERE katalog_id_katalog IN (\n" +
                "    SELECT k.id_katalog\n" +
                "    FROM katalog k\n" +
                "    WHERE k.nazwa = ?\n" +
                ")\n" +
                "AND id_egzemplarze = ?;\n";


        try {
            PreparedStatement statement = connection.prepareStatement(insertUserSQL);
            statement.setString(1, czy_dostepne);
            statement.setString(2, lokalizacja);
            statement.setString(3, katalog);
            statement.setString(4, id_egzemplarze);
            int ret =  statement.executeUpdate();
            closeConnection();
            return ret;
        } catch (SQLException e) {
            /*Jezeli wystapi blad, to oznacza ze taki uzytkownik istnieje. Catch jest pusty, poniewaz dalej funkcja zamknie
            polaczenie i zwroci false, a nastepnie funkcja z registercontroller pokaze monit */
        }
        closeConnection();
        return 0;

    }
}



