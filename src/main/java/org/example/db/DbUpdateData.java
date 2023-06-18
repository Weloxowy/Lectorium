package org.example.db;

import org.example.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasa DbUpdateData to klasa dziedziczącej po klasie DbParent.
 * Posiada ona metody, które usuwają dane.
 *
 * @see org.example.db.DbParent
 */
public class DbUpdateData extends DbParent {

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
     * Metoda zmieniająca avatar użytkownika.
     *
     * @param imageData ciąg bitowy nowego obrazu
     * @param id        ID użytkownika
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
     * Metoda zmienia dane danego egzemplarza.
     *
     * @param czyDostepne   czy książka jest dostępna (parametr wykorzystywany przy wyświetlaniu stanu książki)
     * @param lokalizacja   lokalizacja, gdzie dana książka ma swoje miejsce w bibliotece
     * @param katalog       nazwa pozycji
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
     * @param wydanie    wydanie
     * @param isbn       kod ISBN
     * @param jezyk      język w którym książka jest napisana
     * @param uwagi      uwagi
     * @param idKatalog  id danej książki w katalogu
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
     * Metoda modyfikuje dane użytkownika, o podanym id_uzytkownicy.
     *
     * @param imie           imie użytkownika
     * @param nazwisko       nazwisko użytkownika
     * @param login          login użytkownika
     * @param haslo          hasło użytkownika
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
     * @param czy_admin      pole w bazie przyjmuje wartości "T" i "N". Inne są odrzucane.
     * @param id_uzytkownicy id użytkownika
     * @return ilość zmodyfikowanych rekordów
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
     * Metoda zmienia status użytkownika dając bądź odbierając możliwość zalogowania się użytkownika do programu.
     *
     * @param id_user id użytkownika
     * @param block   parametr "T" oznacza ustawienie blokady, "N" zdjęcie jej.
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
     * @param rentalDate    data końca wypożyczenia
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
}
