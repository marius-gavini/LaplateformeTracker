package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StudentListController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, Integer> ageColumn;

    @FXML
    private TableColumn<Student, Double> gradeColumn;

    @FXML
    private TextField searchIdField;

    @FXML
    private TextField searchAgeField;

    @FXML
    private TextField searchMinGradeField;

    @FXML
    private TextField searchMaxGradeField;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private Label messageLabel;

    @FXML
    private Button refreshButton;

    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        sortComboBox.setItems(FXCollections.observableArrayList("id", "first_name", "last_name", "age", "grade"));
        sortComboBox.setValue("id");

        loadStudents();
    }

    private void loadStudents() {
        List<Student> students = studentDAO.findAll();
        ObservableList<Student> data = FXCollections.observableArrayList(students);
        studentTable.setItems(data);
        messageLabel.setText("" + students.size() + " étudiants chargés.");
    }

    @FXML
    private void searchById() {
        String text = searchIdField.getText();
        if (text == null || text.isBlank()) {
            loadStudents();
            return;
        }

        try {
            int id = Integer.parseInt(text.trim());
            Student student = studentDAO.findById(id);
            if (student == null) {
                studentTable.setItems(FXCollections.observableArrayList());
                messageLabel.setText("Aucun étudiant trouvé pour l'ID " + id + ".");
            } else {
                studentTable.setItems(FXCollections.observableArrayList(student));
                messageLabel.setText("Étudiant trouvé.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("L'ID doit être un nombre entier.");
        }
    }

    @FXML
    private void searchByAge() {
        String text = searchAgeField.getText();
        if (text == null || text.isBlank()) {
            loadStudents();
            return;
        }

        try {
            int age = Integer.parseInt(text.trim());
            List<Student> students = studentDAO.findByAge(age);
            studentTable.setItems(FXCollections.observableArrayList(students));
            messageLabel.setText(students.size() + " étudiant(s) trouvé(s) pour l'âge " + age + ".");
        } catch (NumberFormatException e) {
            messageLabel.setText("L'âge doit être un nombre entier.");
        }
    }

    @FXML
    private void searchByGradeRange() {
        String minText = searchMinGradeField.getText();
        String maxText = searchMaxGradeField.getText();

        if ((minText == null || minText.isBlank()) && (maxText == null || maxText.isBlank())) {
            loadStudents();
            return;
        }

        try {
            double minGrade = minText != null && !minText.isBlank() ? Double.parseDouble(minText.trim()) : 0.0;
            double maxGrade = maxText != null && !maxText.isBlank() ? Double.parseDouble(maxText.trim()) : 20.0;

            List<Student> students = studentDAO.findByGradeRange(minGrade, maxGrade);
            studentTable.setItems(FXCollections.observableArrayList(students));
            messageLabel.setText(students.size() + " étudiant(s) trouvé(s) avec des notes entre " + minGrade + " et " + maxGrade + ".");
        } catch (NumberFormatException e) {
            messageLabel.setText("Les notes doivent être des nombres valides.");
        }
    }

    @FXML
    private void sortStudents() {
        String sortBy = sortComboBox.getValue();
        if (sortBy == null || sortBy.isBlank()) {
            loadStudents();
            return;
        }

        List<Student> students = studentDAO.findAllSorted(sortBy);
        studentTable.setItems(FXCollections.observableArrayList(students));
        messageLabel.setText("Liste triée par " + sortBy + ".");
    }

    @FXML
    private void showStatistics() {
        StudentStatistics stats = StatisticsManager.calculateStatistics(studentDAO);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Statistiques des étudiants");
        alert.setHeaderText("Résumé des données");
        alert.setContentText(stats.toString());
        alert.showAndWait();
    }

    @FXML
    private void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter vers CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(studentTable.getScene().getWindow());

        if (file != null) {
            try {
                List<Student> students = studentDAO.findAll();
                StudentCSVManager.exportToCSV(students, file.getAbsolutePath());
                messageLabel.setText("Export CSV réussi : " + file.getName());
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'export CSV : " + e.getMessage());
            }
        }
    }

    @FXML
    private void importFromCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer depuis CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(studentTable.getScene().getWindow());

        if (file != null) {
            try {
                StudentCSVManager.importFromCSV(file.getAbsolutePath(), studentDAO);
                loadStudents();
                messageLabel.setText("Import CSV réussi depuis : " + file.getName());
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'import CSV : " + e.getMessage());
            }
        }
    }

    @FXML
    private void editSelectedStudent() throws IOException {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Veuillez sélectionner un étudiant à modifier.");
            return;
        }
        StudentFormController.setEditingStudent(selected);
        App.setRoot("student_form");
    }

    @FXML
    private void deleteSelectedStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Veuillez sélectionner un étudiant à supprimer.");
            return;
        }
        boolean deleted = studentDAO.delete(selected.getId());
        if (deleted) {
            loadStudents();
            messageLabel.setText("Étudiant supprimé avec succès.");
        } else {
            messageLabel.setText("Impossible de supprimer l'étudiant.");
        }
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void refreshList() {
        searchIdField.clear();
        searchAgeField.clear();
        searchMinGradeField.clear();
        searchMaxGradeField.clear();
        sortComboBox.setValue("id");
        loadStudents();
    }
}
