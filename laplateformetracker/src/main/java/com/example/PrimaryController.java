package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PrimaryController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private VBox adminPanel;

    @FXML
    private VBox studentPanel;

    @FXML
    private void initialize() {
        User user = App.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Bienvenue " + user.getUsername() + " (" + user.getRole() + ")");
            boolean isAdmin = "admin".equalsIgnoreCase(user.getRole());
            adminPanel.setVisible(isAdmin);
            adminPanel.setManaged(isAdmin);
            studentPanel.setVisible(!isAdmin);
            studentPanel.setManaged(!isAdmin);
        } else {
            welcomeLabel.setText("Bienvenue");
            adminPanel.setVisible(false);
            adminPanel.setManaged(false);
            studentPanel.setVisible(false);
            studentPanel.setManaged(false);
        }
    }

    @FXML
    private void openStudentList() throws IOException {
        App.setRoot("student_list");
    }

    @FXML
    private void openStudentForm() throws IOException {
        StudentFormController.setEditingStudent(null);
        App.setRoot("student_form");
    }

    @FXML
    private void logout() throws IOException {
        App.setCurrentUser(null);
        App.setRoot("login");
    }
}
