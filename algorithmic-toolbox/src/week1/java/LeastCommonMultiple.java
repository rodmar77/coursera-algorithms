package week1.java;
import java.util.Scanner;

class LeastCommonMultiple {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(lcm(in.nextLong(), in.nextLong()));
        }
    }

    private static long lcm(final long a, final long b) {
        return (a * b) / gcd(a, b);
    }

    private static long gcd(final long a, final long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}
