package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, age, promotion_id FROM student ORDER BY id";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapResultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    public Student findById(int id) {
        String sql = "SELECT id, first_name, last_name, age, promotion_id FROM student WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToStudent(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> findByAge(int age) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, age, promotion_id FROM student WHERE age = ? ORDER BY id";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, age);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> findByPromotionId(int promotionId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, age, promotion_id FROM student WHERE promotion_id = ? ORDER BY id";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, promotionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapResultSetToStudent(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> findAllSorted(String sortBy) {
        List<Student> students = new ArrayList<>();
        
        // Validate sortBy to prevent SQL injection - only allow valid column names
        String validSortColumn;
        switch (sortBy.toLowerCase()) {
            case "first_name":
            case "last_name":
            case "age":
            case "promotion_id":
            case "id":
                validSortColumn = sortBy;
                break;
            default:
                validSortColumn = "id";  // Default to id if invalid column provided
        }
        
        String sql = "SELECT id, first_name, last_name, age, promotion_id FROM student ORDER BY " + validSortColumn + " ASC";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapResultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sorted students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    public boolean add(Student student) {
        String sql = "INSERT INTO student (first_name, last_name, age, promotion_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setInt(4, student.getPromotion());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Student student) {
        String sql = "UPDATE student SET first_name = ?, last_name = ?, age = ?, promotion_id = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getAge());
            statement.setInt(4, student.getPromotion());
            statement.setInt(5, student.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting student with id " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public double getAverageGradeByUserId(int userId) {
        String sql = "SELECT AVG(grade) AS avg_grade FROM grades WHERE user_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_grade");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // si aucune note
    }

    public double getAverageGradeByPromotion(int promotionId) {
        String sql = "SELECT AVG(grade) AS avg_grade FROM grades WHERE promotion_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promotionId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_grade");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public int getStudentCount() {
        String sql = "SELECT COUNT(*) as count FROM student";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStudentCountByAgeRange(int minAge, int maxAge) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE age BETWEEN ? AND ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, minAge);
            statement.setInt(2, maxAge);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Student mapResultSetToStudent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int age = resultSet.getInt("age");
        int promotion_id = resultSet.getInt("promotion_id");
        return new Student(id, firstName, lastName, age, promotion_id);
    }
}
