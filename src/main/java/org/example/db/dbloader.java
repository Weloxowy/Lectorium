package org.example.db;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import org.example.Main;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.nio.file.Files;
import java.sql.*;
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
                if (id == 0) {
                    return true;
                }
                if (id > 0 || id < 0) {
                    return false;
                }
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
                final String full_name = (resultSet.getString("imie") + " " + resultSet.getString("nazwisko"));
                final String czy_admin = resultSet.getString("czy_admin");
                Main.user.setImie(name);
                Main.user.setNazwisko(last_name);
                Main.user.setCzy_admin(czy_admin);
                Main.user.setId(id);
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

    public boolean tryRegister(String imie, String nazwisko, String login, String password) { //TODO do dokonczenia
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

    public ArrayList<String[]> array = new ArrayList<String[]>();

    public void print_book() {
        connectToDatabase();
        String print = "SELECT katalog.id_katalog, autor.tytul_autora ,autor.imie_autora, autor.nazwisko_autora, katalog.nazwa, katalog.rok_wydania, \n" +
                "katalog.wydanie, katalog.isbn, katalog.jezyk, katalog.uwagi, wydawnictwo.nazwa_wydawnictwa, gatunek.nazwa_gatunku \n" +
                "FROM katalog, autor, wydawnictwo, gatunek WHERE katalog.autor_id_autor = autor.id_autor AND \n" +
                "katalog.wydawnictwo_id_wydawnictwo = wydawnictwo.id_wydawnictwo AND gatunek.id_gatunek = katalog.gatunek_id_gatunek;";
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
                    Main.user.setImage(image);
                } else {
                    Image def = new Image("/res/icons/dark/avatar.png");
                    Main.user.setImage(def);
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
                    System.out.println(image);//TODO usuniecie tej linijki, o ile wszystkie okladki sie dobrze laduja
                } else {
                    Image def = new Image("/fxml.home/Brakokładki.jpg");
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

    public ArrayList<String[]> copy = new ArrayList<String[]>();

    public void print_copies(int id) {
        connectToDatabase();
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, egzemplarze.lokalizacja, egzemplarze.czy_dostepne,\n" +
                "  CASE WHEN wypozyczenia.data_zwrotu < date('now') THEN NULL ELSE wypozyczenia.data_zwrotu END AS data_zwrotu\n" +
                "FROM katalog\n" +
                "JOIN egzemplarze ON egzemplarze.katalog_id_katalog = katalog.id_katalog\n" +
                "LEFT JOIN wypozyczenia ON wypozyczenia.egzemplarze_id_egzemplarze = egzemplarze.id_egzemplarze\n" +
                "WHERE katalog.id_katalog=?\n" +
                "ORDER BY data_zwrotu DESC;";
        try {
            copy.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final String nazwa = resultSet.getString("nazwa");
                int id_egzemplarze = resultSet.getInt("id_egzemplarze");
                final String lokalizacja = resultSet.getString("lokalizacja");
                final String czy_dostepne = resultSet.getString("czy_dostepne");
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

    public ArrayList<String[]> top = new ArrayList<String[]>();

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

    public ArrayList<String[]> categories = new ArrayList<String[]>();

    public void get_categories() {
        if (categories.isEmpty()) { //TODO: mozna tez dorzucic warunek czasowy ze czas od ostatniego zaladowania wyniosl minimum 1h
            connectToDatabase();
            String print = "SELECT gatunek.nazwa_gatunku , count(katalog.gatunek_id_gatunek) as ilosc \n" +
                    "from gatunek, katalog where gatunek.id_gatunek = katalog.gatunek_id_gatunek \n" +
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
            if(result>0){
                return true;
            }
            return false;
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

    //public ArrayList<String[]> ListHire = new ArrayList<String[]>();
    public ArrayList<String[]> ListHire = new ArrayList<String[]>();
    public void yourHireInformation(int id) {
        connectToDatabase();
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen \n" +
                "from katalog, egzemplarze, autor, wypozyczenia where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze\n" +
                "AND wypozyczenia.uzytkownicy_id_uzytkownicy = ? ";
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
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, wypozyczenia.data_wypozyczenia, wypozyczenia.data_zwrotu, wypozyczenia.ilosc_przedluzen \n" +
                "from katalog, egzemplarze, autor, wypozyczenia where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = wypozyczenia.egzemplarze_id_egzemplarze\n" +
                "AND wypozyczenia.data_zwrotu > date('now')  AND wypozyczenia.uzytkownicy_id_uzytkownicy = ?";
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
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, autor.imie_autora, autor.nazwisko_autora, rezerwacje.data_konca, rezerwacje.data_rezerwacji, rezerwacje.ilosc_przedluzen \n" +
                "from katalog, egzemplarze, autor, rezerwacje where katalog.autor_id_autor = autor.id_autor AND katalog.id_katalog = egzemplarze.katalog_id_katalog AND egzemplarze.id_egzemplarze = rezerwacje.egzemplarze_id_egzemplarze\n" +
                "AND rezerwacje.uzytkownicy_id_uzytkownicy = ?";
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
            if(result>0){
                return true;
            }
            return false;
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
            if(result>0){
                return true;
            }
            return false;
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

    public int update(int id)
    {
        connectToDatabase();
        String print = "UPDATE rezerwacje SET data_konca = DATE(data_konca, '+7 day'), ilosc_przedluzen = ilosc_przedluzen + 1 where rezerwacje.egzemplarze_id_egzemplarze = ? AND rezerwacje.uzytkownicy_id_uzytkownicy = ?;";
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            statement.setInt(2, Main.user.getId());
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            closeConnection();
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        return resultSet;
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
            if(result>0){
                return true;
            }
            return false;
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
        int resultSet = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, Main.user.getId());
            statement.setInt(2, id_egzemplarz);
            resultSet = statement.executeUpdate();
            statement.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            closeConnection();
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        return resultSet;
    }



}



