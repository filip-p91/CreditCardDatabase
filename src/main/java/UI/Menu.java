package UI;

import card.CreditCard;
import database.DatabaseManager;

import java.util.Scanner;

public class Menu {

    private static final Scanner scan = new Scanner(System.in);
    private final DatabaseManager databaseManager;

    public Menu() {
        this.databaseManager = new DatabaseManager();
    }

    public void start() {

        while (true) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            String command = scan.nextLine();

            switch (command) {
                case "1":
                    System.out.println("\nYour card has been created");
                    CreditCard card = new CreditCard();
                    System.out.println("Your card number:\n" + card.getCreditCardNumber());
                    System.out.println("Your card PIN:\n" + card.getCreditCardPin() + "\n");
                    databaseManager.insertCard(card.getCreditCardNumber(), card.getCreditCardPin(), card.getBalance());
                    break;
                case "2":
                    System.out.println("\nEnter your card number:");
                    String cardNumber = scan.nextLine();
                    System.out.println("Enter your PIN:");
                    String pinNumber = scan.nextLine();
                    CreditCard selectedCard = databaseManager.selectCard(cardNumber, pinNumber);

                    if (selectedCard != null) {
                        logIn(selectedCard);
                    } else {
                        System.out.println("Wrong card number or PIN!\n");
                    }
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Wrong command!\n");
            }
        }
    }

    public void logIn(CreditCard card) {
        System.out.println("\nYou have successfully logged in!");
        while (true) {
            System.out.println("\n" +
                    "1. Balance\n" +
                    "2. Add Income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            String command2 = scan.nextLine();
            switch (command2) {
                case "0":
                    System.exit(0);
                case "1":
                    System.out.println("\nBalance: " + databaseManager.getBalance(card.getCreditCardNumber()));
                    break;
                case "2":
                    System.out.println("\nEnter income:");
                    String income = scan.nextLine();
                    databaseManager.updateBalance(card.getCreditCardNumber(), Integer.parseInt(income));
                    break;
                case "3":
                    databaseManager.doTransfer(card);
                    break;
                case "4":
                    databaseManager.closeAccount(card.getCreditCardNumber());
                    System.out.println("The account has been closed!\n");
                    return;
                case "5":
                    System.out.println("Bye!\n");
                    return;
                default:
                    System.out.println("Wrong command!\n");
            }
        }
    }
}