package week1.java;

import java.util.Scanner;

class PartialSumFibonacci {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(partialSumFib(in.nextLong(), in.nextLong()));
        }
    }

    private static long partialSumFib(final long start, final long end) {
        final long prefix = sumFib(start-1), suffix = sumFib(end);
        return (suffix < prefix) ? (suffix + 10) - prefix : suffix - prefix;
    }

    private static long sumFib(final long n) {
        final int[][] startMatrix = {{1, 1, 0}, {1, 0, 0}, {1, 0, 1}};
        final int[][] resultMatrix = pow(startMatrix, n+1);
        return resultMatrix[2][1];
    }

    private static int[][] pow(final int[][] matrix, final long n) {
        if (n <= 1) {
            return matrix;
        } else if (n == 2) {
            return square(matrix);
        } else if ((n % 2) == 0) {
            return pow(square(matrix), n/2);
        } else {
            return mult(matrix, pow(square(matrix), (n-1)/2));
        }
    }

    private static int[][] square(final int[][] matrix) {
        return mult(matrix, matrix);
    }

    private static int[][] mult(final int[][] a, final int[][] b) {
        return new int[][] {
            { m(m(a[0][0]*b[0][0])+m(a[0][1]*b[1][0])+m(a[0][2]*b[2][0])), m(m(a[0][0]*b[0][1])+m(a[0][1]*b[1][1])+m(a[0][2]*b[2][1])), m(m(a[0][0]*b[0][2])+m(a[0][1]*b[1][2])+m(a[0][2]*b[2][2])) },
            { m(m(a[1][0]*b[0][0])+m(a[1][1]*b[1][0])+m(a[1][2]*b[2][0])), m(m(a[1][0]*b[0][1])+m(a[1][1]*b[1][1])+m(a[1][2]*b[2][1])), m(m(a[1][0]*b[0][2])+m(a[1][1]*b[1][2])+m(a[1][2]*b[2][2])) },
            { m(m(a[2][0]*b[0][0])+m(a[2][1]*b[1][0])+m(a[2][2]*b[2][0])), m(m(a[2][0]*b[0][1])+m(a[2][1]*b[1][1])+m(a[2][2]*b[2][1])), m(m(a[2][0]*b[0][2])+m(a[2][1]*b[1][2])+m(a[2][2]*b[2][2])) }
        };
    }

    private static int m(final int n) {
        return (n % 10);
    }
}
