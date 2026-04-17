package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudentCSVManager {

    public static void exportToCSV(List<Student> students, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Écrire l'en-tête
            writer.println("ID,Prénom,Nom,Âge,Note");

            // Écrire les données
            for (Student student : students) {
                writer.printf("%d,%s,%s,%d,%.2f%n",
                    student.getId(),
                    escapeCSV(student.getFirstName()),
                    escapeCSV(student.getLastName()),
                    student.getAge(),
                    student.getGrade());
            }
        }
    }

    public static void importFromCSV(String filePath, StudentDAO studentDAO) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Ignorer l'en-tête
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");

            if (parts.length >= 5) {
                try {
                    String firstName = unescapeCSV(parts[1]);
                    String lastName = unescapeCSV(parts[2]);
                    int age = Integer.parseInt(parts[3].trim());
                    double grade = Double.parseDouble(parts[4].trim());

                    Student student = new Student(firstName, lastName, age, grade);
                    studentDAO.add(student);
                } catch (NumberFormatException e) {
                    System.err.println("Erreur de format à la ligne " + (i + 1) + ": " + line);
                }
            }
        }
    }

    private static String escapeCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static String unescapeCSV(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }
}