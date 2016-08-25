package week3;
import java.math.BigInteger;
import java.util.Scanner;

class RabinKarp {

    private static final long SECOND_HASH_HIGH_PRIME = 999119999L;
    private static final long SECOND_HASH_LOW_PRIME = 179L;

    private static final long FIRST_HASH_HIGH_PRIME = 1000000007L;
    private static final long FIRST_HASH_LOW_PRIME = 263L;

    static class Element implements Comparable<Element> {
        final long fh, sh;
        final int index;

        Element(final int index, final long fh, final long sh) {
            this.fh = fh;
            this.sh = sh;
            this.index = index;
        }

        public int compareTo(final Element that) {
            return (this.fh == that.fh) ?
                            Long.compare(this.sh, that.sh) :
                            Long.compare(this.fh, that.fh);
        }

        public String toString() {
            return this.index + " -> " + "(" + this.fh + ", " + this.sh + ")";
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String pattern = in.next(), text = in.next();

            final long[][] hashes = precomputeHashes(text, pattern);
            final long[] hash = calculateHash(pattern);

            boolean needsSpace = false;
            for (int i = 0; i < hashes.length; i++) {
                if ((hashes[i][0] == hash[0]) && (hashes[i][1] == hash[1])) {
                    if (needsSpace) {
                        System.out.print(" ");
                    }

                    System.out.print(i);
                    needsSpace = true;
                }
            }

            System.out.println();
        }
    }

    private static long[][] precomputeHashes(final String text, final String pattern) {
        final int tl = text.length(), pl = pattern.length();
        final long[][] result = new long[tl -pl + 1][];
        result[tl - pl] = calculateHash(text.substring(tl - pl));

        long y1 = 1, y2 = 1;
        for (int i = 1; i <= pl; i++) {
            y1 = getMod(y1 * FIRST_HASH_LOW_PRIME, 1);
            y2 = getMod(y2 * SECOND_HASH_LOW_PRIME, 2);
        }

        for (int i = tl - pl - 1; i >= 0; i--) {
            result[i] = new long[] {
                            getMod(FIRST_HASH_LOW_PRIME*result[i+1][0] + text.charAt(i) - y1*text.charAt(i + pl), 1),
                            getMod(SECOND_HASH_LOW_PRIME*result[i+1][1] + text.charAt(i) - y2*text.charAt(i + pl), 2)
            };
        }

        return result;
    }

    private static long getMod(final long a, final int index) {
        final long prime = (index == 1) ? FIRST_HASH_HIGH_PRIME : SECOND_HASH_HIGH_PRIME; 
        return ((a % prime) + prime) % prime;
    }

    private static long[] calculateHash(final String text) {
        return new long[] {
            calculateHash(
                    text, 
                    BigInteger.valueOf(FIRST_HASH_HIGH_PRIME), 
                    BigInteger.valueOf(FIRST_HASH_LOW_PRIME)), 
            calculateHash(
                    text, 
                    BigInteger.valueOf(SECOND_HASH_HIGH_PRIME), 
                    BigInteger.valueOf(SECOND_HASH_LOW_PRIME))
        };
    }

    private static long calculateHash(final String text, final BigInteger p, final BigInteger x) {
        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < text.length(); i++) {
            final BigInteger s = BigInteger.valueOf((int) text.charAt(i));
            total = total.add(s.multiply(x.modPow(BigInteger.valueOf(i), p)).mod(p));
        }

        return total.mod(p).longValue();
    }
}