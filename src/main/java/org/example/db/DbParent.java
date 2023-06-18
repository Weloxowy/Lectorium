package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa DbParent jest klasą nadrzędną dla klas zawierających funkcje, które nawiązują połączenie
 * z bazą danych i dokonują na niej jakichś operacji.
 */
public class DbParent {

    /**
     * Pole {@code connection} reprezentuje połączenie do bazy danych.
     * Jest oznaczone jako chronione (protected), co oznacza, że jest dostępne dla klas dziedziczących z tej klasy oraz dla klas w tym samym pakiecie.
     */
    protected Connection connection;

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

}



