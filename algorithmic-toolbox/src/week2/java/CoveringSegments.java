package week2.java;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class CoveringSegments {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int[][] ns = new int[in.nextInt()][];
            for (int i = 0; i < ns.length; i++) {
                ns[i] = new int[] { in.nextInt(), in.nextInt() };
            }

            Arrays.sort(ns, (a, b) -> (a[1] != b[1]) ? a[1] - b[1] : b[0] - a[0]);
            final List<Integer> result = new ArrayList<>();

            int max = ns[0][1];
            result.add(max);
            for (int i = 1; i < ns.length; i++) {
                if (max < ns[i][0]) {
                    max = ns[i][1];
                    result.add(max);
                }
            }

            System.out.println(result.size());
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(result.get(i));
            }
        }
    }

}
