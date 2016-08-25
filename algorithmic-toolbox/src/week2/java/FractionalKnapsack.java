package week2.java;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

class FractionalKnapsack {

    private static final NumberFormat formatter = new DecimalFormat("#0.0000"); 

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int[][] ns = new int[in.nextInt()][];
            final int capacity = in.nextInt();

            for (int i = 0; i < ns.length; i++) {
                ns[i] = new int[] { in.nextInt(), in.nextInt() };
            }

            Arrays.sort(ns, (a, b) -> Double.compare(b[0]/(b[1]*1d), a[0]/(a[1]*1d)));
            double result = 0, used = 0;
            for (int i = 0; (i < ns.length) && (used < capacity); i++) {
                if (used + ns[i][1] <= capacity) {
                    result += ns[i][0];
                    used += ns[i][1];
                } else {
                    final double diff = (capacity - used) / ns[i][1];
                    result += (ns[i][0] * diff);
                    used = capacity;
                }
            }

            System.out.println(formatter.format(result));
        }
    }
}
