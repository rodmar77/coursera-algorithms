package week1.java;

import java.util.Scanner;

class SumFibonacci {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(sumFib(in.nextLong()));
        }
    }

    private static long sumFib(final long n) {
        final long[][] startMatrix = {{1, 1, 0}, {1, 0, 0}, {1, 0, 1}};
        final long[][] resultMatrix = pow(startMatrix, n+1);
        return resultMatrix[2][1];
    }

    private static long[][] pow(final long[][] matrix, final long n) {
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

    private static long[][] square(final long[][] matrix) {
        return mult(matrix, matrix);
    }

    private static long[][] mult(final long[][] a, final long[][] b) {
        return new long[][] {
            { m(m(a[0][0]*b[0][0])+m(a[0][1]*b[1][0])+m(a[0][2]*b[2][0])), m(m(a[0][0]*b[0][1])+m(a[0][1]*b[1][1])+m(a[0][2]*b[2][1])), m(m(a[0][0]*b[0][2])+m(a[0][1]*b[1][2])+m(a[0][2]*b[2][2])) },
            { m(m(a[1][0]*b[0][0])+m(a[1][1]*b[1][0])+m(a[1][2]*b[2][0])), m(m(a[1][0]*b[0][1])+m(a[1][1]*b[1][1])+m(a[1][2]*b[2][1])), m(m(a[1][0]*b[0][2])+m(a[1][1]*b[1][2])+m(a[1][2]*b[2][2])) },
            { m(m(a[2][0]*b[0][0])+m(a[2][1]*b[1][0])+m(a[2][2]*b[2][0])), m(m(a[2][0]*b[0][1])+m(a[2][1]*b[1][1])+m(a[2][2]*b[2][1])), m(m(a[2][0]*b[0][2])+m(a[2][1]*b[1][2])+m(a[2][2]*b[2][2])) }
        };
    }

    private static long m(final long n) {
        return (n % 10);
    }
}
