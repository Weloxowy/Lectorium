package org.example.app;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

/**
 * Klasa reprezentująca skórkę dla pola tekstowego z hasłem.
 * Ta klasa rozszerza klasę `TextFieldSkin` i dostarcza funkcjonalność
 * maskowania tekstu wprowadzanego w polu tekstowym z hasłem.
 */
public class PasswordSkin extends TextFieldSkin {

    /**
     * Konstruktor klasy `PasswordSkin`.
     *
     * @param textField Pole tekstowe, dla którego tworzona jest skórka.
     */
    public PasswordSkin(TextField textField) {
        super(textField);
    }

    /**
     * Metoda maskująca tekst wprowadzany w polu tekstowym z hasłem.
     * Jeśli skórka jest przypisana do pola typu `PasswordField`,
     * zamienia każdy znak tekstu na znak maskujący '•'.
     *
     * @param txt Tekst do zmaskowania.
     * @return Zmaskowany tekst.
     */
    protected String maskText(String txt) {
        if (getSkinnable() instanceof PasswordField) {
            int n = txt.length();

            return "•".repeat(n);
        } else {
            return txt;
        }
    }
}