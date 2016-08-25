package week2;

import java.util.Arrays;
import java.util.Scanner;

class ConstructSuffixArray {

    static class Data implements Comparable<Data> {
        private final int index;
        private final String value;

        Data(final int index, final String value) {
            this.index = index;
            this.value = value;
        }

        public int compareTo(final Data that) {
            return (this.value.compareTo(that.value));
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next();
            final Data[] array = new Data[text.length()];
            for (int i = 0; i < array.length; i++) {
                array[i] = new Data(i, cycle(text, i));
            }

            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(array[i].index);
            }

            System.out.println();
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
