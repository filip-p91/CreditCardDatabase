package database;

import card.CreditCard;

import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {

    private static final Scanner scan = new Scanner(System.in);
    private final Database db;

    public DatabaseManager() {
        this.db = Database.getInstance();
    }

    public void insertCard(String cardNumber, String pinNumber, int balance) {
        String statement = "INSERT INTO card(number,pin, balance) VALUES('" + cardNumber + "', '" + pinNumber + "', " + balance + ")";
        executeUpdate(statement);
    }

    public CreditCard selectCard(String cardNumber, String cardPin) {
        String statement = "SELECT number, pin, balance FROM card WHERE number = " + cardNumber + " AND pin = " + cardPin;
        try (Connection conn = db.connectTable();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(statement)) {
            while (rs.next()) {
                int balance = rs.getInt("balance");
                return new CreditCard(cardNumber, cardPin, balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public CreditCard selectCard(String cardNumber) {
        String statement = "SELECT number, pin, balance FROM card WHERE number = " + cardNumber;
        try (Connection conn = db.connectTable();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(statement)) {
            while (rs.next()) {
                String cardPin = rs.getString("pin");
                int balance = rs.getInt("balance");
                return new CreditCard(cardNumber, cardPin, balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getBalance(String cardNumber) {
        String statement = "SELECT balance FROM card WHERE number = " + cardNumber;
        try (Connection conn = db.connectTable();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(statement)) {
            while (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void updateBalance(String cardNumber, int balanceChange) {
        String statement = "UPDATE card SET balance = balance + " + balanceChange + " WHERE number = " + cardNumber;
        executeUpdate(statement);
    }

    public void doTransfer(CreditCard fromCard) {
        System.out.println("\nEnter number of card to transfer money:");
        String destCardNumber = scan.nextLine();
        if (destCardNumber.equals(fromCard.getCreditCardNumber())) {
            System.out.println("\nYou can't transfer money to the same account!");
            return;
        } else if (selectCard(destCardNumber) == null) {
            System.out.println("\nWrong card number!");
            return;
        }
        System.out.println("\nEnter how much money you want to transfer:");
        int transfer = Integer.parseInt(scan.nextLine());
        if (transfer > fromCard.getBalance()) {
            System.out.println("\nNot enough money!");
        } else if (transfer <= 0) {
            System.out.println("\nValue have to be greater than 0!");
        } else {
            updateBalance(fromCard.getCreditCardNumber(), -transfer);
            updateBalance(destCardNumber, transfer);
            System.out.println("\nSuccess!");
        }
    }

    public void closeAccount(String cardNumber) {
        String statement = "DELETE FROM card WHERE number = '" + cardNumber + "';";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement) {
        try (Connection conn = db.connectTable(); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
