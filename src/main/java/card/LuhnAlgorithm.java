package card;

public class LuhnAlgorithm {

    public static String lastDigit(String cardNumber) {
        int sum = 0;
        int[] luhnAlgorithmHelper = new int[cardNumber.length()];
        for (int i = 0; i < luhnAlgorithmHelper.length; i++) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

            if (i % 2 == 0) {
                luhnAlgorithmHelper[i] = digit * 2;
                if (luhnAlgorithmHelper[i] > 9) {
                    luhnAlgorithmHelper[i] -= 9;
                }
            } else {
                luhnAlgorithmHelper[i] = digit;
            }
            sum += luhnAlgorithmHelper[i];
        }
        return String.valueOf((10 - sum % 10) % 10);
    }
}