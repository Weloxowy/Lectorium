
package org.example.home;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.example.Katalog;
import org.example.Main;
import org.example.Wypozyczenia;

import static org.example.Main.dbload;

public class yourHire extends home{
        @FXML
        private ImageView avatar;

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
        private Label Name;

         @FXML
         private Label Surname;

         @FXML
        private Button Wszystkie;


        @FXML
        private Button Aktualne;

        @FXML
        private TextField searchbar;
        @FXML
         private TableView<Wypozyczenia> lista = new TableView<Wypozyczenia>();
         @FXML
         private AnchorPane anchortable = new AnchorPane();

         @FXML
         private AnchorPane anchor_hire;

    public void init(String imie, String nazwisko, MouseEvent event, Image image) {
        nametag.setText(imie + " " + nazwisko);
        avatar.setImage(Main.user.getImage());
        avatar_view();
        font();
        Lista_Hire(Main.user.getId());
        Name.setText(imie);
        Surname.setText(nazwisko);


    }
    public void Lista_Hire(int id) {
        dbload.yourHireInformation(id);
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();


        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn nazwaCol = new TableColumn("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.3);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));


        TableColumn egzemplarzeCol = new TableColumn("id_egzemplarze");
        egzemplarzeCol.setMinWidth(anchortable.getPrefWidth()*0.05);
        egzemplarzeCol.setCellValueFactory(
                new PropertyValueFactory<>("id_egzemplarze"));

        TableColumn data_wypozyczeniaCol = new TableColumn("data_wypozyczenia");
        data_wypozyczeniaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        data_wypozyczeniaCol.setCellValueFactory(
                new PropertyValueFactory<>("data_wypozyczenia"));

        TableColumn data_zwrotuCol = new TableColumn("data_zwrotu");
        data_zwrotuCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        data_zwrotuCol.setCellValueFactory(
                new PropertyValueFactory<>("data_zwrotu"));

        for (String[] tab : dbload.ListHire) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora));
        }
        //Dodaj wartości do kolumn
        lista.setItems(items);
        //Dodaj kolumny do tabeli
        lista.getColumns().addAll(egzemplarzeCol, nazwaCol,  data_wypozyczeniaCol,data_zwrotuCol,autorCol);
        // Ustaw preferowaną wielkość TableView na zgodną z AnchorPane
        lista.setPrefWidth(anchor_hire.getPrefWidth());
        lista.setPrefHeight(anchor_hire.getPrefHeight());
        // Powiąż preferowane wielkości TableView i AnchorPane
        lista.prefWidthProperty().bind(anchor_hire.widthProperty());
        lista.prefHeightProperty().bind(anchor_hire.heightProperty());
        // Dodaj TableView do AnchorPane
        anchor_hire.getChildren().addAll(lista);
        // Ustaw parametry kotwiczenia TableView na wartość 0
        AnchorPane.setTopAnchor(lista, 0.0);
        AnchorPane.setLeftAnchor(lista, 0.0);
        AnchorPane.setBottomAnchor(lista, 0.0);
        AnchorPane.setRightAnchor(lista, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");
        //tworzenie listy posortowanych elementow dla tych ktore sa poprawne
/*
        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                int row = tablePosition.getRow();
                Integer data = (Integer) idCol.getCellObservableValue(row).getValue();
                System.out.println(data);
            }
        });

 */
    }
    void avatar_view() {
        int radius = 28;
        double centerX = avatar.getBoundsInLocal().getWidth() / 2.0;
        double centerY = avatar.getBoundsInLocal().getHeight() / 2.0;
        Circle clipCircle = new Circle(centerX, centerY, radius);
        avatar.setClip(clipCircle);
    }

    @FXML
    void font(){
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
    }

    @FXML
    void check_hire_information(MouseEvent event)
    {
        ObservableList<Wypozyczenia> items = FXCollections.observableArrayList();
        dbload.check_hire_information(Main.user.getId());
        for (String[] tab : dbload.ListHire) {
            String id_egzemplarze = tab[0];
            String nazwa = tab[1];
            String nazwa_autora = tab[2];
            String data_wypozyczenia = tab[3];
            String data_zwrotu = tab[4];
            items.add(new Wypozyczenia(id_egzemplarze,nazwa, data_wypozyczenia, data_zwrotu,nazwa_autora));
        }

        lista.setItems(items);
    }


}
