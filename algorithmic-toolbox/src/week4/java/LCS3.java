package week4.java;
import java.util.Scanner;

class LCS3 {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(lcs3(readArray(in), readArray(in), readArray(in)));
        }
    }

    private static int lcs3(final int[] a, final int[] b, final int[] c) {
        final int[][][] result = new int[a.length + 1][b.length + 1][c.length + 1];
        for (int i = 1; i < result.length; i++) {
            final int ni = a[i - 1];
            for (int j = 1; j < result[i].length; j++) {
                final int nj = b[j - 1];
                for (int k = 1; k < result[i][j].length; k++) {
                    final int nk = c[k - 1];
                    if ((ni == nj) && (nj == nk)) {
                        result[i][j][k] = 1 + result[i-1][j-1][k-1];
                    } else {
                        result[i][j][k] = max(result[i-1][j][k], result[i][j-1][k], result[i][j][k-1]);
                    }
                }
            }
        }

        return result[a.length][b.length][c.length];
    }

    private static int max(final int i, final int j, final int k) {
        return ((i > j) && (i > k)) ? i : ((j > k) ? j : k);
    }

    private static int[] readArray(final Scanner in) {
        final int[] result = new int[in.nextInt()];
        for (int i = 0; i < result.length; i++) {
            result[i] = in.nextInt();
        }

        return result;
    }
}
