package org.example.db;

import org.example.Main;
import org.example.home.homecontroller;
import org.example.verify.logincontroller;

import javax.swing.plaf.nimbus.State;
import java.io.File;
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
            System.out.println("Error inserting user: " + e.getMessage());
            System.exit(100);
        }
        closeConnection();
        return false;
    }

    public void load_file(){
        
    }
}

