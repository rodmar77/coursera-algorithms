package week3.java;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MajorityElement {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final Map<Integer, Integer> totals = new HashMap<>();
            final int count = in.nextInt();
            final int needed = ((count % 2) == 0) ? ((count/2) + 1) : ((count + 1) / 2);

            final Integer defaultValue = 0;
            for (int i = 0; i < count; i++) {
                final Integer key = in.nextInt();
                totals.put(key, totals.getOrDefault(key, defaultValue) + 1);
            }

            final boolean hasMajority = totals.values().stream().anyMatch(n -> n >= needed);
            System.out.println(hasMajority ? 1 : 0);
        }
    }
}