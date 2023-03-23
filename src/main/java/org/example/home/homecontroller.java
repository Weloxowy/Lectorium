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


}
