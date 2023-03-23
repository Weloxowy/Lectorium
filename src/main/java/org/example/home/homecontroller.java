package org.example.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.Main;
import org.example.verify.registercontroller;

import java.io.IOException;

public class homecontroller {

    @FXML
    public void font(){
        Font ssp_semibold = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-SemiBold.ttf"), 25);
        Font ssp_reg = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-Regular.ttf"), 13);
        Font pop_med = Font.loadFont(getClass().getResourceAsStream("/res/Poppins-Medium.ttf"), 13);
        nametag.setFont(ssp_semibold);
        labelbiblioteka.setFont(pop_med);
        labelglowna.setFont(pop_med);
        labelkatalog.setFont(pop_med);
        labelkontakt.setFont(pop_med);
        labelkategorie.setFont(pop_med);
        labelnowosci.setFont(pop_med);
        labelrezerwacje.setFont(pop_med);
        labelwypozyczenia.setFont(pop_med);
    }
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
    private ImageView logo;

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private VBox vbox;

    public void init(String imie, String nazwisko){
        nametag.setText(imie+" "+nazwisko);
    }

    @FXML
    void katalog_klik(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller controller = loader.getController();
            controller.Katalog_lista(
                    Main.kat.getId_katalog(),
                    Main.kat.getNazwa(),
                    Main.kat.getRok_wydania(),
                    Main.kat.getWydanie(),
                    Main.kat.getIsbn(),
                    Main.kat.getJezyk());
            // TODO: 23.03.2023 Dodać arraya do przechowywania wszystkich danych z tabeli, a następnie przesłać go do katalog.fxml 
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

}
