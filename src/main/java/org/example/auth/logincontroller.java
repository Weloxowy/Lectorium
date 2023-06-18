package org.example.auth;

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
import org.example.User;
import org.example.app.PasswordSkin;
import org.example.db.DbAuth;
import org.example.db.DbParent;
import org.example.app.home.homecontroller;

import java.io.IOException;
import java.util.Objects;

/**
 * Kontroler interfejsu użytkownika obsługujący logowanie użytkownika.
 * Ta klasa działa jako kontroler dla pliku FXML i obsługuje różne zdarzenia
 * oraz interakcje użytkownika w interfejsie logowania.
 */
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

    /**
     * Metoda zwracająca pole hasło.
     *
     * @return Pole hasło.
     */
    public PasswordField getHaslo() {
        return haslo;
    }

    /**
     * Metoda ustawiająca pole hasło.
     *
     * @param haslo Pole hasło.
     */
    public void setHaslo(PasswordField haslo) {
        this.haslo = haslo;
    }


    /**
     * Metoda ustawiająca odpowiednie czcionki dla elementów interfejsu użytkownika.
     * Wczytuje czcionki z plików zasobów.
     */
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

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku zamknięcia okna.
     * Zamyka okno logowania.
     */
    @FXML
    void onclosewindow() {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku minimalizacji okna.
     * Minimalizuje okno logowania.
     */
    @FXML
    void onminimalizewindow() {
        Stage stage = (Stage) minimizebutton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Metoda obsługująca naciśnięcie przycisku na klawiaturze.
     * Przechwytuje naciśnięcie klawisza Enter i wywołuje zdarzenie kliknięcia przycisku submit.
     *
     * @param event Obiekt zdarzenia klawiatury.
     */
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

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku rejestracji.
     * Przechodzi do ekranu rejestracji.
     *
     * @param event Obiekt zdarzenia myszy.
     */
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
            controller.getHaslo().setSkin(new PasswordSkin(controller.getHaslo()));
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

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku submit (próba logowania).
     * Sprawdza wprowadzone dane logowania i podejmuje odpowiednie działania.
     *
     * @param event Obiekt zdarzenia myszy.
     */
    @FXML
    void onsubmit(MouseEvent event) {
        error.setOpacity(0.0);
        DbAuth auth = new DbAuth();
        String log = login.getText();
        String has = haslo.getText();

        boolean res = auth.tryLogin(log, has);
        if (res) {
            if(User.getInstance().getCzy_zablokowany().contentEquals("T")){
                onfailure(true);
            }
            else{
                onsuccess(event); //przekazywanie wartosci z funkcji tryLogin?
            }
        } else {
            onfailure(false);
        }

    }

    /**
     * Metoda obsługująca udane logowanie.
     * Otwiera nowe okno główne aplikacji po zalogowaniu.
     *
     * @param event Obiekt zdarzenia myszy.
     */
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

            controller.init(User.getInstance().getImie(),User.getInstance().getNazwisko());
            stage.setResizable(true);
            stage.setFullScreen(false);
            if(User.getInstance().getCzy_admin().contentEquals("T")){
                stage.setTitle("Lectorium (zalogowano jako administrator)");
            }else{
                stage.setTitle("Lectorium");
            }
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

    /**
     * Metoda obsługująca nieudane logowanie.
     * Wyświetla komunikat o błędzie logowania.
     *
     * @param blockade Informacja, czy konto jest zablokowane.
     */
    void onfailure(boolean blockade) {
        if(blockade==true){
            error.setText("Konto zablokowane");
        }
        else{
            error.setText("Błąd logowania");
        }
        error.setOpacity(1.0);
    }
}

