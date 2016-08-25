package week1.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class SmallFibonacci {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(fib(in.nextInt()));
        }
    }

    private static Map<Integer, Long> memory = new HashMap<>();

    private static long fib(final int n) {
        return memory.computeIfAbsent(n, k -> {
            if (k == 0) return 0L;
            else if (k == 1) return 1L;
            else return fib(k - 1) + fib(k - 2);
        });
    }
}
