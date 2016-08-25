package week4.java;
import java.util.Scanner;

class TakeGold {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int maxValue = in.nextInt();
            final int[] values = new int[in.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = in.nextInt();
            }

            System.out.println(getMaxPossibleAmount(maxValue, values));
        }
    }

    private static int getMaxPossibleAmount(final int maxValue, final int[] values) {
        final int[][] result = new int[maxValue + 1][values.length + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int w = 1; w <= maxValue; w++) {
                result[w][i] = result[w][i-1];
                if (values[i-1] <= w) {
                    final int val = result[w - values[i-1]][i - 1] + values[i-1];
                    if (result[w][i] < val) {
                        result[w][i] = val;
                    }
                }
            }
        }

        return result[maxValue][values.length];
    }
}
