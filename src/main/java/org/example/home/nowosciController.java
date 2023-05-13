package org.example.home;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.example.Katalog;
import org.example.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.Main.dbload;

public class nowosciController extends home{

    @FXML
    private ImageView avatar; //TODO labele z katalogami zostały przejete przez metode font w klasie home. zrob tak z reszta kontrolerów oraz zrob dziedziczenie lokalnych fontow po tym z home'a

    @FXML
    private Label nametag;

    @FXML
    private TextField searchbar;

    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox3;
    @FXML
    private VBox vbox4;
    @FXML
    private VBox vbox5;
    @FXML
    private VBox vbox6;
    @FXML
    private VBox vbox7;
    @FXML
    private VBox vbox8;

    @FXML
    private AnchorPane  nowosci_anchor;

    @FXML
    private GridPane grid_nowosci;

    @FXML
    void search_init(MouseEvent event){
        String query = searchbar.getText();
        katalog_clicked(event,query);
    }


    @FXML
    void searchbar_exited(MouseEvent event) {

    }

    @Override
    void font(Scene scene) {
        super.font(scene);
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Label header = (Label) grid_nowosci.lookup("#nowosci");
        header.setFont(ssp_sb_h1);
    }

    public void init(String imie, String nazwisko, MouseEvent event, Image image){
        dbload.get_top();
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        int w = Main.user.getId();
        System.out.println(w);
        int i=1;
        grid_nowosci.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid_nowosci.setPrefHeight(Region.USE_COMPUTED_SIZE);

        nowosci_anchor.setTopAnchor(grid_nowosci, 10.0);
        nowosci_anchor.setLeftAnchor(grid_nowosci, 10.0);
        nowosci_anchor.setRightAnchor(grid_nowosci, 10.0);
        nowosci_anchor.setBottomAnchor(grid_nowosci, 10.0);

        grid_nowosci.setHgap(55.0);
        grid_nowosci.setVgap(30.0);
        //dorzucic lambde do sledzenia lewego gornego rogu kazdego vboxa i umieszczanie tam cyfry
        Map<Integer, VBox> vboxMap = new HashMap<>();
            vboxMap.put(1, vbox1);
            vboxMap.put(2, vbox2);
            vboxMap.put(3, vbox3);
            vboxMap.put(4, vbox4);
            vboxMap.put(5, vbox5);
            vboxMap.put(6, vbox6);
            vboxMap.put(7, vbox7);
            vboxMap.put(8, vbox8);

        for (String[] tab : dbload.top) {
            Integer id_katalog = Integer.valueOf(tab[0]);
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            System.out.println(nazwa_autora);
            VBox vbox = vboxMap.get(i);
            setLabelText(vbox,nazwa,nazwa_autora,id_katalog,i);
            i++;
        }
    }

    public void setLabelText(VBox vb, String nazwa, String autor,int id_katalog, int i) {
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Label label_a = (Label) vb.lookup("#author"+i);
        if (label_a != null) {
            label_a.setText(autor);
            label_a.setFont(pop_r_h1);
        }
        Label label_n = (Label) vb.lookup("#title"+i);
        if (label_n != null) {
            label_n.setText(nazwa);
            label_a.setFont(pop_r_h1);
        }
        dbload.get_cover(id_katalog);
        ImageView image = (ImageView) vb.lookup("#cover"+i);
        if(image != null){
            image.setImage(Main.kat.getOkladka());
            double centerX = image.getBoundsInLocal().getWidth();
            double centerY = image.getBoundsInLocal().getHeight();
            Rectangle rectangle = new Rectangle(centerX, centerY);
            rectangle.setArcWidth(10.0);
            rectangle.setArcHeight(10.0);
            image.setClip(rectangle);
        }
    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void refer_to_book(MouseEvent event){
        System.out.println( event.getSource());
        Map<Integer, VBox> vboxMap = new HashMap<>();
        vboxMap.put(1, vbox1);
        vboxMap.put(2, vbox2);
        vboxMap.put(3, vbox3);
        vboxMap.put(4, vbox4);
        vboxMap.put(5, vbox5);
        vboxMap.put(6, vbox6);
        vboxMap.put(7, vbox7);
        vboxMap.put(8, vbox8);
        int i=1;
        if(dbload.array.size() == 0){
            dbload.print_book();
        }
        for (Map.Entry<Integer, VBox> act : vboxMap.entrySet()) {
            if (event.getSource().equals(act.getValue())) {
                katalog_item(event,dbload.array.size()-i+1);
            }
            i++;
        }
    }
}
