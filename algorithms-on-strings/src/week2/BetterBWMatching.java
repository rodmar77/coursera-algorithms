package week2;
import java.util.Arrays;
import java.util.Scanner;

class BetterBWMatching {

    static class BetterBWData {
        private final int[] firstOccurrence;
        private final int[][] count;

        final int length;

        BetterBWData(final String text) {
            this.length = text.length();

            this.firstOccurrence = new int[5];
            Arrays.fill(this.firstOccurrence, -10000000);

            this.count = new int[length + 1][5];
            process(text);
        }

        void process(final String text) {
            final char[] last = text.toCharArray();
            final char[] first = last.clone();
            Arrays.sort(first);

            for (int i = 0; i < text.length(); i++) {
                final int firstSymbolIndex = getSymbolIndex(first[i]);
                if (firstOccurrence[firstSymbolIndex] < 0) {
                    firstOccurrence[firstSymbolIndex] = i;
                }

                final int lastSymbolIndex = getSymbolIndex(last[i]);
                System.arraycopy(count[i], 0, count[i+1], 0, 5);
                count[i+1][lastSymbolIndex]++;
            }
        }

        int getCount(final int index, final char symbol) {
            return count[index][getSymbolIndex(symbol)];
        }

        int getFirstOccurrence(final char symbol) {
            return firstOccurrence[getSymbolIndex(symbol)];
        }

        private int getSymbolIndex(final char symbol) {
            switch (symbol) {
                case 'A': return 0;
                case 'C': return 1;
                case 'T': return 2;
                case 'G': return 3;
                case '$': return 4;
                default: return -1;
            }
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next();
            final int[] answers = new int[in.nextInt()];

            final BetterBWData data = new BetterBWData(text);
            for (int i = 0; i < answers.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(betterBwMatching(data, in.next()));
            }

            System.out.println();
        }
    }

    private static int betterBwMatching(final BetterBWData data, final String pattern) {
        try {
            int top = 0, bottom = data.length - 1, index = pattern.length() - 1;
            while (top <= bottom) {
                if (index >= 0) {
                    final char symbol = pattern.charAt(index--);
                    top = data.getFirstOccurrence(symbol) + data.getCount(top, symbol);
                    if (top < 0) {
                        break;
                    }

                    bottom = data.getFirstOccurrence(symbol) + data.getCount(bottom + 1, symbol) - 1;
                    if (bottom < 0) {
                        break;
                    }
                } else {
                    return bottom - top + 1;
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
        }

        return 0;
    }
}