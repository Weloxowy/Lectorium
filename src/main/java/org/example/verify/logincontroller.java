package org.example.verify;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.db.dbloader;
import org.example.home.homecontroller;

import java.io.IOException;
import java.util.Objects;

public class logincontroller {
    @FXML
    private ImageView closebutton;

    @FXML
    private PasswordField haslo;

    @FXML
    private TextField login;

    @FXML
    private ImageView minimizebutton;

    @FXML
    private Button submit;

    @FXML
    private Text switchtoregister;

    @FXML
    private Label zalogujsietext;


    @FXML
    private Label error;


    public void font() {
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"),25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"),14);
        zalogujsietext.setFont(ssp_sb_h1);
        submit.setFont(pop_r_h1);
        haslo.setFont(pop_r_h2);
        login.setFont(pop_r_h2);
        error.setFont(pop_b_h1);
        switchtoregister.setFont(pop_r_h2);
    }

    @FXML
    void onclosewindow() {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onminimalizewindow() {
        Stage stage = (Stage) minimizebutton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void buttonPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume(); // przechwycenie klawisza Enter
            submit.fireEvent(new MouseEvent(
                    MouseEvent.MOUSE_CLICKED,
                    100, 100, 0, 0, MouseButton.PRIMARY, 1,
                    false, false, false, false,false,false,false,false,false,false,null)); // wywołanie zdarzenia MouseEvent
        }
    }

    @FXML
    void onregisterclicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent;
        final double[] x = {0};
        final double[] y = {0};
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/register.fxml"));
            parent = loader.load();

            registercontroller controller = loader.getController();
            controller.font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        scene.setOnMousePressed(event2 -> {
            x[0] = event2.getSceneX();
            y[0] = event2.getSceneY();
        });
        scene.setOnMouseDragged(event3 -> {
            stage.setX(event3.getScreenX()- x[0]);
            stage.setY(event3.getScreenY()- y[0]);
        });
        stage.setScene(scene);
    }

    @FXML
    void onsubmit(MouseEvent event) {
        error.setOpacity(0.0);
        org.example.db.dbloader l = new dbloader();
        String log = login.getText();
        String has = haslo.getText();

        boolean res = l.tryLogin(log, has);
        if (res) {
            onsuccess(event); //przekazywanie wartosci z funkcji tryLogin?
        } else {
            onfailure();
        }

    }

    void onsuccess(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/home.fxml"));
            parent = loader.load();
            homecontroller controller = loader.getController();

            controller.init(Main.user.getImie(),Main.user.getNazwisko());
            stage.setResizable(true);
            stage.setFullScreen(false);
            stage.setTitle("Lectorium");
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxml.home/home.css")).toExternalForm());
            stage.setScene(scene);
            controller.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        stage.show();
    }

    void onfailure() {
        error.setOpacity(1.0);
    }
}

