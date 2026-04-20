package com.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentCSVManagerTest {

    @Test
    void exportToCSVShouldWriteHeaderAndStudentLines() throws IOException {
        List<Student> students = List.of(
            new Student(1, "Alice", "Dupont", 18, 2),
            new Student(2, "Bob", "Martin", 20, 1)
        );

        Path tempFile = Files.createTempFile("students", ".csv");
        try {
            StudentCSVManager.exportToCSV(students, tempFile.toString());
            List<String> lines = Files.readAllLines(tempFile);

            assertEquals(3, lines.size());
            assertEquals("ID,Prénom,Nom,Âge,Note", lines.get(0));
            assertEquals("1,Alice,Dupont,18,14.50", lines.get(1));
            assertEquals("2,Bob,Martin,20,12.00", lines.get(2));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void importFromCSVShouldCallDaoAddForValidRows() throws IOException {
        Path tempFile = Files.createTempFile("students-import", ".csv");
        List<String> content = List.of(
            "ID,Prénom,Nom,Âge,Note",
            "1,Claire,Bernard,17,15.25",
            "2,Lucas,Le Grand,21,13.10"
        );
        Files.write(tempFile, content);

        List<Student> added = new ArrayList<>();
        StudentDAO stubDao = new StudentDAO() {
            @Override
            public boolean add(Student student) {
                added.add(student);
                return true;
            }
        };

        try {
            StudentCSVManager.importFromCSV(tempFile.toString(), stubDao);

            assertEquals(2, added.size());
            assertEquals("Claire", added.get(0).getFirstName());
            assertEquals("Bernard", added.get(0).getLastName());
            assertEquals(17, added.get(0).getAge());
            assertEquals(1, added.get(0).getPromotion());
            assertEquals("Lucas", added.get(1).getFirstName());
            assertEquals("Le Grand", added.get(1).getLastName());
            assertEquals(21, added.get(1).getAge());
            assertEquals(2, added.get(1).getPromotion());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
