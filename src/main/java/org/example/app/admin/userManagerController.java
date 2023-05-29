package org.example.app.admin;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.example.User;
import org.example.app.appParent;


public class userManagerController extends appParent {
    @FXML
    private ImageView avatar = new ImageView();

    @FXML
    private Label labelglowna;


    @FXML
    private TextField searchbar;

    @FXML
    private Label nametag;

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
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

}
