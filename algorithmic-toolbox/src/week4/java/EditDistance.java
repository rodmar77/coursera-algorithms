package week4.java;
import java.util.Scanner;

class EditDistance {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            System.out.println(editDistance(in.next(), in.next()));
        }
    }

    private static int editDistance(final String a, final String b) {
        final int[][] distance = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < distance.length; i++) {
            distance[i][0] = i;
        }

        for (int j = 0; j < distance[0].length; j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i < distance.length; i++) {
            for (int j = 1; j < distance[i].length; j++) {
                final char ca = a.charAt(i - 1), cb = b.charAt(j - 1);
                final int dist = (ca == cb) ? 0 : 1;
                distance[i][j] = min(distance[i-1][j-1] + dist, distance[i-1][j] + 1, distance[i][j-1] + 1);
            }
        }

        return distance[a.length()][b.length()];
    }

    private static int min(final int i, final int j, final int k) {
        return ((i < j) && (i < k)) ? i : ((j < k) ? j : k);
    }
}
