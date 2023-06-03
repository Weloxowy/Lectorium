package org.example.app.admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.User;
import org.example.app.appParent;

import static org.example.Main.dbload;


public class catalogManagerController extends appParent{
    @FXML
    private TextField add_egz;

    @FXML
    private AnchorPane anchortable;

    @FXML
    private ImageView avatar;

    @FXML
    private Button dodaj_egzemplarz;

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
    private ImageView logout;

    @FXML
    private Label nametag;

    @FXML
    private Pane pane_id_masked;

    @FXML
    private ImageView search_button;

    @FXML
    private ImageView search_button1;

    @FXML
    private TextField searchbar;

    @FXML
    private TextField searchbar1;

    @FXML
    private Button button_add_egz;

    @FXML
    private TextField localization_book;

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista(anchortable, searchbar1);
        pane_id_masked.setVisible(false);

        labelglowna.setStyle("-fx-text-fill:#808080");
    }

    @FXML
    public void font(Scene scene) {
        super.font(scene);

    }

    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void search_init(MouseEvent event) {
        String query = searchbar.getText();
        katalog_clicked(event, query);
    }

    @FXML
    public void dodaj_egz_button()
    {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(10),
                event -> pane_id_masked.setVisible(false)
        ));
        pane_id_masked.setVisible(true);




        timeline.play();
    }

    @FXML
    public void press_button_add_egz(MouseEvent event)
    {

            String dodaj_textField = add_egz.getText();
            String localization = localization_book.getText();
            dbload.add_to_database("T", localization, dodaj_textField);






    }


}
