package org.example;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
public class Rezerwacje {

    SimpleStringProperty id_egzemplarze;
    SimpleStringProperty nazwa;
    SimpleStringProperty data_konca;
    SimpleStringProperty data_rezerwacji;
    SimpleStringProperty nazwa_autora;
    SimpleStringProperty przedluz_rezerwacje;
    SimpleStringProperty anuluj_rezerwacje;


    public Rezerwacje(String id_egz, String naz,String nazwa_autor, String data_ko, String data_rez , String prze_rez, String anul_rez)
    {
        this.id_egzemplarze = new SimpleStringProperty(id_egz);
        this.nazwa = new SimpleStringProperty(naz);
        this.nazwa_autora = new SimpleStringProperty(nazwa_autor);
        this.data_konca = new SimpleStringProperty(data_ko);
        this.data_rezerwacji = new SimpleStringProperty(data_rez);
        this.przedluz_rezerwacje = new SimpleStringProperty(prze_rez);
        this.anuluj_rezerwacje = new SimpleStringProperty(anul_rez);
    }

    public String getId_egzemplarze() {
        return id_egzemplarze.get();
    }

    public SimpleStringProperty id_egzemplarzeProperty() {
        return id_egzemplarze;
    }

    public void setId_egzemplarze(String id_egzemplarze) {
        this.id_egzemplarze.set(id_egzemplarze);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getData_konca() {
        return data_konca.get();
    }

    public SimpleStringProperty data_koncaProperty() {
        return data_konca;
    }

    public void setData_konca(String data_konca) {
        this.data_konca.set(data_konca);
    }

    public String getData_rezerwacji() {
        return data_rezerwacji.get();
    }

    public SimpleStringProperty data_rezerwacjiProperty() {
        return data_rezerwacji;
    }

    public void setData_rezerwacji(String data_rezerwacji) {
        this.data_rezerwacji.set(data_rezerwacji);
    }

    public String getNazwa_autora() {
        return nazwa_autora.get();
    }

    public SimpleStringProperty nazwa_autoraProperty() {
        return nazwa_autora;
    }

    public void setNazwa_autora(String nazwa_autora) {
        this.nazwa_autora.set(nazwa_autora);
    }

    public String getPrzedluz_rezerwacje() {
        return przedluz_rezerwacje.get();
    }

    public SimpleStringProperty przedluz_rezerwacjeProperty() {
        return przedluz_rezerwacje;
    }

    public void setPrzedluz_rezerwacje(String przedluz_rezerwacje) {
        this.przedluz_rezerwacje.set(przedluz_rezerwacje);
    }

    public String getAnuluj_rezerwacje() {
        return anuluj_rezerwacje.get();
    }

    public SimpleStringProperty anuluj_rezerwacjeProperty() {
        return anuluj_rezerwacje;
    }

    public void setAnuluj_rezerwacje(String anuluj_rezerwacje) {
        this.anuluj_rezerwacje.set(anuluj_rezerwacje);
    }
}
