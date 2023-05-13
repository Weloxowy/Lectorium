package org.example.home;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Main;

import static org.example.Main.dbload;

public class homecontroller extends home{

    @FXML
    public void font(){
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),14);
        nametag.setFont(ssp_sb_h1);
        labelbiblioteka.setFont(pop_b_h1);
        labelglowna.setFont(pop_b_h1);
        labelkatalog.setFont(pop_b_h1);
        labelkontakt.setFont(pop_b_h1);
        labelkategorie.setFont(pop_b_h1);
        labelnowosci.setFont(pop_b_h1);
        labelrezerwacje.setFont(pop_b_h1);
        labelwypozyczenia.setFont(pop_b_h1);
        searchbar.setFont(pop_r_h1);
        biblioteka_title.setFont(pop_b_h1);
        biblioteka_desc.setFont(pop_r_h2);
    }
    @FXML
    private ImageView avatar = new ImageView();

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
    private Text biblioteka_desc;

    @FXML
    private ImageView biblioteka_photo;

    @FXML
    private Label biblioteka_title;

    @FXML
    private ImageView image_a1;

    @FXML
    private ImageView image_b1;

    @FXML
    private ImageView image_b2;

    @FXML
    private VBox vbox;

    @FXML
    private ImageView search_button;

    @FXML
    private AnchorPane anchor;

    @FXML
    void searchbar_exited(MouseEvent event) {
        searchbar.setFocusTraversable(false);
    }
    public void init(String imie, String nazwisko, MouseEvent event, Image image){
        nametag.setText(imie+" "+nazwisko);
        searchbar_exited(event);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        images_view();
    }

    void avatar_view(){
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    void images_view(){
        double centerXa1 = image_a1.getBoundsInLocal().getWidth();
        double centerYa1 = image_a1.getBoundsInLocal().getHeight();
        Rectangle rectanglea1 = new Rectangle(centerXa1, centerYa1);
        rectanglea1.setArcWidth(20.0);
        rectanglea1.setArcHeight(20.0);
        //rectangle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
        image_a1.setClip(rectanglea1);

        double centerXb1 = image_b1.getBoundsInLocal().getWidth();
        double centerYb1 = image_b1.getBoundsInLocal().getHeight();
        Rectangle rectangleb1 = new Rectangle(centerXb1, centerYb1);
        rectangleb1.setArcWidth(20.0);
        rectangleb1.setArcHeight(20.0);
        //rectangle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
        image_b1.setClip(rectangleb1);

        double centerXb2 = image_b2.getBoundsInLocal().getWidth();
        double centerYb2 = image_b2.getBoundsInLocal().getHeight();
        Rectangle rectangleb2 = new Rectangle(centerXb2, centerYb2);
        rectangleb2.setArcWidth(20.0);
        rectangleb2.setArcHeight(20.0);
        //rectangle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
        image_b2.setClip(rectangleb2);

        double centerXbib = biblioteka_photo.getBoundsInLocal().getWidth();
        double centerYbib = biblioteka_photo.getBoundsInLocal().getHeight();
        Rectangle rectanglebib = new Rectangle(centerXbib, centerYbib);
        rectanglebib.setArcWidth(20.0);
        rectanglebib.setArcHeight(20.0);
        biblioteka_photo.setClip(rectanglebib);
    }

    @FXML
    void search_init(MouseEvent event){
        String query = searchbar.getText();
        katalog_clicked(event,query);
    }


    @FXML
    void menu_panels(MouseEvent event){
        if(event.getSource().equals(image_a1)){ //pokaz wyszukiwania dla autora Houellebecq
            String query = "Houellebecq";
            katalog_clicked(event,query);
        }
        if(event.getSource().equals(image_b1)){ //pokaz ksiazke o id 130
            String query = "Wilcza Rzeka";
            katalog_item(event,130);
        }
        if(event.getSource().equals(image_b2)){ //pokaz wyszukiwania dla autora King
            String query = "King";
            katalog_clicked(event,query);
        }
    }
    //TODO dodać menu slider
}
