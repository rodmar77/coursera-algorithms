package week1.java;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HugeFibonacci {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final long number = in.nextLong();
            final int mod = in.nextInt();

            final int index = (int) (number % getPisano(mod));
            System.out.println(fibonacciMod(index, mod));
        }
    }

    private static int getPisano(final int mod) {
        int length = getLowerBound(mod);

        long current = 0;
        for (int i = 0; i < length; i++) {
            current += fibonacciMod(i, mod);
        }

        long proposed = 0;
        for (int i = length + 1; i <= 2*length; i++) {
            proposed += fibonacciMod(i, mod);
        }

        long appendCurrent = 0, appendProposed = 0;
        while ((proposed != current) || (appendCurrent != appendProposed)) {
            appendCurrent = fibonacciMod(length, mod);
            appendProposed = fibonacciMod(2*length + 2, mod);

            current += appendCurrent;
            proposed -= fibonacciMod(length + 1, mod);
            proposed += fibonacciMod(2*length + 1, mod) + appendProposed;
            length++;
        }

        return length + 1;
    }

    private static final Map<Integer, Long> cache = new HashMap<>();
    private static long fibonacciMod(final int index, final int mod) {
        return cache.computeIfAbsent(index, k -> {
            if (index == 0) {
                return 0L;
            } else if (index == 1) {
                return 1L;
            } else {
                return (fibonacciMod(index - 1, mod) + fibonacciMod(index - 2, mod)) % mod;
            }
        });
    }

    private static int getLowerBound(final int number) {
        return 2*getLucasNumberIndex(number);
    }

    private static int getLucasNumberIndex(final int max) {
        int a = 2, b = 1, index = 1;
        while (b < max) {
            final int tmp = a + b;
            a = b;
            b = tmp;
            index++;
        }

        return (index - 1);
    }

}
