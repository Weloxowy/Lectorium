package org.example.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.Main;
import org.example.db.dbloader;

import java.util.ArrayList;

import static org.example.Main.dbload;

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

    public void init(){
        Katalog_lista();
    }
    public void Katalog_lista()
    {
        dbload.print_book();
        System.out.println("test");
        ObservableList<String[]> items = null;
        System.out.println(dbload.array.size());

        for(String tab[]: dbload.array) {
            String finalstr = "";
            for (String text: tab) {
                //lista.getItems().add(text);
                finalstr = finalstr + " " + text;
            }
            lista.getItems().add(finalstr);
        }
    }

    private String[] concatenateArrays(ArrayList<String[]> arrays) {
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }

        String[] result = new String[totalLength];
        int currentIndex = 0;

        for (String[] array : arrays) {
            for (String str : array) {
                result[currentIndex++] = str;
            }
        }

        return result;
    }

}
