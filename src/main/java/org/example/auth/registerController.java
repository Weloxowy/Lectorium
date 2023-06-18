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
import org.example.app.home.homeController;
import org.example.db.DbAuth;

import java.io.IOException;
import java.util.Objects;

/**
 * Kontroler interfejsu użytkownika obsługujący rejestrację użytkownika.
 * Ta klasa działa jako kontroler dla pliku FXML i obsługuje różne zdarzenia
 * oraz interakcje użytkownika w interfejsie rejestracji.
 */
public class registerController {

    @FXML
    private ImageView closebutton;

    @FXML
    private PasswordField haslo;

    @FXML
    private TextField imie;

    @FXML
    private TextField login;

    @FXML
    private ImageView minimizebutton;

    @FXML
    private TextField nazwisko;

    @FXML
    private ImageView strzalkabutton;

    @FXML
    private Button submit;

    @FXML
    private Text switchtologin;

    @FXML
    private Label zarejestrujsietext;

    @FXML
    private Label error_dane;

    @FXML
    private Label error_haslo;

    @FXML
    private Label error_login;

    /**
     * Metoda ustawiająca odpowiednie czcionki dla elementów interfejsu użytkownika
     */
    public void font() {
        // Załadowanie czcionek z plików zasobów
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 14);

        // Przypisanie odpowiednich czcionek do poszczególnych elementów interfejsu
        zarejestrujsietext.setFont(ssp_sb_h1);
        submit.setFont(pop_r_h1);
        haslo.setFont(pop_r_h2);
        login.setFont(pop_r_h2);
        imie.setFont(pop_r_h2);
        nazwisko.setFont(pop_r_h2);
        switchtologin.setFont(pop_r_h2);
        error_dane.setFont(pop_b_h1);
        error_haslo.setFont(pop_b_h1);
        error_login.setFont(pop_b_h1);
    }

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku zamknięcia okna.
     * Zamyka okno logowania.
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
     */
    @FXML
    void onclosewindow(MouseEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda obsługująca zdarzenie kliknięcia przycisku minimalizacji okna.
     * Minimalizuje okno logowania.
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
     */
    @FXML
    void onminimalizewindow(MouseEvent event) {
        Stage stage = (Stage) minimizebutton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Obsługa zdarzenia rejestracji
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
     */
    @FXML
    void onregisterclicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent parent;
        final double[] x = {0};
        final double[] y = {0};
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            parent = loader.load();
            loginController controller = loader.getController();
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
            stage.setX(event3.getScreenX() - x[0]);
            stage.setY(event3.getScreenY() - y[0]);
        });
        stage.setScene(scene);
    }


    /**
     * Obsługa zdarzenia przycisku submit (próba rejestracji)
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
     */
    @FXML
    void onsubmit(MouseEvent event) {
        hidefailuresign(event);
        DbAuth auth = new DbAuth();
        String name = imie.getText();
        String last_name = nazwisko.getText();
        String log = login.getText();
        String has = haslo.getText();
        if (has.isEmpty() || log.isEmpty() || last_name.isEmpty() || name.isEmpty()) {
            error_dane.setOpacity(1.0);
            return;
        }
        if (has.length() < 5) {
            error_haslo.setOpacity(1.0);
            return;
        }
        if (auth.testRegister(log)) {
            boolean res = auth.tryRegister(name, last_name, log, has);
            res = auth.tryLogin(log, has);
            if (res) {
                onsuccess(event); //przekazywanie wartosci z funkcji tryLogin?
            } else {
                onfailure();
            }
        } else {
            error_login.setOpacity(1.0);
        }
    }

    /**
     * Funkcja obsługująca zdarzenie udanej rejestracji, czyli gdy funkcja tryLogin zwróci wartość true.
     * Funkcja uruchamia zamyka okno logowania i otwiera okno aplikacji.
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
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
            homeController controller = loader.getController();

            controller.init(User.getImie(), User.getNazwisko());
            stage.setResizable(true);
            stage.setFullScreen(false);
            if (User.getInstance().getCzy_admin().contentEquals("T")) {
                stage.setTitle("Lectorium (zalogowano jako administrator)");
            } else {
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
     * Obsługa zdarzenia nieudanej rejestracji polegająca na wyświetleniu komunikatu o błędzie.
     */
    void onfailure() {
        error_login.setOpacity(1.0);
    }

    /**
     * Ukrycie komunikatów o błędach
     *
     * @param event obiekt klasy MouseEvent wywoływany kliknięciem myszy
     */
    @FXML
    void hidefailuresign(MouseEvent event) {
        if (error_dane.getOpacity() == 1.0) {
            error_dane.setOpacity(0.0);
        }
        if (error_haslo.getOpacity() == 1.0) {
            error_haslo.setOpacity(0.0);
        }
        if (error_login.getOpacity() == 1.0) {
            error_login.setOpacity(0.0);
        }
    }

    /**
     * Funkcja która wywołuje funkcję submit poprzez emulację uruchomienia eventu kliknięcia guzika Submit.
     *
     * @param event obiekt klasy KeyEvent wywoływany kliknięciem przycisku na klawiaturze
     */
    //
    @FXML
    public void buttonPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume(); // przechwycenie klawisza Enter
            submit.fireEvent(new MouseEvent(
                    MouseEvent.MOUSE_CLICKED,
                    100, 100, 0, 0, MouseButton.PRIMARY, 1,
                    false, false, false, false, false, false, false, false, false, false, null)); // wywołanie zdarzenia MouseEvent
        }
    }

    public PasswordField getHaslo() {
        return haslo;
    }

    public void setHaslo(PasswordField haslo) {
        this.haslo = haslo;
    }
}