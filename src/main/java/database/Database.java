package database;

import java.sql.*;
import java.util.Scanner;

public class Database {

    private static final Scanner scan = new Scanner(System.in);
    private static Database instance;
    private final String url;

    private Database() {
        this.url = createUrl();
        this.createDatabase();
        this.createTable();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    private String createUrl() {
        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();
        return "jdbc:mysql://localhost:3306?user=" + username + "&password=" + password;
    }

    private void createDatabase() {
        String statement = "CREATE DATABASE IF NOT EXISTS credit_cards; ";
        String use = "USE credit_cards; ";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(statement);
            stmt.execute(use);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String statement = "CREATE TABLE IF NOT EXISTS card ("
                + " id INT AUTO_INCREMENT PRIMARY KEY,"
                + " number VARCHAR(50),"
                + " pin VARCHAR(50),"
                + " balance INT DEFAULT 0"
                + ");";
        try (Connection conn = connectTable();
             Statement stmt = conn.createStatement()) {
            stmt.execute(statement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Wrong username or password!");
            System.exit(0);
        }
    }

    public Connection connectTable() {
        Connection conn = null;
        try {
            String[] splitUrl = url.split("jdbc:mysql://localhost:3306");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/credit_cards" + splitUrl[1]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}