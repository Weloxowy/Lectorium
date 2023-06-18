package org.example.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasa DbDeleteData to klasa dziedziczącej po klasie DbParent.
 * Posiada ona metody, które usuwają dane.
 *
 * @see org.example.db.DbParent
 *
 */
public class DbDeleteData extends DbParent{
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
}
