package week3.java;
import java.util.Arrays;
import java.util.Scanner;

class Sorting {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int[] array = new int[in.nextInt()];
            for (int i = 0; i < array.length; i++) {
                array[i] = in.nextInt();
            }

            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(array[i]);
            }
        }
    }

}
