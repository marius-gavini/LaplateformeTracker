package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            errorLabel.setText("Veuillez saisir le nom d'utilisateur et le mot de passe.");
            return;
        }

        User user = AuthService.authenticate(username, password);
        if (user == null) {
            errorLabel.setText("Identifiants invalides.");
            return;
        }

        App.setCurrentUser(user);
        App.setRoot("primary");
    }
}
