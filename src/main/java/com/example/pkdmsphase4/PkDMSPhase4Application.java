package com.example.pkdmsphase4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
public class PkDMSPhase4Application {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Ask user for database details
        System.out.print("Enter MySQL database name (ex: pokemondb): ");
        String dbName = scanner.nextLine().trim();

        System.out.print("Enter MySQL username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter MySQL password: ");
        String password = scanner.nextLine().trim();

        // Build JDBC URL
        String jdbcUrl = "jdbc:mysql://localhost:3306/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&requireSSL=false&serverTimezone=UTC";

        // Set Spring datasource properties dynamically
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", username);
        System.setProperty("spring.datasource.password", password);

        // Test connection before launching the Spring Boot app
        System.out.println("\nTesting database connection...");
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            if (conn != null) {
                System.out.println("Connected successfully to database: " + dbName);
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to database. Please check credentials and try again.");
            System.err.println("Error: " + e.getMessage());
            return; // Stop execution if connection fails
        }

        // Launch the Spring Boot application
        System.out.println("\nStarting PkDMS Phase 4 application...\n");
        SpringApplication.run(PkDMSPhase4Application.class, args);
    }
}
