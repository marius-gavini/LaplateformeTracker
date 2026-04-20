package com.example;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentFormController {

    @FXML
    private Label formTitle;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField promotionField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button saveButton;

    private final StudentDAO studentDAO = new StudentDAO();
    private static Student editingStudent;

    public static void setEditingStudent(Student student) {
        editingStudent = student;
    }

    @FXML
    private void initialize() {
        if (editingStudent != null) {
            formTitle.setText("Modifier un étudiant");
            firstNameField.setText(editingStudent.getFirstName());
            lastNameField.setText(editingStudent.getLastName());
            ageField.setText(String.valueOf(editingStudent.getAge()));
            promotionField.setText(String.valueOf(editingStudent.getPromotion()));
        } else {
            formTitle.setText("Ajouter un nouvel étudiant");
        }
    }

    @FXML
    private void saveStudent() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageText = ageField.getText();
        String promotion = promotionField.getText();

        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank() || ageText == null || ageText.isBlank() || promotion == null || promotion.isBlank()) {
            errorLabel.setText("Tous les champs doivent être remplis.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText.trim());

            if (age <= 0) {
                errorLabel.setText("L'âge doit être un entier positif.");
                return;
            }

            if (editingStudent != null) {
                editingStudent.setFirstName(firstName.trim());
                editingStudent.setLastName(lastName.trim());
                editingStudent.setAge(age);
                editingStudent.setPromotion(Integer.parseInt(promotion.trim()));
                boolean updated = studentDAO.update(editingStudent);
                if (updated) {
                    App.setRoot("student_list");
                } else {
                    errorLabel.setText("Impossible de mettre à jour l'étudiant.");
                }
            } else {
                Student student = new Student(firstName.trim(), lastName.trim(), age, Integer.parseInt(promotion.trim()));
                boolean added = studentDAO.add(student);
                if (added) {
                    App.setRoot("student_list");
                } else {
                    errorLabel.setText("Impossible d'ajouter l'étudiant.");
                }
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("L'âge et la promotion doivent être des nombres valides.");
        }
    }

    @FXML
    private void cancel() throws IOException {
        App.setRoot("student_list");
    }
}
