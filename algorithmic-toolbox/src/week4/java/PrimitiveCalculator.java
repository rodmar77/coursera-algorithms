package week4.java;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class PrimitiveCalculator {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final List<Integer> sequence = getSequence(in.nextInt());
            System.out.println(sequence.size() - 1);
            for (int i = 0; i < sequence.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(sequence.get(i));
            }
        }
    }

    private static List<Integer> getSequence(final int number) {
        final List<Integer> sequence = new ArrayList<>();
        sequence.add(number);

        return getSequence(number, number, sequence);
    }

    private static final Map<Integer, List<Integer>> cache = new HashMap<>();
    private static List<Integer> getSequence(final int number, final int current, final List<Integer> sequence) {
        if (sequence.size() > 35) {
            return null;
        } else if (cache.containsKey(current)) {
            final List<Integer> result = cache.get(current);
            if ((result != null) && (result.size() < sequence.size())) {
                return result;
            }
        }

        List<Integer> result = null;
        if (current == 0) {
            result = sequence;
        } else {
            if ((((current - 1) % 3) == 0) || ((current > 1) && (((current - 1) % 2) == 0))) {
                result = getSequence(number, current - 1, addToSequence(sequence, current - 1));
            }

            if ((current % 3) == 0) {
                result = getSmallest(
                                result,
                                getSequence(number, current / 3, addToSequence(sequence, current / 3)));
            } else if ((current % 2) == 0) {
                result = getSmallest(
                                result,
                                getSequence(number, current / 2, addToSequence(sequence, current / 2)));
            }
        }

        cache.put(current, result);
        return result;
    }

    private static List<Integer> getSmallest(final List<Integer>... sequences) {
        List<Integer> smallest = null;
        for (final List<Integer> sequence : sequences) {
            if ((smallest == null) || ((sequence != null) && (smallest.size() > sequence.size()))) {
                smallest = sequence;
            }
        }

        return smallest;
    }

    private static List<Integer> addToSequence(final List<Integer> sequence, final int number) {
        final List<Integer> result = new ArrayList<>(sequence);
        if (number > 0) {
            result.add(0, number);
        }

        return result;
    }
}
