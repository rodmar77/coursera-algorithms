package week1.java;
import java.util.Scanner;

class GreatestCommonDivisor {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(gcd(in.nextLong(), in.nextLong()));
        }
    }

    private static long gcd(final long a, final long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}
