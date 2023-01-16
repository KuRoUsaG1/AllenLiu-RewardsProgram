package com.example.rewards.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionChecker {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/rewardsdb";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
