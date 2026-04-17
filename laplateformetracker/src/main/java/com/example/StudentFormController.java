package com.example;

import java.io.IOException;
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
    private TextField gradeField;

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
            gradeField.setText(String.valueOf(editingStudent.getGrade()));
        } else {
            formTitle.setText("Ajouter un nouvel étudiant");
        }
    }

    @FXML
    private void saveStudent() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageText = ageField.getText();
        String gradeText = gradeField.getText();

        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank() || ageText == null || ageText.isBlank() || gradeText == null || gradeText.isBlank()) {
            errorLabel.setText("Tous les champs doivent être remplis.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText.trim());
            double grade = Double.parseDouble(gradeText.trim());

            if (age <= 0) {
                errorLabel.setText("L'âge doit être un entier positif.");
                return;
            }
            if (grade < 0 || grade > 20) {
                errorLabel.setText("La note doit être entre 0 et 20.");
                return;
            }

            if (editingStudent != null) {
                editingStudent.setFirstName(firstName.trim());
                editingStudent.setLastName(lastName.trim());
                editingStudent.setAge(age);
                editingStudent.setGrade(grade);
                boolean updated = studentDAO.update(editingStudent);
                if (updated) {
                    App.setRoot("student_list");
                } else {
                    errorLabel.setText("Impossible de mettre à jour l'étudiant.");
                }
            } else {
                Student student = new Student(firstName.trim(), lastName.trim(), age, grade);
                boolean added = studentDAO.add(student);
                if (added) {
                    App.setRoot("student_list");
                } else {
                    errorLabel.setText("Impossible d'ajouter l'étudiant.");
                }
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("L'âge et la note doivent être des nombres valides.");
        }
    }

    @FXML
    private void cancel() throws IOException {
        App.setRoot("student_list");
    }
}
