package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentStatisticsTest {

    @Test
    void gettersShouldReturnValues() {
        StudentStatistics stats = new StudentStatistics(14.5, 10, 3, 5, 2);

        assertEquals(14.5, stats.getAverageGradeByPromotion());
        assertEquals(10, stats.getTotalStudents());
        assertEquals(3, stats.getStudentsUnder18());
        assertEquals(5, stats.getStudents18To25());
        assertEquals(2, stats.getStudentsOver25());
    }

    @Test
    void toStringShouldContainFormattedSummary() {
        StudentStatistics stats = new StudentStatistics(15.0, 12, 2, 7, 3);
        String summary = stats.toString();

        assertTrue(summary.contains("Nombre total d'étudiants: 12"));
        assertTrue(summary.contains(String.format("Moyenne des notes: %.2f/20", 15.0)));
        assertTrue(summary.contains("Étudiants de moins de 18 ans: 2"));
        assertTrue(summary.contains("Étudiants de 18 à 25 ans: 7"));
        assertTrue(summary.contains("Étudiants de plus de 25 ans: 3"));
    }
}
