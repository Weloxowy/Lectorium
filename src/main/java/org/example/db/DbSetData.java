package org.example.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Klasa DbSetData to klasa dziedziczącej po klasie DbParent.
 * Posiada ona metody, które dodają dane do bazy.
 *
 * @see org.example.db.DbParent
 */
public class DbSetData extends DbParent {

    /**
     * Metoda dodaje nowego użytkownika do bazy. Dodawany tak użytkownik nie musi spełniać żadnych warunków,
     * w przeciwieństwie do funkcji tryRegister, gdzie tam sprawdzanych jest kilka warunków.
     *
     * @param imie      imie użytkownika
     * @param nazwisko  nazwisko użytkownika
     * @param login     login użytkownika
     * @param haslo     hasło użytkownika
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
     * Metoda dodająca autora do tabeli autor.
     *
     * @param imie     imie autora
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
            if (result == 1)
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
            if (result == 1)
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
            if (result == 1)
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
     * Funkcja dodaje nowy egzemplarz do bazy danych.
     *
     * @param czy_dostepne status dostępności egzemplarza
     * @param lokalizacja  lokalizacja egzemplarza
     * @param katalog      nazwa katalogu, do którego należy egzemplarz
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
     * @param nazwa             nazwa ksiazki
     * @param rok_wydania       rok wydania
     * @param wydanie           wydanie
     * @param isbn              kod ISBN
     * @param jezyk             język książki
     * @param uwagi             uwagi odnośnie książki
     * @param imie_autora       imie autora
     * @param nazwisko_autora   nazwisko autora
     * @param nazwa_gatunku     nazwa gatunku książki
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
            polaczenie i zwroci false, a nastepnie funkcja z registerController pokaze monit */
        }
        closeConnection();
        return tab;
    }
}
