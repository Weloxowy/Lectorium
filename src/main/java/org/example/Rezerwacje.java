package org.example;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa reprezentująca rezerwacje.
 */
public class Rezerwacje {

    SimpleStringProperty id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty data_konca;
    SimpleStringProperty data_rezerwacji;
    SimpleStringProperty nazwa_autora;
    SimpleStringProperty przedluz_rezerwacje;
    SimpleStringProperty anuluj_rezerwacje;


    /**
     * Tworzy nową instancję rezerwacji.
     *
     * @param id_egz        Identyfikator egzemplarza.
     * @param naz           Nazwa rezerwowanego elementu.
     * @param nazwa_autor   Nazwa autora.
     * @param data_ko       Data zakończenia rezerwacji.
     * @param data_rez      Data rozpoczęcia rezerwacji.
     * @param prze_rez      Informacja o możliwości przedłużenia rezerwacji.
     * @param anul_rez      Informacja o możliwości anulowania rezerwacji.
     */
    public Rezerwacje(String id_egz, String naz, String nazwa_autor, String data_ko, String data_rez , String prze_rez, String anul_rez)
    {
        this.id_egzemplarze = new SimpleStringProperty(id_egz);
        this.nazwa = new SimpleStringProperty(naz);
        this.nazwa_autora = new SimpleStringProperty(nazwa_autor);
        this.data_konca = new SimpleStringProperty(data_ko);
        this.data_rezerwacji = new SimpleStringProperty(data_rez);
        this.przedluz_rezerwacje = new SimpleStringProperty(prze_rez);
        this.anuluj_rezerwacje = new SimpleStringProperty(anul_rez);
    }

    /**
     * Zwraca identyfikator egzemplarza.
     *
     * @return Identyfikator egzemplarza.
     */
    public String getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    /**
     * Zwraca właściwość identyfikatora egzemplarza.
     *
     * @return Właściwość identyfikatora egzemplarza.
     */
    public SimpleStringProperty id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    /**
     * Ustawia identyfikator egzemplarza.
     *
     * @param id_egzemplarze Identyfikator egzemplarza do ustawienia.
     */
    public void setId_egzemplarze(String id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    /**
     * Zwraca nazwę rezerwowanego elementu.
     *
     * @return Nazwa rezerwowanego elementu.
     */
    public String getNazwa() {
        return nazwa.get();
    }

    /**
     * Zwraca właściwość nazwy rezerwowanego elementu.
     *
     * @return Właściwość nazwy rezerwowanego elementu.
     */
    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    /**
     * Ustawia nazwę rezerwowanego elementu.
     *
     * @param nazwa Nazwa rezerwowanego elementu do ustawienia.
     */
    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    /**
     * Zwraca datę zakończenia rezerwacji.
     *
     * @return Data zakończenia rezerwacji.
     */
    public String getData_konca() {
        return data_konca.get();
    }

    /**
     * Zwraca właściwość daty zakończenia rezerwacji.
     *
     * @return Właściwość daty zakończenia rezerwacji.
     */
    public SimpleStringProperty data_koncaProperty() {
        return data_konca;
    }

    /**
     * Ustawia datę zakończenia rezerwacji.
     *
     * @param data_konca Data zakończenia rezerwacji do ustawienia.
     */
    public void setData_konca(String data_konca) {
        this.data_konca.set(data_konca);
    }

    /**
     * Zwraca datę rozpoczęcia rezerwacji.
     *
     * @return Data rozpoczęcia rezerwacji.
     */
    public String getData_rezerwacji() {
        return data_rezerwacji.get();
    }

    /**
     * Zwraca właściwość daty rozpoczęcia rezerwacji.
     *
     * @return Właściwość daty rozpoczęcia rezerwacji.
     */
    public SimpleStringProperty data_rezerwacjiProperty() {
        return data_rezerwacji;
    }

    /**
     * Ustawia datę rozpoczęcia rezerwacji.
     *
     * @param data_rezerwacji Data rozpoczęcia rezerwacji do ustawienia.
     */
    public void setData_rezerwacji(String data_rezerwacji) {
        this.data_rezerwacji.set(data_rezerwacji);
    }

    /**
     * Zwraca nazwę autora.
     *
     * @return Nazwa autora.
     */
    public String getNazwa_autora() {
        return nazwa_autora.get();
    }

    /**
     * Zwraca właściwość nazwy autora.
     *
     * @return Właściwość nazwy autora.
     */
    public SimpleStringProperty nazwa_autoraProperty() {
        return nazwa_autora;
    }

    /**
     * Ustawia nazwę autora.
     *
     * @param nazwa_autora Nazwa autora do ustawienia.
     */
    public void setNazwa_autora(String nazwa_autora) {
        this.nazwa_autora.set(nazwa_autora);
    }

    /**
     * Zwraca informację o możliwości przedłużenia rezerwacji.
     *
     * @return Informacja o możliwości przedłużenia rezerwacji.
     */
    public String getPrzedluz_rezerwacje() {
        return przedluz_rezerwacje.get();
    }

    /**
     * Zwraca właściwość informacji o możliwości przedłużenia rezerwacji.
     *
     * @return Właściwość informacji o możliwości przedłużenia rezerwacji.
     */
    public SimpleStringProperty przedluz_rezerwacjeProperty() {
        return przedluz_rezerwacje;
    }

    /**
     * Ustawia informację o możliwości przedłużenia rezerwacji.
     *
     * @param przedluz_rezerwacje Informacja o możliwości przedłużenia rezerwacji do ustawienia.
     */
    public void setPrzedluz_rezerwacje(String przedluz_rezerwacje) {
        this.przedluz_rezerwacje.set(przedluz_rezerwacje);
    }

    /**
     * Zwraca informację o możliwości anulowania rezerwacji.
     *
     * @return Informacja o możliwości anulowania rezerwacji.
     */
    public String getAnuluj_rezerwacje() {
        return anuluj_rezerwacje.get();
    }

    /**
     * Zwraca właściwość informacji o możliwości anulowania rezerwacji.
     *
     * @return Właściwość informacji o możliwości anulowania rezerwacji.
     */
    public SimpleStringProperty anuluj_rezerwacjeProperty() {
        return anuluj_rezerwacje;
    }

    /**
     * Ustawia informację o możliwości anulowania rezerwacji.
     *
     * @param anuluj_rezerwacje Informacja o możliwości anulowania rezerwacji do ustawienia.
     */
    public void setAnuluj_rezerwacje(String anuluj_rezerwacje) {
        this.anuluj_rezerwacje.set(anuluj_rezerwacje);
    }
}