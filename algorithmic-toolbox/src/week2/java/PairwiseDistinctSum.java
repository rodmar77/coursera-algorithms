package week2.java;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class PairwiseDistinctSum {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final List<Integer> result = maximumPDS(in.nextInt());
            System.out.println(result.size());
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(result.get(i));
            }

            System.out.println();
        }
    }

    private static List<Integer> maximumPDS(final int number) {
        if (number <= 2) {
            return Arrays.asList(number);
        }

        final List<Integer> result = new ArrayList<>();
        int current = number, next = 1;
        while (current > 0) {
            if (current - next > next) {
                result.add(next);
                current -= next;
                next++;
            } else {
                result.add(current);
                current = 0;
            }
        }

        return result;
    }

}
