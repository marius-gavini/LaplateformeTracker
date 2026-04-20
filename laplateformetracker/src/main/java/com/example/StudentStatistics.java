package com.example;

public class StudentStatistics {
    
    private final double averageGradeByPromotion;
    private final int totalStudents;
    private final int studentsUnder18;
    private final int students18To25;
    private final int studentsOver25;

    public StudentStatistics(double averageGradeByPromotion, int totalStudents, int studentsUnder18, int students18To25, int studentsOver25) {
    
        this.averageGradeByPromotion = averageGradeByPromotion;
        this.totalStudents = totalStudents;
        this.studentsUnder18 = studentsUnder18;
        this.students18To25 = students18To25;
        this.studentsOver25 = studentsOver25;
    }


    public double getAverageGradeByPromotion() {
        return averageGradeByPromotion;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public int getStudentsUnder18() {
        return studentsUnder18;
    }

    public int getStudents18To25() {
        return students18To25;
    }

    public int getStudentsOver25() {
        return studentsOver25;
    }

    @Override
    public String toString() {
        return String.format(
            "Statistiques des étudiants:\n" +
            "Nombre total d'étudiants: %d\n" +
            "Moyenne des notes: %.2f/20\n" +
            "Étudiants de moins de 18 ans: %d\n" +
            "Étudiants de 18 à 25 ans: %d\n" +
            "Étudiants de plus de 25 ans: %d",
            totalStudents, averageGradeByPromotion, studentsUnder18, students18To25, studentsOver25
        );
    }
}