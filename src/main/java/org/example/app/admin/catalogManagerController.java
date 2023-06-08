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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
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
    private AnchorPane root_anchor;

    @FXML
    private GridPane grid;

    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista(anchortable, searchbar1);
        pane_id_masked.setVisible(false);
        labelwypozyczenia.setStyle("-fx-text-fill:#808080");
        root_anchor.setTopAnchor(grid,0.0);
        root_anchor.setLeftAnchor(grid,0.0);
        root_anchor.setRightAnchor(grid,0.0);
        root_anchor.setBottomAnchor(grid,0.0);
        root_anchor.setTopAnchor(pane_id_masked,0.0);
        root_anchor.setLeftAnchor(pane_id_masked,0.0);
        root_anchor.setRightAnchor(pane_id_masked,0.0);
        root_anchor.setBottomAnchor(pane_id_masked,0.0);
    }

    @FXML
    public void font(Scene scene) {
        super.font(scene);
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 20);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 14);
        Label pane_tytul = (Label) scene.lookup("#pane_tytul");
        pane_tytul.setFont(pop_b_h2);
        TextField pane_txt_1 = (TextField) scene.lookup("#pane_txt_1");
        pane_txt_1.setFont(pop_r_h2);
        TextField pane_txt_2 = (TextField) scene.lookup("#pane_txt_2");
        pane_txt_2.setFont(pop_r_h2);
        TextField pane_txt_3 = (TextField) scene.lookup("#pane_txt_3");
        pane_txt_3.setFont(pop_r_h2);
        TextField pane_txt_4 = (TextField) scene.lookup("#pane_txt_4");
        pane_txt_4.setFont(pop_r_h2);
        TextField pane_txt_5 = (TextField) scene.lookup("#pane_txt_5");
        pane_txt_5.setFont(pop_r_h2);
        Button pane_button = (Button) scene.lookup("#pane_button");
        pane_button.setFont(pop_b_h2);
        Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
        pane_result_msg.setFont(pop_b_h2);

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
    public void dodaj_egz_button() //funkcja ustawiajaca dodawanie egzemplarza
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Dodaj egzemplarz książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki"); //ustawiamy to co jest widoczne jak pole jest puste
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj lokalizację egzemplarza");
        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Dodaj egzemplarz");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.add_to_database("T", pane_txt_2.getText(),pane_txt_1.getText());
            if(ret>0){
                pane_result_msg.setText("Egzemplarz został dodany");
            }
            else{
                pane_result_msg.setText("Egzemplarz nie został dodany");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    @FXML
    public void usun_egz_button() //funkcja ustawiajaca dodawanie egzemplarza
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Usuń egzemplarz książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz usunąć"); //ustawiamy to co jest widoczne jak pole jest puste
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj id egzemplarza");
        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Usuń egzemplarz");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.delete_one_record_from_database(pane_txt_1.getText(),pane_txt_2.getText());
            if(ret>0){
                pane_result_msg.setText("Egzemplarz został usunięty");
            }
            else{
                pane_result_msg.setText("Egzemplarz nie został usunięty");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }


    @FXML
    public void usun_pozycje_button() //funkcja ustawiajaca dodawanie egzemplarza
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Usuń pozycje książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz usunąć"); //ustawiamy to co jest widoczne jak pole jest puste

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj isbn książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj nazwę gatunku książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj nazwę wydawnictwa książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Usuń pozycje");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.delete_one_position_from_database(pane_txt_1.getText(),pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText());
            if(ret>0){
                pane_result_msg.setText("Pozycja usunięta");
            }
            else{
                pane_result_msg.setText("Pozycja nie została usunięta");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    @FXML
    public void zmodyfikuj_egzemplarze() //funkcja ustawiajaca dodawanie egzemplarza
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Zmodyfikuj egzemplarze książek");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj id egzemplarza"); //ustawiamy to co jest widoczne jak pole jest puste

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj czy książka jest dostępna");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj lokalizacje książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj katalog książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj egzemplarz");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.modify_egzemplarz(pane_txt_2.getText(),pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_1.getText());
            if(ret>0){
                pane_result_msg.setText("Modyfikacja udana");
            }
            else{
                pane_result_msg.setText("Modyfikacja się nie powiodła");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    @FXML
    public void add_position() //funkcja ustawiajaca dodawanie egzemplarza
    {
        //String nazwa, String rok_wydania, String wydanie, String isbn, String jezyk, String uwagi, String imie_autora, String nazwisko_autora, String nazwa_gatunku, String nazwa_wydawnictwa
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Dodaj pozycje książki");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj nazwę książki którą chcesz dodac"); //ustawiamy to co jest widoczne jak pole jest puste

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj rok wydania książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj wydanie książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj isbn książki");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj język książki");

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(1.0);
        pane_txt_6.setPromptText("Uwagi");

        TextField pane_txt_7 = (TextField) pane_id_masked.lookup("#pane_txt_7");
        pane_txt_7.setOpacity(1.0);
        pane_txt_7.setPromptText("Podaj imię autora");

        TextField pane_txt_8 = (TextField) pane_id_masked.lookup("#pane_txt_8");
        pane_txt_8.setOpacity(1.0);
        pane_txt_8.setPromptText("Podaj nazwisko autora");

        TextField pane_txt_9 = (TextField) pane_id_masked.lookup("#pane_txt_9");
        pane_txt_9.setOpacity(1.0);
        pane_txt_9.setPromptText("Podaj gatunek książki");

        TextField pane_txt_10 = (TextField) pane_id_masked.lookup("#pane_txt_10");
        pane_txt_10.setOpacity(1.0);
        pane_txt_10.setPromptText("Podaj wydawnictwo książki");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Dodaj pozycje");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.add_one_record_from_catalog(pane_txt_1.getText(),pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(),pane_txt_5.getText(),pane_txt_6.getText(),pane_txt_7.getText(),pane_txt_8.getText(),pane_txt_9.getText(),pane_txt_10.getText());
            if(ret>0){
                pane_result_msg.setText("Pozycja dodana");
            }
            else{
                pane_result_msg.setText("Pozycji nie udało się dodać");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    @FXML
    public void zmodyfikuj_pozycje() //funkcja ustawiajaca dodawanie egzemplarza
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul"); //lapiemy label i ustawiamy tytul
        pane_tytul.setText("Zmodyfikuj pozycje książek");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1"); //lapiemy pole i ustawiamy widocznosc na 1
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj id katalogu"); //ustawiamy to co jest widoczne jak pole jest puste

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj rok wydania książki");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj wydanie książki");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj isbn książki");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj język książki");

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(1.0);
        pane_txt_6.setPromptText("Uwagi");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj pozycje");
        pane_button.setOnMouseClicked(event -> { //reakcja na wcisniecie guzika dodaj w lambdzie; dzieki temu bedzie indywidualna dla kazdej funkcji
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = dbload.modify_position(pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText(), pane_txt_6.getText(), pane_txt_1.getText());
            if(ret>0){
                pane_result_msg.setText("Modyfikacja udana");
            }
            else{
                pane_result_msg.setText("Modyfikacja się nie powiodła");
            }
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event2 -> pane_result_msg.setOpacity(0.0)
            ));
            pane_result_msg.setOpacity(1.0);

            timeline.play();
        });
        pane_id_masked.setVisible(true);
    }

    @FXML
    public void hide_pane(MouseEvent event){ //funkcja chowajaca pola; dla wszystkich guzikow; wywolywana jak klikniemy x
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(0.0);
        pane_txt_1.clear();
        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(0.0);
        pane_txt_2.clear();
        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(0.0);
        pane_txt_3.clear();
        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(0.0);
        pane_txt_4.clear();
        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(0.0);
        pane_txt_5.clear();

        TextField pane_txt_6 = (TextField) pane_id_masked.lookup("#pane_txt_6");
        pane_txt_6.setOpacity(0.0);
        pane_txt_6.clear();

        TextField pane_txt_7 = (TextField) pane_id_masked.lookup("#pane_txt_7");
        pane_txt_7.setOpacity(0.0);
        pane_txt_7.clear();

        TextField pane_txt_8 = (TextField) pane_id_masked.lookup("#pane_txt_8");
        pane_txt_8.setOpacity(0.0);
        pane_txt_8.clear();

        TextField pane_txt_9 = (TextField) pane_id_masked.lookup("#pane_txt_9");
        pane_txt_9.setOpacity(0.0);
        pane_txt_9.clear();

        TextField pane_txt_10 = (TextField) pane_id_masked.lookup("#pane_txt_10");
        pane_txt_10.setOpacity(0.0);
        pane_txt_10.clear();
        Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
        pane_result_msg.setOpacity(0.0);
        ImageView pane_add_cover = (ImageView) pane_id_masked.lookup("#pane_add_cover");
        pane_add_cover.setOpacity(0.0);
        pane_id_masked.setVisible(false);
    }


}
