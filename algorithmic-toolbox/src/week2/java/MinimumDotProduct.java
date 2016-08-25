package week2.java;
import java.util.Arrays;
import java.util.Scanner;

class MinimumDotProduct {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int n = in.nextInt();
            final int[] a = readArray(in, n), b = readArray(in, n);

            long result = 0;
            for (int i = 0; i < n; i++) {
                result += (1L * a[n-i-1] * b[i]);
            }

            System.out.println(result);
        }
    }

    private static int[] readArray(final Scanner in, final int length) {
        final int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = in.nextInt();
        }

        Arrays.sort(result);
        return result;
    }
}