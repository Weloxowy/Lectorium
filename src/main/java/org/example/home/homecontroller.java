package org.example.home;

import javafx.fxml.FXML;
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

public class homecontroller {

    @FXML
    public void font(){
        Font name = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-SemiBold.ttf"), 25);
        Font category = Font.loadFont(getClass().getResourceAsStream("/res/SourceSerifPro-Regular.ttf"), 13);
        nametag.setFont(name);
        labelbiblioteka.setFont(category);
        labelglowna.setFont(category);
        labelkatalog.setFont(category);
        labelkontakt.setFont(category);
        labelkategorie.setFont(category);
        labelnowosci.setFont(category);
        labelrezerwacje.setFont(category);
        labelwypozyczenia.setFont(category);
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


}
