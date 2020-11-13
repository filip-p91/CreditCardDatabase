package card;

import java.util.Random;

public class CreditCard {
    private final String creditCardPin;
    private String creditCardNumber;
    private final int balance;

    public CreditCard() {
        Random random = new Random();
        final String BIN = "400000";

        creditCardNumber = BIN + String.format("%9d", random.nextInt(1000000000)).replace(' ', '0');
        creditCardNumber = creditCardNumber + LuhnAlgorithm.lastDigit(creditCardNumber);

        creditCardPin = String.format("%4d", random.nextInt(10000)).replace(' ', '0');
        balance = 0;
    }

    public CreditCard(String creditCardNumber, String creditCardPin, int balance) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardPin = creditCardPin;
        this.balance = balance;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCreditCardPin() {
        return creditCardPin;
    }

    public int getBalance() {
        return balance;
    }
}