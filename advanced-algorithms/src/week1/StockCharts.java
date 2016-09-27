package week1;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class StockCharts {

    public static void main(String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int stockCount = in.nextInt(), stockSize = in.nextInt();

            final int[][] stocks = new int[stockCount][stockSize];
            for (int i = 0; i < stockCount; i++) {
                for (int j = 0; j < stockSize; j++) {
                    stocks[i][j] = in.nextInt();
                }
            }

            final boolean[][] g = new boolean[stockCount][stockCount];
            for (int i = 0; i < stockCount; i++) {
                for (int j = 0; j < stockCount; j++) {
                    g[i][j] = below(stocks[i], stocks[j]);
                }
            }

            int result = 0;
            final int[] match = new int[stockCount];
            Arrays.fill(match, -1);

            final BitSet seen = new BitSet(stockCount);
            for (int i = 0; i < stockCount; i++) {
                seen.clear();
                if (assign(stockCount, i, match, g, seen)) {
                    result++;
                }
            }

            System.out.println(stockCount - result);
        }
    }

    private static boolean assign(
                                final int stockCount, 
                                final int u, 
                                final int[] match, 
                                final boolean[][] g, 
                                final BitSet seen) {

        for (int v = 0; v < stockCount; ++v) {
            if ((g[u][v]) && (!seen.get(v))) {
                seen.set(v);
                if ((match[v] == -1) || (assign(stockCount, match[v], match, g, seen))) {
                    match[v] = u;
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean below(final int[] a, final int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= b[i]) {
                return false;
            }
        }

        return true;
    }
}