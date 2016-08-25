package week2;
import java.util.Scanner;
import java.util.stream.IntStream;

class BurrowsWheelerTransform {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next();
            System.out.println(
                            IntStream
                                .range(0, text.length())
                                .mapToObj(n -> cycle(text, n))
                                .sorted()
                                .map(n -> n.substring(n.length() - 1))
                                .reduce((a, b) -> a + b)
                                .orElse(null));
        }
    }

    private static String cycle(final String text, final int n) {
        if (n == 0) {
            return text;
        }

        final char[] result = new char[text.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = text.charAt((i + n) % result.length);
        }

        return new String(result);
    }
}
