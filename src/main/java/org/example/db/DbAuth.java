package org.example.db;

import javafx.scene.image.Image;
import org.example.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa DbAuth to klasa dziedziczącej po klasie DbParent.
 * Posiada ona metody dotyczące logowania i rejestrowania się użytkowników.
 *
 * @see org.example.db.DbParent
 *
 */
public class DbAuth extends DbParent{

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
             * a nastepnie funkcja z registerController pokaze monit.
             */
        } finally {
            closeConnection();
        }
        return false;
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

}
