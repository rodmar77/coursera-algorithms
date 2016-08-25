package week2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class ReconstructBurrowsWheeler {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final char[] original = in.next().toCharArray();

            final char[] sorted = original.clone();
            Arrays.sort(sorted);

            final Map<Character, AtomicInteger> leftCounters = new HashMap<>(), rightCounters = new HashMap<>();
            final Map<String, String> reference = new HashMap<>();

            for (int i = 0; i < sorted.length; i++) {
                final int lc = leftCounters.computeIfAbsent(sorted[i], c -> new AtomicInteger(0)).incrementAndGet();
                final int rc = rightCounters.computeIfAbsent(original[i], c -> new AtomicInteger(0)).incrementAndGet();

                reference.put("" + sorted[i] + lc, "" + original[i] + rc);
            }

            final char[] result = new char[original.length];
            String key = "$1";
            int index = result.length - 2;
            while (index >= 0) {
                final String value = reference.get(key);
                result[index--] = value.charAt(0);
                key = value;
            }

            result[result.length - 1] = '$';
            System.out.println(new String(result));
        }
    }
}
