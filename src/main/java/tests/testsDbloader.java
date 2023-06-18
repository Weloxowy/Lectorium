package tests;
import javafx.scene.image.Image;
import org.example.User;
import org.example.db.DbAuth;
import org.example.db.DbParent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

public class testsDbloader{

    @Test
    public void testGetImage() {
        // Przygotowanie

        DbAuth auth = new DbAuth();

        // Wykonanie
        int id = 1; // ID testowego użytkownika
        auth.getImage(id);

        // Sprawdzenie
        Image image = User.getInstance().getImage();
        assertNotNull(image, "Zdjęcie nie zostało załadowane");
    }

    @ParameterizedTest
    @ValueSource(strings = {"awiech,awiech", "mmaj,mmaj123"}) // Lista loginów i haseł do testowania
    public void testTryLogin(String loginAndPassword) {
        // Przygotowanie
        String[] credentials = loginAndPassword.split(",");
        String login = credentials[0];
        String password = credentials[1];
        DbAuth auth = new DbAuth();

        // Wykonanie
        boolean result = auth.tryLogin(login, password);

        // Sprawdzenie
        assertTrue(result, "Nie udało się zalogować dla loginu: " + login);
    }


}
