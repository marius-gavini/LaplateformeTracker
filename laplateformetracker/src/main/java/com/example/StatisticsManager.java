package com.example;

public class StatisticsManager {

    public static StudentStatistics calculateStatistics(StudentDAO studentDAO) {
        double averageGrade = studentDAO.getAverageGrade();
        int totalStudents = studentDAO.getStudentCount();
        int studentsUnder18 = studentDAO.getStudentCountByAgeRange(0, 17);
        int students18To25 = studentDAO.getStudentCountByAgeRange(18, 25);
        int studentsOver25 = studentDAO.getStudentCountByAgeRange(26, 120);

        return new StudentStatistics(averageGrade, totalStudents, studentsUnder18, students18To25, studentsOver25);
    }
}