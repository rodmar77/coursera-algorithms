package week3;

import java.util.Arrays;
import java.util.Scanner;

class ConstructLargeSuffixArray {

    static class DensePositiveMapper  {
        private final int offset;
        private final int[] forward;

        DensePositiveMapper(int [] input, int start, int length) {
            final int[] minmax = minmax(input, start, length);
            final int min = minmax[0];
            final int max = minmax[1];

            this.forward = new int[max - min + 1];
            final int offset = -min;

            final int end = start + length;
            for (int i = start; i < end; i++) {
                forward[input[i] + offset] = 1;
            }

            int k = 1;
            for (int i = 0; i < forward.length; i++) {
                if (forward[i] != 0) {
                    forward[i] = k++;
                }
            }

            this.offset = offset;
        }

        public void map(int[] input, final int start, final int length) {
            for (int i = start, l = length; l > 0; l--, i++) {
                input[i] = forward[input[i] + offset];
            }
        }
    }

    private final static boolean leq(int a1, int a2, int b1, int b2) {
        return (a1 < b1 || (a1 == b1 && a2 <= b2));
    }

    private final static boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return (a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3)));
    }

    private final static void radixPass(
                                    final int[] src, 
                                    final int[] dst, 
                                    final int[] v, 
                                    final int vi, 
                                    final int n, 
                                    final int K, 
                                    final int start,
                                    final int[] cnt) {

        Arrays.fill(cnt, 0, K + 1, 0);
        for (int i = 0; i < n; i++) {
            cnt[v[start + vi + src[i]]]++;
        }

        for (int i = 0, sum = 0; i <= K; i++) {
            final int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }

        for (int i = 0; i < n; i++) {
            dst[cnt[v[start + vi + src[i]]]++] = src[i];
        }
    }

    private static final int[] suffixArray(
                                    final int[] s, 
                                    final int[] SA, 
                                    final int n, 
                                    final int K, 
                                    final int start, 
                                    int[] cnt) {

        final int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
        final int[] s12 = new int[n02 + 3];
        final int[] SA12 = new int[n02 + 3];
        final int[] s0 = new int[n0];
        final int[] SA0 = new int[n0];

        for (int i = 0, j = 0; i < n + (n0 - n1); i++) {
            if ((i % 3) != 0) {
                s12[j++] = i;
            }
        }

        cnt = ensureSize(cnt, K + 1);
        radixPass(s12, SA12, s, +2, n02, K, start, cnt);
        radixPass(SA12, s12, s, +1, n02, K, start, cnt);
        radixPass(s12, SA12, s, +0, n02, K, start, cnt);

        int name = 0, c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; i++) {
            if (s[start + SA12[i]] != c0 || s[start + SA12[i] + 1] != c1 || s[start + SA12[i] + 2] != c2) {
                name++;
                c0 = s[start + SA12[i]];
                c1 = s[start + SA12[i] + 1];
                c2 = s[start + SA12[i] + 2];
            }

            if ((SA12[i] % 3) == 1) {
                s12[SA12[i] / 3] = name;
            } else {
                s12[SA12[i] / 3 + n0] = name;
            }
        }

        if (name < n02) {
            cnt = suffixArray(s12, SA12, n02, name, start, cnt);
            for (int i = 0; i < n02; i++) {
                s12[SA12[i]] = i + 1;
            }
        } else {
            for (int i = 0; i < n02; i++) {
                SA12[s12[i] - 1] = i;
            }
        }

        for (int i = 0, j = 0; i < n02; i++) {
            if (SA12[i] < n0) {
                s0[j++] = 3 * SA12[i];
            }
        }

        radixPass(s0, SA0, s, 0, n0, K, start, cnt);
        for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
            final int i = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
            final int j = SA0[p];

            if (SA12[t] < n0 ? 
                      leq(s[start + i], s12[SA12[t] + n0], s[start + j], s12[j / 3])
                    : leq(s[start + i], s[start + i + 1], s12[SA12[t] - n0 + 1], s[start + j], s[start + j + 1], s12[j / 3 + n0])) {

                SA[k] = i;
                t++;
                if (t == n02) {
                    for (k++; p < n0; p++, k++) {
                        SA[k] = SA0[p];
                    }
                }
            } else {
                SA[k] = j;
                p++;
                if (p == n0) {
                    for (k++; t < n02; t++, k++) {
                        SA[k] = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
                    }
                }
            }
        }

        return cnt;
    }

    private static final int[] ensureSize(final int[] tab, final int length) {
        if (tab.length < length) {
            return new int[length];
        }

        return tab;
    }

    private static int [] buildSuffixArray(final CharSequence sequence) {
        final int[] input = new int [sequence.length() + 575];
        for (int i = sequence.length() - 1; i >= 0; i--) {
            input[i] = sequence.charAt(i);
        }

        final int start = 0;
        final int length = sequence.length();

        final DensePositiveMapper mapper = new DensePositiveMapper(input, start, length);
        mapper.map(input, start, length);

        return buildSuffixArray(input, start, length);
}

    private static int[] buildSuffixArray(final int[] input, final int start, final int length) {
        final int alphabetSize = minmax(input, start, length)[1];
        final int[] result = new int[length + 3];

        final int[] tail = new int[3];
        System.arraycopy(input, start + length, tail, 0, 3);
        Arrays.fill(input, start + length, start + length + 3, 0);

        suffixArray(input, result, length, alphabetSize, start, new int[alphabetSize + 2]);
        System.arraycopy(tail, 0, input, start + length, 3);

        return result;
    }

    private static int[] minmax(final int [] input, final int start, final int length) {
        int max = input[start];
        int min = max;
        for (int i = length - 2, index = start + 1; i >= 0; i--, index++) {
            final int v = input[index];
            if (v > max) {
                max = v;
            }
            if (v < min) {
                min = v;
            }
        }

        return new int[] { min, max };
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next();
            final int[] result = buildSuffixArray(text);
            for (int i = 0; i < text.length(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(result[i]);
            }

            System.out.println();
        }
    }
}