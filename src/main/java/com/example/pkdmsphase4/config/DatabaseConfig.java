package com.example.pkdmsphase4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Scanner;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter MySQL host (e.g., localhost):");
        String host = scanner.nextLine();

        System.out.println("Enter MySQL port (default 3306):");
        String port = scanner.nextLine();
        if (port.isBlank()) port = "3306";

        System.out.println("Enter database name:");
        String dbName = scanner.nextLine();

        System.out.println("Enter MySQL username:");
        String username = scanner.nextLine();

        System.out.println("Enter MySQL password:");
        String password = scanner.nextLine();

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&serverTimezone=UTC";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        System.out.println("Connecting to database at: " + url);

        return dataSource;
    }
}
