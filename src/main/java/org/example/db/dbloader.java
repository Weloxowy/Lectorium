package org.example.db;
import javafx.scene.image.Image;
import org.example.Main;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class dbloader {
    private Connection connection;
    private Statement stat;
    private ArrayList<String[]> data;

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
            System.out.println("Error searching for user: " + e.getMessage());
            System.exit(1);
        }

    }

    public boolean testRegister(String login){
        connectToDatabase();
        String testUserSQL = "SELECT count(id_uzytkownicy) as cnt from uzytkownicy where login=?";
        try{
            PreparedStatement statement = connection.prepareStatement(testUserSQL);
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("cnt");
                closeConnection();
                if(id==0){
                    return true;
                }
                if(id>0 || id<0){
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
                //resultSet.close();  //jezeli nadal bedzie wywalalo uzytkownikow z ret 0 - odkomentowac linie
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
        String print = "SELECT katalog.id_katalog, autor.tytul_autora ,autor.imie_autora, autor.nazwisko_autora, katalog.nazwa, katalog.rok_wydania, katalog.wydanie, katalog.isbn, katalog.jezyk, katalog.uwagi, wydawnictwo.nazwa_wydawnictwa FROM katalog, autor, wydawnictwo WHERE katalog.autor_id_autor = autor.id_autor AND katalog.wydawnictwo_id_wydawnictwo = wydawnictwo.id_wydawnictwo";
        try {
            array.clear(); //unikamy ładowania wiele razy tych samych rekordow
            PreparedStatement statement = connection.prepareStatement(print);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_katalog = resultSet.getInt("id_katalog");
                final String nazwa = resultSet.getString("nazwa");
                String nazwa_autora = "";
                nazwa_autora = nazwa_autora.concat(resultSet.getString("imie_autora") +" "+ resultSet.getString("nazwisko_autora"));
                final String rok_wydania = resultSet.getString("rok_wydania");
                final String wydanie = resultSet.getString("wydanie");
                final String isbn = resultSet.getString("isbn");
                final String jezyk = resultSet.getString("jezyk");
                final String uwagi = resultSet.getString("uwagi");
                final String nazwa_wydawnictwa = resultSet.getString("nazwa_wydawnictwa");
                String[] row = {String.valueOf(id_katalog),nazwa,nazwa_autora, rok_wydania, wydanie, isbn, jezyk, uwagi, nazwa_wydawnictwa};
                array.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }

        closeConnection();
    }

    public ArrayList<String[]> getData() {
        return data;
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
                }
                else{
                    Image def = new Image("/res/icons/dark/avatar.png");
                    Main.user.setImage(def);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }

    public void get_cover(int id){
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
                    System.out.println(image);//usuniecie
                }
                else{
                    Image def = new Image("/fxml.home/Brakokładki.jpg");
                    Main.kat.setOkladka(def);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }

    public ArrayList<String[]> copy = new ArrayList<String[]>();

    public void print_copies(int id) {
        System.out.println("ID" +id);
        connectToDatabase();
        String print = "SELECT katalog.nazwa, egzemplarze.id_egzemplarze, egzemplarze.lokalizacja, egzemplarze.czy_dostepne,\n" +
                "  CASE WHEN wypozyczenia.data_zwrotu < date('now') THEN NULL ELSE wypozyczenia.data_zwrotu END AS data_zwrotu\n" +
                "FROM katalog\n" +
                "JOIN egzemplarze ON egzemplarze.katalog_id_katalog = katalog.id_katalog\n" +
                "LEFT JOIN wypozyczenia ON wypozyczenia.egzemplarze_id_egzemplarze = egzemplarze.id_egzemplarze\n" +
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
                final String czy_dostepne = resultSet.getString("czy_dostepne");
                final String data_zwrotu = resultSet.getString("data_zwrotu");
                String[] row = {nazwa,String.valueOf(id_egzemplarze),lokalizacja,czy_dostepne,data_zwrotu};
                copy.add(row);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while dowloading data from DB: " + e.getMessage());
            System.exit(100);
        }
        closeConnection();
    }

}


