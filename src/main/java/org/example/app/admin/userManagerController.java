package org.example.app.admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.example.app.appParent;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.example.Main.*;


public class userManagerController extends appParent {
    @FXML
    private ImageView avatar = new ImageView();

    @FXML
    private Label labelglowna;


    @FXML
    private TextField searchbar;

    @FXML
    private Label nametag;

    @FXML
    private AnchorPane anchortable;

    @FXML
    private Label labelbiblioteka;

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
    public Label labelwypozyczenia;

    @FXML
    private ImageView logout;

    @FXML
    private ImageView search_button;

    @FXML
    private Pane pane_id_masked;

    @FXML
    private Pane pane_id_masked_log;

    @FXML
    public AnchorPane pane_search_result;

    @FXML
    private AnchorPane root_anchor;

    @FXML
    private GridPane grid;

    @FXML
    private TextField pane_search_user;
    @FXML
    final TableView<Users> lista = new TableView<>();

    @FXML
    final TableView<Wypozyczenia> lista_rent = new TableView<>();
    @FXML
    final TableView<Rezerwacje> lista_res = new TableView<>();
    @FXML
    private AnchorPane anchor;
    boolean activate_diff = false;
    public void init(String imie, String nazwisko) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(User.getInstance().getImage());
        avatar_view();
        Katalog_lista_adminUser();
        labelrezerwacje.setStyle("-fx-text-fill:#808080");
        pane_id_masked.setVisible(false);
        pane_id_masked_log.setVisible(false);
        AnchorPane.setTopAnchor(grid,0.0);
        AnchorPane.setLeftAnchor(grid,0.0);
        AnchorPane.setRightAnchor(grid,0.0);
        AnchorPane.setBottomAnchor(grid,0.0);
        AnchorPane.setTopAnchor(pane_id_masked,0.0);
        AnchorPane.setLeftAnchor(pane_id_masked,0.0);
        AnchorPane.setRightAnchor(pane_id_masked,0.0);
        AnchorPane.setBottomAnchor(pane_id_masked,0.0);
        AnchorPane.setTopAnchor(pane_id_masked_log,0.0);
        AnchorPane.setLeftAnchor(pane_id_masked_log,0.0);
        AnchorPane.setRightAnchor(pane_id_masked_log,0.0);
        AnchorPane.setBottomAnchor(pane_id_masked_log,0.0);

    }

    @FXML
    public void font(Scene scene) {
        super.font(scene);
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
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
        TextField pane_txt_6 = (TextField) scene.lookup("#pane_txt_6");
        pane_txt_6.setFont(pop_r_h2);
        TextField pane_txt_7 = (TextField) scene.lookup("#pane_txt_7");
        pane_txt_7.setFont(pop_r_h2);
        TextField pane_txt_8 = (TextField) scene.lookup("#pane_txt_8");
        pane_txt_8.setFont(pop_r_h2);
        TextField pane_txt_9 = (TextField) scene.lookup("#pane_txt_9");
        pane_txt_9.setFont(pop_r_h2);
        TextField pane_txt_10 = (TextField) scene.lookup("#pane_txt_10");
        pane_txt_10.setFont(pop_r_h2);
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

    public void Katalog_lista_adminUser() {
        db_getData.printUsers();
        ObservableList<Users> items = FXCollections.observableArrayList();

        TableColumn<Users, ?> name = new TableColumn<>("Imie");
        name.setMinWidth(anchortable.getPrefWidth()*0.25);
        name.setCellValueFactory(
                new PropertyValueFactory<>("imie_katalog"));

        TableColumn<Users, ?> surname = new TableColumn<>("Nazwisko");
        surname.setMinWidth(anchortable.getPrefWidth()*0.25);
        surname.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko_katalog"));

        TableColumn<Users, ?> id_user = new TableColumn<>("Id_użytkownika");
        id_user.setMinWidth(anchortable.getPrefWidth()*0.15);
        id_user.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));

        TableColumn<Users, ?> check_admin = new TableColumn<>("Czy administrator?");
        check_admin.setMinWidth(anchortable.getPrefWidth()*0.15);
        check_admin.setCellValueFactory(
                new PropertyValueFactory<>("czy_admin_katalog"));

        TableColumn<Users, ?> block_user = new TableColumn<>("Czy użytkownik jest zablokowany?");
        block_user.setMinWidth(anchortable.getPrefWidth()*0.15);
        block_user.setCellValueFactory(
                new PropertyValueFactory<>("czy_zablokowany"));


        for(String[] tab: db_getData.users) {
            int id = Integer.parseInt(tab[2]);
            String imie = tab[0];
            String nazwisko = tab[1];
            String czy_dostepne = tab[3];
            String czy_zablokowany = tab[4];
            items.add(new Users(imie,nazwisko,id,czy_dostepne,czy_zablokowany));

        }
        lista.setItems(items);
        lista.setFixedCellSize(30);
        if(lista.getColumns().size() == 0) {
            lista.getColumns().addAll(name, surname, id_user, check_admin, block_user);
        }
        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());
        anchortable.getChildren().clear();
        anchortable.getChildren().addAll(lista);
        AnchorPane.setLeftAnchor(anchortable, 0.0);
        AnchorPane.setBottomAnchor(anchortable, 0.0);
        AnchorPane.setRightAnchor(anchortable, 0.0);


        lista.getStylesheets().add("/fxml.home/home.css");

    }


    @FXML
    public void dodaj_uzytkownika()
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Dodawanie użytkownika");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj imie użytkownika");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj nazwisko");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj login użytkownika");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj hasło użytkownika");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj czy użytkownika ma być adminem");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Dodaj użytkownika");

        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(pane_txt_1);
        textFields.add(pane_txt_2);
        textFields.add(pane_txt_3);
        textFields.add(pane_txt_4);
        textFields.add(pane_txt_5);

        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");

            for (TextField field: textFields) {
                if(field.getText().isEmpty()) {
                    pane_result_msg.setText("Użytkownika nie udało sie dodać");
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        event2 -> pane_result_msg.setOpacity(0.0)
                    ));
                pane_result_msg.setOpacity(1.0);
                timeline.play();
                }}

            int ret = db_setData.addUser(pane_txt_1.getText(),pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText());
            if(ret>0){
                pane_result_msg.setText("Użytkownik dodany");
            }
            else{
                pane_result_msg.setText("Użytkownika nie udało sie dodać");
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
    public void zmodyfikuj_uzytkownika()
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Modyfikacja użytkownika");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj imie użytkownika");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj nazwisko");

        TextField pane_txt_3 = (TextField) pane_id_masked.lookup("#pane_txt_3");
        pane_txt_3.setOpacity(1.0);
        pane_txt_3.setPromptText("Podaj login użytkownika");

        TextField pane_txt_4 = (TextField) pane_id_masked.lookup("#pane_txt_4");
        pane_txt_4.setOpacity(1.0);
        pane_txt_4.setPromptText("Podaj hasło użytkownika");

        TextField pane_txt_5 = (TextField) pane_id_masked.lookup("#pane_txt_5");
        pane_txt_5.setOpacity(1.0);
        pane_txt_5.setPromptText("Podaj id użytkownika");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj użytkownika");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_updateData.changeUser(pane_txt_1.getText(),pane_txt_2.getText(), pane_txt_3.getText(), pane_txt_4.getText(), pane_txt_5.getText());
            if(ret>0){
                pane_result_msg.setText("Użytkownik zmodyfikowany");
            }
            else{
                pane_result_msg.setText("Użytkownika nie udało sie zmodyfikować");
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
    public void zmodyfikuj_uprawnienia_uzytkownika()
    {
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Modyfikacja uprawnienia użytkownika");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Zmodyfikuj uprawnienia (T-admin, N-użytkownik)");

        TextField pane_txt_2 = (TextField) pane_id_masked.lookup("#pane_txt_2");
        pane_txt_2.setOpacity(1.0);
        pane_txt_2.setPromptText("Podaj id użytkownika");



        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Modyfikuj uprawnienia");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret = db_updateData.modifyUprawnienia(pane_txt_1.getText(),pane_txt_2.getText());
            if(ret>0){
                pane_result_msg.setText("Uprawnienia zmodyfikowane");
            }
            else{
                pane_result_msg.setText("Uprawnień nie udało sie zmodyfikować");
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
    void usun_uzytkownika(){
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Usuwanie użytkownika");
    TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj login użytkownika");

    Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Usuń użytkownika");
        pane_button.setOnMouseClicked(event -> {
        Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");
            int ret;
        if(Integer.parseInt(pane_txt_1.getText()) == User.getInstance().getId()){
            ret=0;
        }
        else{
            ret = db_deleteData.deleteUser(pane_txt_1.getText());
        }
        if(ret>0){
            pane_result_msg.setText("Uprawnienia zmodyfikowane");
        }
        else{
            pane_result_msg.setText("Uprawnień nie udało sie zmodyfikować");
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
    void zablokuj_uzytkownika(){
        Label pane_tytul = (Label) pane_id_masked.lookup("#pane_tytul");
        pane_tytul.setText("Blokowanie/ Odblokowywanie użytkownika");
        TextField pane_txt_1 = (TextField) pane_id_masked.lookup("#pane_txt_1");
        pane_txt_1.setOpacity(1.0);
        pane_txt_1.setPromptText("Podaj login użytkownika");

        Button pane_button = (Button) pane_id_masked.lookup("#pane_button");
        pane_button.setText("Zablokuj/Oblokuj użytkownika");
        pane_button.setOnMouseClicked(event -> {
            Label pane_result_msg = (Label) pane_id_masked.lookup("#pane_result_msg");

            int ret = 0;
            for (String[] gbs: db_getData.users) {
                System.out.println(pane_txt_1.getText());
                System.out.println(gbs[2]);
                if(gbs[2].contentEquals(pane_txt_1.getText())){
                    if(gbs[4].contentEquals("T"))
                        ret = db_updateData.blockUser(pane_txt_1.getText(),"N");
                    else
                        ret = db_updateData.blockUser(pane_txt_1.getText(),"T");
                    break;
                }
            }

            if(ret>0){
                pane_result_msg.setText("Zmieniony stan blokady.");
            }
            else{
                pane_result_msg.setText("Błąd w zmienianiu stanu blokady.");
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
    public void rent_activate(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            if(!pane_search_user.toString().isEmpty() && !pane_search_user.getText().isBlank()
                    && pane_search_user.getText().matches("[0-9]+") ) {
                if(activate_diff) {
                    init_rent_list(Integer.parseInt(pane_search_user.getText()));
                }
                if(!activate_diff) {
                    init_res_list(Integer.parseInt(pane_search_user.getText()));
                }
            }
            pane_search_result.setOpacity(1.0);
        }
    }

    @FXML
    public void check_rent(MouseEvent event){
        pane_id_masked_log.setVisible(true);
        activate_diff = true;
    }

    @FXML
    public void check_res(MouseEvent event){
        pane_id_masked_log.setVisible(true);
        activate_diff = false;
    }

    public void init_rent_list(int id){
        pane_search_result.getChildren().clear();
        db_getData.getHireInformation(id);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();

            TableColumn<Wypozyczenia, ?> autorCol = new TableColumn<>("Autor");
            autorCol.setMinWidth(pane_search_result.getPrefWidth() * 0.2);
            autorCol.setCellValueFactory(
                    new PropertyValueFactory<>("nazwa_autora"));

            TableColumn<Wypozyczenia, ?> nazwaCol = new TableColumn<>("Nazwa");
            nazwaCol.setMinWidth(pane_search_result.getPrefWidth() * 0.2);
            nazwaCol.setCellValueFactory(
                    new PropertyValueFactory<>("nazwa"));

            TableColumn<Wypozyczenia, ?> egzemplarzeCol = new TableColumn<>("Numer egzemplarza");
            egzemplarzeCol.setMinWidth(pane_search_result.getPrefWidth() * 0.15);
            egzemplarzeCol.setCellValueFactory(
                    new PropertyValueFactory<>("id_egzemplarze"));

            TableColumn<Wypozyczenia, ?> data_wypozyczeniaCol = new TableColumn<>("Data wypozyczenia");
            data_wypozyczeniaCol.setMinWidth(pane_search_result.getPrefWidth() * 0.15);
            data_wypozyczeniaCol.setCellValueFactory(
                    new PropertyValueFactory<>("data_wypozyczenia"));

            TableColumn<Wypozyczenia, ?> data_zwrotuCol = new TableColumn<>("Termin zwrotu");
            data_zwrotuCol.setMinWidth(pane_search_result.getPrefWidth() * 0.1);
            data_zwrotuCol.setCellValueFactory(
                    new PropertyValueFactory<>("data_zwrotu"));
            TableColumn<Wypozyczenia, String> grzywnaCol = new TableColumn<>("Grzywna");
            grzywnaCol.setMinWidth(pane_search_result.getPrefWidth() * 0.1);
            grzywnaCol.setCellValueFactory(
                    new PropertyValueFactory<>("data_zwrotu"));

            grzywnaCol.setCellFactory(col -> {
                TableCell<Wypozyczenia, String> cell = new TableCell<>();
                cell.itemProperty().addListener((obs, old, newVal) -> {
                    String debt;
                    int rowIndex = cell.getIndex();
                    if (data_zwrotuCol.getCellObservableValue(rowIndex) != null) {
                        String data_zwrotu = data_zwrotuCol.getCellData(rowIndex).toString();
                        LocalDate dataZwrotu = LocalDate.parse(data_zwrotu, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate currentDate = LocalDate.now();

                        if (dataZwrotu.isAfter(currentDate)) {
                            cell.setText(" - ");
                        } else if (dataZwrotu.isBefore(currentDate) && !data_zwrotuCol.getCellData(rowIndex).toString().isEmpty()) {
                            DecimalFormat currency = new DecimalFormat("#0.00");
                            int diff = (int) ChronoUnit.DAYS.between(dataZwrotu, currentDate);
                            debt = String.valueOf(currency.format(diff * 0.2));
                            cell.setText(debt + " zł");
                        }
                    }

                });
                return cell;
            });

        for (String[] tab : db_getData.rental) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            String ilosc_przedluzen = tab[5];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora,ilosc_przedluzen));
        }

        lista_rent.getItems().clear();
        lista_rent.setItems(items);

        if(lista_rent.getColumns().size() == 0) {
            lista_rent.getColumns().addAll(nazwaCol, egzemplarzeCol, autorCol, data_wypozyczeniaCol, data_zwrotuCol, grzywnaCol);
        }

        lista_rent.setPlaceholder(new Label("Brak rekordów. Sprawdź poprawność identyfikatora użytkownika."));

        lista_rent.setFixedCellSize(30);

        lista_rent.prefWidthProperty().bind(pane_search_result.heightProperty());
        lista_rent.prefWidthProperty().bind(pane_search_result.widthProperty());

        lista_rent.prefWidthProperty().bind(pane_search_result.widthProperty());
        lista_rent.prefHeightProperty().bind(pane_search_result.heightProperty());

        pane_search_result.getChildren().clear();
        pane_search_result.getChildren().addAll(lista_rent);

        AnchorPane.setTopAnchor(lista_rent, 0.0);
        AnchorPane.setLeftAnchor(lista_rent, 0.0);
        AnchorPane.setBottomAnchor(lista_rent, 0.0);
        AnchorPane.setRightAnchor(lista_rent, 0.0);

        lista_rent.getStylesheets().add("/fxml.home/home.css");
        pane_search_result.setVisible(true);

    }

    public void init_res_list(int id) {
        pane_search_result.getChildren().clear();
        System.out.println(pane_search_result.getChildren().isEmpty());
        db_getData.getReservationInformation(id);
        ObservableList<Rezerwacje> items_res = FXCollections.observableArrayList();


        TableColumn<Rezerwacje, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(lista_res.getPrefWidth()*0.10);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Rezerwacje, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(lista_res.getPrefWidth()*0.15);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));


        TableColumn<Rezerwacje, ?> egzemplarzeCol = new TableColumn<>("Numer egzemplarze");
        egzemplarzeCol.setMinWidth(lista_res.getPrefWidth()*0.05);
        egzemplarzeCol.setCellValueFactory(
                new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn<Rezerwacje, ?> data_koncaCol = new TableColumn<>("Data konca");
        data_koncaCol.setMinWidth(lista_res.getPrefWidth()*0.10);
        data_koncaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_konca"));

        TableColumn<Rezerwacje, ?> data_rezerwacjiCol = new TableColumn<>("Data rezerwacji");
        data_rezerwacjiCol.setMinWidth(lista_res.getPrefWidth()*0.10);
        data_rezerwacjiCol.setCellValueFactory(
                new PropertyValueFactory<>("data_rezerwacji"));

        TableColumn<Rezerwacje, String> przedluz_rezerwacjeCol = new TableColumn<>("Przedluż rezerwacje");
        przedluz_rezerwacjeCol.setMinWidth(lista_res.getPrefWidth()*0.2);
        przedluz_rezerwacjeCol.setCellValueFactory(
                new PropertyValueFactory<>("przedluz_rezerwacje"));

        TableColumn<Rezerwacje, String> anuluj_rezerwacjeCol = new TableColumn<>("Anuluj rezerwacje");
        anuluj_rezerwacjeCol.setMinWidth(lista_res.getPrefWidth()*0.15);
        anuluj_rezerwacjeCol.setCellValueFactory(
                new PropertyValueFactory<>("anuluj_rezerwacje"));

        przedluz_rezerwacjeCol.setCellFactory(col -> {
            TableCell<Rezerwacje, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (przedluz_rezerwacjeCol.getCellObservableValue(rowIndex) != null) {
                    Node centreBox = createPriorityGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                    cell.setOnMouseClicked(event -> {
                        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                            if(Integer.parseInt(przedluz_rezerwacjeCol.getCellObservableValue(rowIndex).getValue())>2) {
                                Label notificationLabel = new Label("Przekroczono limit przedluzen rezerwacji.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER);
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("""
                                    -fx-border-radius: 10;
                                        -fx-border-color: #004aad;
                                        -fx-background-radius: 10;
                                        -fx-background-color: NULL;
                                        -fx-border-width: 1;
                                        -fx-text-fill: #004aad;""");
                                Timeline timeline = new Timeline(new KeyFrame(
                                        Duration.seconds(3),
                                        event2 -> notificationLabel.setVisible(false)
                                ));
                                timeline.play();
                                anchor.getChildren().add(notificationLabel);
                            }
                            else{
                                TablePosition<Rezerwacje, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                                int row = tablePosition.getRow();
                                int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                                db_updateData.updateReservation(data);
                                Label notificationLabel = new Label("Przedłużono pomyślnie.");
                                Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                notificationLabel.setFont(pop_r_h1);
                                notificationLabel.setAlignment(Pos.CENTER);
                                notificationLabel.setPrefSize(300, 50);
                                notificationLabel.setLayoutX(700);
                                notificationLabel.setLayoutY(320);
                                notificationLabel.setStyle("""
                                    -fx-border-radius: 10;
                                        -fx-border-color: #004aad;
                                        -fx-background-radius: 10;
                                        -fx-background-color: NULL;
                                        -fx-border-width: 1;
                                        -fx-text-fill: #004aad;""");
                                Timeline timeline = new Timeline(new KeyFrame(
                                        Duration.seconds(3),
                                        event2 -> {
                                            notificationLabel.setVisible(false);
                                            yourReservation_clicked(event);
                                        }
                                ));
                                timeline.play();
                                anchor.getChildren().add(notificationLabel);
                            }
                        }
                    });
                }
            });
            return cell;

        });


        anuluj_rezerwacjeCol.setCellFactory(col -> {
            TableCell<Rezerwacje, String> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                int rowIndex = cell.getIndex();
                if (anuluj_rezerwacjeCol.getCellObservableValue(rowIndex) != null) {
                    Node centreBox = createDeleteGraphic();
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));

                    cell.setOnMouseClicked(event -> {
                        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                            if(anuluj_rezerwacjeCol.getCellData(rowIndex).contentEquals("1")) {
                                TablePosition<Rezerwacje, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                                int row = tablePosition.getRow();
                                int data = Integer.parseInt((String) egzemplarzeCol.getCellObservableValue(row).getValue());
                                if(db_deleteData.deleteReservation(data,User.getInstance().getId()) > 0)
                                {
                                    Label notificationLabel = new Label("Anulowano rezerwacje.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER);
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("""
                                            -fx-border-radius: 10;
                                                -fx-border-color: #004aad;
                                                -fx-background-radius: 10;
                                                -fx-background-color: NULL;
                                                -fx-border-width: 1;
                                                -fx-text-fill: #004aad;""");
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> {notificationLabel.setVisible(false); labelrezerwacje.fireEvent(event);}
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);
                                }
                                else
                                {
                                    Label notificationLabel = new Label("Nie udalo sie anulowac rezerwacji.");
                                    Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"),18);
                                    notificationLabel.setFont(pop_r_h1);
                                    notificationLabel.setAlignment(Pos.CENTER);
                                    notificationLabel.setPrefSize(300, 50);
                                    notificationLabel.setLayoutX(150);
                                    notificationLabel.setLayoutY(70);
                                    notificationLabel.setStyle("""
                                            -fx-border-radius: 10;
                                                -fx-border-color: #004aad;
                                                -fx-background-radius: 10;
                                                -fx-background-color: NULL;
                                                -fx-border-width: 1;
                                                -fx-text-fill: #004aad;""");
                                    Timeline timeline = new Timeline(new KeyFrame(
                                            Duration.seconds(3),
                                            event2 -> {notificationLabel.setVisible(false); labelrezerwacje.fireEvent(event);}
                                    ));
                                    timeline.play();
                                    anchor.getChildren().add(notificationLabel);
                                }
                            }
                        }
                    });
                }
            });
            return cell;

        });


        for (String[] tab : db_getData.rental) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_konca = tab[3];
            String data_rezerwacji = tab[4];
            String przedluz_rezerwacje = tab[5];
            String anuluj_rezerwacje = tab[6];

            items_res.add(new Rezerwacje(id_egzemplarze,nazwa, nazwa_autora, data_konca, data_rezerwacji,  przedluz_rezerwacje, anuluj_rezerwacje));
        }

        lista_res.getItems().clear();
        lista_res.setItems(items_res);

        if(lista_res.getColumns().size() == 0) {
            lista_res.getColumns().addAll(egzemplarzeCol, nazwaCol,  autorCol, data_koncaCol,data_rezerwacjiCol,przedluz_rezerwacjeCol, anuluj_rezerwacjeCol);
        }

        lista_res.setPlaceholder(new Label("Jesteśmy zaskoczeni, że niczego nie znaleźliśmy! Czyżbyśmy mieli dzień wolny?"));

        lista_res.prefWidthProperty().bind(pane_search_result.widthProperty());
        lista_res.prefHeightProperty().bind(pane_search_result.heightProperty());

        pane_search_result.getChildren().clear();
        pane_search_result.getChildren().addAll(lista_res);

        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");
        pane_search_result.setVisible(true);
    }

    @FXML
    public void hide_panes(MouseEvent event){
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
        pane_search_result.setVisible(false);
        pane_id_masked_log.setVisible(false);
        Katalog_lista_adminUser();
    }


}
