package org.example.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.User;
import org.example.app.admin.catalogManagerController;
import org.example.app.admin.userManagerController;
import org.example.app.home.*;
import org.example.auth.loginController;

import java.io.IOException;
import java.util.Objects;

/**
 * Klasa appParent reprezentuje kontroler głównego okna aplikacji.
 * Zawiera metody obsługujące zdarzenia interakcji użytkownika oraz
 * inicjalizujące interfejs użytkownika.
 */
public class appParent {

    /**
     * Metoda obsługuje zdarzenie wylogowania użytkownika.
     *
     * @param event zdarzenie myszy
     */
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
            loginController controller = loader.getController();
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

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk katalogu.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    protected void katalog_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            catalogController kat = loader.getController();
            kat.init(User.getImie(), User.getNazwisko());
            kat.Katalog_lista();
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk katalogu z zapytaniem wziętym z pola wyszukiwania.
     *
     * @param event zdarzenie myszy
     * @param query zapytanie z wyszukiwania
     */
    @FXML
    protected void katalog_clicked(MouseEvent event, String query) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            catalogController kat = loader.getController();
            kat.init(User.getImie(), User.getNazwisko());
            kat.Katalog_lista(query);
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk kategorii.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    void kategorie_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/kategorie.fxml"));
            parent = loader.load();
            categoriesController controller = loader.getController();
            controller.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            controller.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk kontaktu.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    void contact_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/contact.fxml"));
            parent = loader.load();
            contactController contact = loader.getController();
            contact.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            contact.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk strony głównej.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    void glowna_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/home.fxml"));
            parent = loader.load();
            homeController controller = loader.getController();
            controller.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            controller.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk biblioteki.
     *
     * @param event zdarzenie myszy
     */
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

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk profilu użytkownika.
     *
     * @param event zdarzenie myszy
     */
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
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            Profile.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na przycisk nowości.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    void nowosciController_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/nowosci.fxml"));
            parent = loader.load();
            latestBooksController nowosci = loader.getController();
            nowosci.init(User.getImie(), User.getNazwisko());
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            nowosci.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda wykorzystuje aktualny interfejs użytkownika i ustawia czcionki.
     *
     * @param scene obiekt reprezentujący aktualnie przetwarzaną scenę aplikacji
     */
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

    /**
     * Metoda obsługuje zdarzenie kliknięcia na wiersz danej książki w katalogu, bądź inne pole.
     *
     * @param event    zdarzenie myszy
     * @param id_egz   numer id ksiazki w katalogu
     * @param if_admin boolean sprawdzający czy wchodzi admin czy zwykły użytkownik
     */
    protected void katalog_item(MouseEvent event, int id_egz, boolean if_admin) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog_item.fxml"));
            parent = loader.load();
            Main.db_getData.getCover(id_egz);
            catalogItemController kat = loader.getController();
            kat.init(User.getImie(), User.getNazwisko());
            kat.if_adm = if_admin;
            kat.load(id_egz);
            Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na pole twoje wypożyczenia.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    protected void yourHire_clicked(MouseEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        if (User.getInstance().getCzy_admin().contentEquals("T")) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/catalogManager.fxml"));
                parent = loader.load();
                catalogManagerController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourHire.fxml"));
                parent = loader.load();
                yourRentsController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko(), event);
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Metoda obsługuje zdarzenie kliknięcia na pole twoje rezerwacje.
     *
     * @param event zdarzenie myszy
     */
    @FXML
    protected void yourReservation_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        if (User.getInstance().getCzy_admin().contentEquals("T")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/userManager.fxml"));
                parent = loader.load();
                userManagerController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourReservation.fxml"));
                parent = loader.load();
                yourReservationsController Profile = loader.getController();
                Profile.init(User.getImie(), User.getNazwisko());
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Metoda dostosowuje wygląd programu i treść bocznego menu,
     * w zależności od tego czy zalogowany jest student, czy administrator.
     *
     * @param scene obiekt reprezentujący aktualnie przetwarzaną scenę aplikacji
     */
    void overrideLabels(Scene scene) {
        if (User.getInstance().getCzy_admin().contentEquals("T")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxml.admin/admin.css")).toExternalForm());
            scene.getRoot().applyCss();
            Label labelrezerwacje = (Label) scene.lookup("#labelrezerwacje");
            labelrezerwacje.setText("Zarządzanie użytkownikami");
            Label labelwypozyczenia = (Label) scene.lookup("#labelwypozyczenia");
            labelwypozyczenia.setText("Zarządzanie katalogiem");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fxml.home/home.css")).toExternalForm());
            scene.getRoot().applyCss();
        }
    }

    /**
     * Ustala minimalne wymiary okna.
     *
     * @param stage referencja do obiektu Stage
     */
    protected void size_guard(Stage stage) {
        stage.setMinHeight(768);
        stage.setMinWidth(1250);
    }

    /**
     * Tworzy grafikę plusa dla pola w tabeli.
     *
     * @return obiekt Node reprezentujący grafikę plusa
     */
    protected Node createPriorityGraphic() {
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/add.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    /**
     * Tworzy grafikę ticka dla pola w tabeli.
     *
     * @return obiekt Node reprezentujący grafikę tick (fajki)
     */
    protected Node createConfirmGraphic() {
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/check.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }

    /**
     * Tworzy grafikę minusa dla pola w tabeli.
     *
     * @return obiekt Node reprezentujący grafikę minusa
     */
    protected Node createDeleteGraphic() {
        HBox graphicContainer = new HBox();
        graphicContainer.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/icons/dark/remove.png"))));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        graphicContainer.getChildren().add(imageView);
        return graphicContainer;
    }
}


