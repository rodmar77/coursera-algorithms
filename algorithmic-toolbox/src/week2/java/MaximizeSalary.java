package week2.java;

import java.util.Arrays;
import java.util.Scanner;

class MaximizeSalary {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String[] ns = new String[in.nextInt()];
            for (int i = 0; i < ns.length; i++) {
                ns[i] = in.next();
            }

            Arrays
                .sort(
                    ns, 
                    (a, b) -> Integer.compare(
                                Integer.parseInt(b + a), 
                                Integer.parseInt(a + b)));

            for (final String n : ns) {
                System.out.print(n);
            }

            System.out.println();
        }
    }
}
