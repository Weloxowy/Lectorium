package org.example.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class katalogcontroller {

    @FXML
    private ImageView avatar;

    @FXML
    private Label labelbiblioteka;

    @FXML
    private Label labelglowna;

    @FXML
    private Label labelkatalog;

    @FXML
    private Label labelkategorie;

    @FXML
    private Label labelkontakt;

    @FXML
    private Label labelnowosci;

    @FXML
    private Label labelrezerwacje;

    @FXML
    private Label labelwypozyczenia;

    @FXML
    private ListView<String> lista;

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;


    public void Katalog_lista(int id_katalog, String nazwa, String rok_wydania, String wydanie, String isbn, String jezyk)
    {

        ObservableList<String> items = FXCollections.observableArrayList (
                id_katalog+" | "+nazwa+ "", "", "");

        lista.setItems(items);
    }

}
