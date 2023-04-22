package org.example.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.verify.logincontroller;

import java.io.IOException;

public class homecontroller {

    public homecontroller() {
    }

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
    void searchbar_exited(MouseEvent event) {
        searchbar.setFocusTraversable(false);
    }
    public void init(String imie, String nazwisko, MouseEvent event, Image image){ //zamienic na image
        nametag.setText(imie+" "+nazwisko);
        searchbar_exited(event);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        images_view();
    }
    @FXML
    void logout_perform(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            parent = loader.load();
            logincontroller controller = loader.getController();
            controller.font();
            stage.setResizable(false);
            stage.setResizable(true);
            stage.isMaximized();
            stage.setFullScreen(false);
            stage.setTitle("Lectorium alpha");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
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
    }
    void avatar_view(){
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void katalog_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            kat.init_lista();
            kat.font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
    @FXML
    void contact_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/contact.fxml"));
                parent = loader.load();
                contactcontroller contact = loader.getController();
                contact.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }


    @FXML
    void kategorie_clicked(MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/kategorie.fxml"));
            parent = loader.load();
            kategoriecontroller controller = loader.getController();
            controller.font();
            controller.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

    @FXML
<<<<<<< src/main/java/org/example/home/homecontroller.java
    void katalog_clicked_with_param(MouseEvent event, String key) { //najpewniej do usuniecia
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
            kat.init_lista();
            kat.font();
            kat.searchbar_exited(key);
=======
    void contact_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/contact.fxml"));
            parent = loader.load();
            contactcontroller contact = loader.getController();
            contact.init(Main.user.getImie(),Main.user.getNazwisko(),event,Main.user.getImage());
>>>>>>> src/main/java/org/example/home/homecontroller.java
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }

<<<<<<< src/main/java/org/example/home/homecontroller.java
    @FXML
    void banner_clicked(MouseEvent event) { //TODO dokończyc
       if(event.getSource().equals(image_a1)){
           /*String text = "Houellebecq";
           katalog_clicked_with_param(event,text);*/
       }
       else if(event.getSource().equals(image_b1)){
           //System.out.println("BBB");
       }
       else if(event.getSource().equals(image_b2)){
           //System.out.println("CCC");
       }
    }
=======
>>>>>>> src/main/java/org/example/home/homecontroller.java
}
