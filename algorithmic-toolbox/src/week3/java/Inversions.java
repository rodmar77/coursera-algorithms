package week3.java;
import java.util.Arrays;
import java.util.Scanner;

class Inversions {

    static class Result {
        private final int[] array;
        private final long inversions;

        Result(final int[] array, final long inversions) {
            this.array = array;
            this.inversions = inversions;
        }

        public String toString() {
            return this.inversions + " -> " + Arrays.toString(this.array);
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            final int[] array = new int[in.nextInt()];
            for (int i = 0; i < array.length; i++) {
                array[i] = in.nextInt();
            }

            System.out.println(inversionCount(array));
        }
    }

    private static long inversionCount(final int[] array) {
        final Result sorted = sort(array);
        return sorted.inversions;
    }

    private static Result sort(final int[] array) {
        if (array.length == 1) {
            return new Result(array, 0);
        }

        final int[][] arrays = split(array);
        final Result left = sort(arrays[0]), right = sort(arrays[1]);
        return merge(left, right);
    }

    private static Result merge(final Result left, final Result right) {
        final int[] result = new int[left.array.length + right.array.length];
        long inv = 0;
        for (int i = 0, li = 0, ri = 0; i < result.length; i++) {
            if (li == left.array.length) {
                System.arraycopy(right.array, ri, result, i, result.length - i);
                break;
            } else if (ri == right.array.length) {
                System.arraycopy(left.array, li, result, i, result.length - i);
                break;
            } else if (right.array[ri] < left.array[li]) {
                inv += (left.array.length - li);
                result[i] = right.array[ri++];
            } else {
                result[i] = left.array[li++];
            }
        }

        return new Result(result, left.inversions + right.inversions + inv);
    }

    private static int[][] split(final int[] array) {
        final int middle = array.length / 2;
        final int[] left = new int[middle], right = new int[array.length - middle];

        System.arraycopy(array, 0, left, 0, left.length);
        System.arraycopy(array, middle, right, 0, right.length);
        return new int[][] { left, right };
    }
}