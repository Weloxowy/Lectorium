package org.example.db;

import javafx.scene.image.ImageView;
import org.example.Main;
import org.example.home.homecontroller;
import org.example.verify.logincontroller;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class dbloader {
    private Connection connection;
    private Statement stat;

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

    public void initializeDatabase() { //niewykorzystywane; zostawic na pozniej

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
                final String full_name = (resultSet.getString("imie")+" "+resultSet.getString("nazwisko"));
                final String czy_admin = resultSet.getString("czy_admin");
                Main.user.setImie(name);
                Main.user.setNazwisko(last_name);
                Main.user.setCzy_admin(czy_admin);
                Main.user.setId(id);
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

    public void load_file(){
        
    }

    public void print_book()
    {
        connectToDatabase();
        String print = "SELECT id_katalog, nazwa, rok_wydania, wydanie, isbn, jezyk FROM katalog";

        try {
            PreparedStatement statement = connection.prepareStatement(print);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { //Accepted login
                int id_katalog = resultSet.getInt("id_katalog");
                final String nazwa = resultSet.getString("nazwa");
                final String rok_wydania = resultSet.getString("rok_wydania");
                final String wydanie = resultSet.getString("wydanie");
                final String isbn = resultSet.getString("isbn");
                final String jezyk = resultSet.getString("jezyk");
                Main.kat.setId_katalog(id_katalog);
                Main.kat.setNazwa(nazwa);
                Main.kat.setRok_wydania(rok_wydania);
                Main.kat.setWydanie(wydanie);
                Main.kat.setIsbn(isbn);
                Main.kat.setJezyk(jezyk);
                resultSet.close();
                closeConnection();
            } else { //Wrong login or password
                System.out.println("Błąd logowania");
                resultSet.close();
                closeConnection();
            }
        } catch (SQLException e) { //Error while connecting with DB
            System.out.println("Error while connecting with DB: " + e.getMessage());
            System.exit(100);
        }

        closeConnection();
    }

    void getimage(int id, ImageView imageView) {
        connectToDatabase();//TODO jezeli zdjecie != NULL; dokonczyc konwersje bloba na image;zamienic parametry homecontroller
        String print = "SELECT avatar FROM uzytkownicy WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(print);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //Blob blob = resultSet.getBlob("avatar");
                //InputStream is = blob.getBinaryStream();
                //OutputStream os = new FileOutputStream(new File("photo.jpg"));
                //byte[] content = new byte[1024];
                //int size = 0;
                //while((size = is.read(content)) != -1 ){
                    //os.write(content,0,size);
                //}
                //Main.user.setImage(os);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting image: " + e.getMessage());
            System.exit(100);
        } finally {
            closeConnection();
        }
    }
}

