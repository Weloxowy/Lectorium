package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Katalog;
import org.example.Main;
import org.example.User;
import org.example.app.admin.catalogManagerController;
import org.example.app.admin.userManagerController;
import org.example.app.home.*;
import org.example.auth.logincontroller;

import java.io.IOException;
import java.util.Objects;

import static org.example.Main.db_getData;
import static org.example.Main.db_parent;

public class appParent {
    boolean zapamietaj;
    @FXML
    void logout_perform(MouseEvent event) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("res/logo/Lectorium_logo.png");
        stage.getIcons().add(icon);
        final Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldstage.close();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.verify/login.fxml"));
            parent = loader.load();
            logincontroller controller = loader.getController();
            controller.getHaslo().setSkin(new PasswordSkin(controller.getHaslo()));
            controller.font();
            stage.setResizable(true);
            stage.isMaximized();
            stage.setFullScreen(false);
            stage.setTitle("Lectorium alpha");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (parent == null)
            return;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void katalog_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(User.getImie(), User.getNazwisko());
            kat.Katalog_lista();
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void katalog_clicked(MouseEvent event, String query) {
        zapamietaj = true;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(User.getImie(), User.getNazwisko());
            kat.Katalog_lista(query);
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void kategorie_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/kategorie.fxml"));
            parent = loader.load();
            kategoriecontroller controller = loader.getController();
            controller.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            controller.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void contact_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/contact.fxml"));
            parent = loader.load();
            contactcontroller contact = loader.getController();
            contact.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            contact.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void glowna_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/home.fxml"));
            parent = loader.load();
            homecontroller controller = loader.getController();
            controller.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            controller.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void library_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourLibrary.fxml"));
                parent = loader.load();
                yourLibraryController library = loader.getController();
                library.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                library.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void yourProfileController_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourProfileController.fxml"));
            parent = loader.load();
            yourProfileController Profile = loader.getController();
            Profile.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            Profile.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void nowosciController_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/nowosci.fxml"));
            parent = loader.load();
            nowosciController nowosci = loader.getController();
            nowosci.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            nowosci.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void font(Scene scene) {
        overrideLabels(scene);
        Font ssp_sb_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/SourceSerifPro-SemiBold.ttf"), 25);
        Font pop_r_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 18);
        Font pop_b_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 14);
        Label nametag = (Label) scene.lookup("#nametag");
        nametag.setFont(ssp_sb_h1);
        Label labelbiblioteka = (Label) scene.lookup("#labelbiblioteka");
        labelbiblioteka.setFont(pop_b_h2);
        Label labelglowna = (Label) scene.lookup("#labelglowna");
        labelglowna.setFont(pop_b_h2);
        Label labelkatalog = (Label) scene.lookup("#labelkatalog");
        labelkatalog.setFont(pop_b_h2);
        Label labelkontakt = (Label) scene.lookup("#labelkontakt");
        labelkontakt.setFont(pop_b_h2);
        Label labelkategorie = (Label) scene.lookup("#labelkategorie");
        labelkategorie.setFont(pop_b_h2);
        Label labelnowosci = (Label) scene.lookup("#labelnowosci");
        labelnowosci.setFont(pop_b_h2);
        Label labelrezerwacje = (Label) scene.lookup("#labelrezerwacje");
        labelrezerwacje.setFont(pop_b_h2);
        Label labelwypozyczenia = (Label) scene.lookup("#labelwypozyczenia");
        labelwypozyczenia.setFont(pop_b_h2);
        TextField searchbar = (TextField) scene.lookup("#searchbar");
        searchbar.setFont(pop_r_h1);
    }

    protected void katalog_item(MouseEvent event, int id_egz,boolean if_admin) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            size_guard(stage);
            Parent parent;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog_item.fxml"));
                parent = loader.load();
                Main.db_getData.getCover(id_egz);
                katalog_itemcontroller kat = loader.getController();
                kat.init(User.getImie(), User.getNazwisko());
                kat.if_adm = if_admin;
                kat.load(id_egz);
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                kat.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }





    @FXML
    protected void yourHire_clicked(MouseEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        if(User.getInstance().getCzy_admin().contentEquals("T")){

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/catalogManager.fxml"));
                parent = loader.load();
                catalogManagerController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourHire.fxml"));
                parent = loader.load();
                yourHire Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko(), event);
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    final TableView<Katalog> lista = new TableView<>();
    public void Katalog_lista(AnchorPane anchortable, TextField searchbar) {
        db_getData.getBooks();
        ObservableList<Katalog> items = FXCollections.observableArrayList();

        TableColumn<Katalog, ?> idCol = new TableColumn<>("Id");
        idCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id_katalog"));
        idCol.setVisible(false);

        TableColumn<Katalog, ?> autorCol = new TableColumn<>("Autor");
        autorCol.setMinWidth(anchortable.getPrefWidth()*0.15);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa_autora"));

        TableColumn<Katalog, ?> nazwaCol = new TableColumn<>("Nazwa");
        nazwaCol.setMinWidth(anchortable.getPrefWidth()*0.25);
        nazwaCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwa"));

        TableColumn<Katalog, ?> rokCol = new TableColumn<>("Rok wydania");
        rokCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        rokCol.setCellValueFactory(
                new PropertyValueFactory<>("rok_wydania"));

        TableColumn<Katalog, ?> wydanieCol = new TableColumn<>("Wydanie");
        wydanieCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        wydanieCol.setCellValueFactory(
                new PropertyValueFactory<>("wydanie"));

        TableColumn<Katalog, ?> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));

        TableColumn<Katalog, ?> jezykCol = new TableColumn<>("Język");
        jezykCol.setMinWidth(anchortable.getPrefWidth()*0.1);
        jezykCol.setCellValueFactory(
                new PropertyValueFactory<>("jezyk"));

        TableColumn<Katalog, ?> uwagiCol = new TableColumn<>("Uwagi");
        uwagiCol.setMinWidth(anchortable.getWidth()*0.4);
        uwagiCol.setCellValueFactory(
                new PropertyValueFactory<>("uwagi"));
        for (String[] tab: db_getData.books) {
            items.add(new Katalog(Integer.parseInt(tab[0]), tab[1], tab[2], tab[3], tab[4], tab[5], tab[6], tab[7], tab[8], tab[9]));
        }

        lista.setItems(items);

        lista.setFixedCellSize(30);

        lista.getColumns().addAll(idCol,nazwaCol,autorCol,rokCol,wydanieCol,isbnCol,jezykCol,uwagiCol);

        lista.setPrefWidth(anchortable.getPrefWidth());
        lista.setPrefHeight(anchortable.getPrefHeight());

        lista.prefWidthProperty().bind(anchortable.widthProperty());
        lista.prefHeightProperty().bind(anchortable.heightProperty());

        anchortable.getChildren().addAll(lista);

        AnchorPane.setLeftAnchor(anchortable, 0.0);
        AnchorPane.setBottomAnchor(anchortable, 0.0);
        AnchorPane.setRightAnchor(anchortable, 0.0);

        lista.getStylesheets().add("/fxml.home/home.css");


        FilteredList<Katalog> filteredList = new FilteredList<>(items, b -> true);

        searchbar.textProperty().addListener((observable,newValue, oldValue) -> filteredList.setPredicate(Katalog -> {
            if(newValue.isEmpty() || newValue.isBlank()){ return true;}

            String searchword = newValue.toLowerCase();

            if(Katalog.getNazwa().toLowerCase().contains(searchword)){
                return true;
            }
            if(Katalog.getNazwa_autora().toLowerCase().contains(searchword)){
                return true;
            }
            return Katalog.getIsbn().toLowerCase().contains(searchword);

        }));

        SortedList<Katalog> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(lista.comparatorProperty());

        lista.setItems(sortedList);

        lista.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                TablePosition<Katalog, ?> tablePosition = lista.getSelectionModel().getSelectedCells().get(0);
                Integer data = (Integer) idCol.getCellObservableValue(tablePosition.getRow()).getValue();
                zapamietaj = false;
                katalog_item(event,data,true);
            }
        });
    }

    @FXML
    protected void yourReservation_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        if(User.getInstance().getCzy_admin().contentEquals("T")){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/userManager.fxml"));
                parent = loader.load();
                userManagerController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourReservation.fxml"));
                parent = loader.load();
                yourReservation Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    void overrideLabels(Scene scene){
        if(User.getInstance().getCzy_admin().contentEquals("T")){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxml.admin/admin.css")).toExternalForm());
            scene.getRoot().applyCss();
            Label labelrezerwacje = (Label) scene.lookup("#labelrezerwacje");
            labelrezerwacje.setText("Zarządzanie użytkownikami");
            Label labelwypozyczenia = (Label) scene.lookup("#labelwypozyczenia");
            labelwypozyczenia.setText("Zarządzanie katalogiem");
        }
        else{
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxml.home/home.css")).toExternalForm());
            scene.getRoot().applyCss();
        }
    }

    protected void size_guard(Stage stage){
        stage.setMinHeight(768);
        stage.setMinWidth(1250);
    }

    protected Node createPriorityGraphic(){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/add.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }
    protected Node createConfirmGraphic() {
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/check.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    protected Node createDeleteGraphic(){
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/remove.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }
}


