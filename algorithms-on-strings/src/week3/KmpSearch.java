package week3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class KmpSearch {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final List<Integer> result = new KmpSearch(in.next()).search(in.next());
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(result.get(i));
            }

            System.out.println();
        }
    }

    private final int length;
    private final int[][] dfa;

    public KmpSearch(final String pattern) {
        this.length = pattern.length();

        this.dfa = new int[4][length + 1]; 
        this.dfa[indexOf(pattern.charAt(0))][0] = 1;
        for (int x = 0, j = 1; j <= length; j++) {
            for (int c = 0; c < 4; c++) { 
                dfa[c][j] = dfa[c][x];
            }

            if (j < length) {
                dfa[indexOf(pattern.charAt(j))][j] = j + 1; 
                x = dfa[indexOf(pattern.charAt(j))][x];
            }
        }
    } 

    public List<Integer> search(final String txt) {
        final List<Integer> result = new ArrayList<>();

        int n = txt.length();
        for (int i = 0, j = 0; i < n; i++) {
            j = dfa[indexOf(txt.charAt(i))][j];

            if (j == length) {
                result.add(i - length + 1);
            }
        }

        return result;
    }

    private int indexOf(final char c) {
        switch (c) {
            case 'A': return 0;
            case 'C': return 1;
            case 'T': return 2;
             default: return 3;
        }
    }
}