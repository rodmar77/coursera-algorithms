package week1.java;
import java.util.Scanner;

class LargeFibonacci {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(fib(in.nextInt()));
        }
    }

    private static int fib(final int n) {
        int a = 0, b = 1;
        if (n == 0) {
            return a;
        } else {
            for (int i = 2; i <= n; i++) {
                final int tmp = (a + b) % 10;
                a = b;
                b = tmp;
            }

            return b;
        }
    }
}
