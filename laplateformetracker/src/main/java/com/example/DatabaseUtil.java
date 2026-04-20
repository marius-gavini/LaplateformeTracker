package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_Laplateforme_tracker";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if (connection != null) {
                System.out.println("Database connection established successfully");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection to " + DB_URL);
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public static void initializeDatabase() {
        // CHECK IF TABLES EXIST
        if (tablesExist()) {
            System.out.println("Database tables already exist - skipping initialization");
            return;
        }

        // CREATE PROMOTIONS
        String createPromotionsTableSql = "CREATE TABLE promotions ("
                + "id SERIAL PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL, "
                + "year INT NOT NULL"
                + ");";
        
        // CREATE STUDENTS
        String createStudentTableSql = "CREATE TABLE students ("
                + "id SERIAL PRIMARY KEY, "
                + "first_name VARCHAR(100) NOT NULL, "
                + "last_name VARCHAR(100) NOT NULL, "
                + "age INTEGER NOT NULL, "
                + "promotion_id INT NOT NULL REFERENCES promotions(id) "
                + ");";

        // CREATE USERS
        String createUsersTableSql = "CREATE TABLE users ("
                + "id SERIAL PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'admin'))"
                + ");";

        // CREATE GRADES
        String createGradesTableSql = "CREATE TABLE grades ("
                + "id SERIAL PRIMARY KEY, "
                + "user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE, "
                + "subject VARCHAR(50) NOT NULL, "
                + "grade NUMERIC(4,2) NOT NULL "
                + ");";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(createPromotionsTableSql);
            statement.execute(createStudentTableSql);
            statement.execute(createUsersTableSql);
            statement.execute(createGradesTableSql);
            System.out.println("Database initialized successfully - all tables created");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean tablesExist() {
        String checkTablesSql = "SELECT COUNT(*) FROM information_schema.tables "
                + "WHERE table_schema = 'public' AND table_name IN ('students', 'users', 'promotions', 'grades')";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(checkTablesSql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int tableCount = resultSet.getInt(1);
                return tableCount == 4;
            }
        } catch (SQLException e) {
            System.out.println("Checking table existence: " + e.getMessage());
        }
        return false;
    }
}
