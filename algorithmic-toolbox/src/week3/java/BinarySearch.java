package week3.java;
import java.util.Arrays;
import java.util.Scanner;

class BinarySearch {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int[][] as = readArray(in, true), bs = readArray(in, false);
            for (int i = 0; i < bs.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                final int index = Arrays.binarySearch(as, bs[i], (x, y) -> x[1] - y[1]);
                if (index < 0) {
                    System.out.print("-1");
                } else {
                    System.out.print(as[index][0]);
                }
            }
        }
    }

    private static int[][] readArray(final Scanner in, final boolean sort) {
        final int[][] result = new int[in.nextInt()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new int[] { i, in.nextInt() };
        }

        if (sort) {
            Arrays.sort(result, (a, b) -> a[1] - b[1]);
        }

        return result;
    }

}
