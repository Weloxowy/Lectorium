package org.example.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.Main;
import org.example.User;
import org.example.app.admin.adminController;
import org.example.app.admin.catalogManagerController;
import org.example.app.admin.userManagerController;
import org.example.app.home.*;
import org.example.verify.logincontroller;

import java.io.IOException;
import java.util.Objects;

import static org.example.Main.dbload;

public class appParent {

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
    void katalog_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog.fxml"));
            parent = loader.load();
            katalogcontroller kat = loader.getController();
            kat.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
            controller.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
            contact.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
            controller.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
        if(User.getInstance().getCzy_admin().contentEquals("T")){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/adminPanel.fxml"));
                parent = loader.load();
                adminController controller = loader.getController();
                controller.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                controller.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/yourLibrary.fxml"));
                parent = loader.load();
                yourLibraryController library = loader.getController();
                library.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
                Scene scene = new Scene(parent, stage.getWidth() - 15, stage.getHeight() - 38);
                stage.setScene(scene);
                library.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            Profile.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
            nowosci.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
        Font pop_r_h2 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-Regular.ttf"), 14);
        Font pop_b_h1 = Font.loadFont(getClass().getResourceAsStream("/res/font/Poppins-SemiBold.ttf"), 20);
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

    protected void katalog_item(MouseEvent event, int row) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/katalog_item.fxml"));
            parent = loader.load();
            dbload.get_cover(row);
            katalog_itemcontroller kat = loader.getController();
            kat.init(User.getInstance().getImie(),User.getInstance().getNazwisko());
            kat.load(row-1);
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            kat.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void yourHire_clicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        Parent parent;
        if(User.getInstance().getCzy_admin().contentEquals("T")){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.admin/catalogManager.fxml"));
                parent = loader.load();
                catalogManagerController Profile = loader.getController();
                Profile.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
                Profile.init(User.getInstance().getImie(), User.getInstance().getNazwisko(), event);
                Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
                stage.setScene(scene);
                Profile.font(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
                Profile.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
                Profile.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
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
            Label labelbiblioteka = (Label) scene.lookup("#labelbiblioteka");
            labelbiblioteka.setText("Panel Administratora");
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

    @FXML       //NIE USUWAĆ, NA POTEM
    void Controller(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        size_guard(stage);
        System.out.println(((Node) event.getSource()).getId().toString());
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml.home/nowosci.fxml"));
            parent = loader.load();
            nowosciController nowosci = loader.getController();
            nowosci.init(User.getInstance().getImie(), User.getInstance().getNazwisko());
            Scene scene = new Scene(parent,stage.getWidth()-15,stage.getHeight()-38);
            stage.setScene(scene);
            nowosci.font(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


