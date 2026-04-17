package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        User user = App.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Bienvenue " + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            welcomeLabel.setText("Bienvenue");
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
