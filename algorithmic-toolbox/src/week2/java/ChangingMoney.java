package week2.java;
import java.util.Scanner;

class ChangingMoney {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(getTotal(in.nextInt()));
        }
    }

    private static int getTotal(final int amount) {
        if (amount < 5) {
            return amount;
        } else if (amount < 10) {
            return 1 + getTotal(amount - 5);
        } else {
            return (amount / 10) + getTotal(amount % 10);
        }
    }

}
