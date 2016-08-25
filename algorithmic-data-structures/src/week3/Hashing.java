package week3;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class Hashing {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final List<String>[] buckets = new ArrayList[in.nextInt()];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new ArrayList<>();
            }

            final int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                final String command = in.next();
                if ("add".equals(command)) {
                    final String text = in.next();
                    final List<String> bucket = getBucket(buckets, text);
                    if (!bucket.contains(text)) {
                        bucket.add(0, text);
                    }
                } else if ("check".equals(command)) {
                    final List<String> bucket = buckets[in.nextInt()];
                    for (int j = 0; j < bucket.size(); j++) {
                        if (j > 0) {
                            System.out.print(" ");
                        }

                        System.out.print(bucket.get(j));
                    }

                    System.out.println();
                } else if ("find".equals(command)) {
                    final String text = in.next();
                    final List<String> bucket = getBucket(buckets, text);
                    System.out.println(bucket.contains(text) ? "yes" : "no");
                } else if ("del".equals(command)) {
                    final String text = in.next();
                    final List<String> bucket = getBucket(buckets, text);
                    bucket.remove(text);
                }
            }
        }
    }

    private static List<String> getBucket(final List<String>[] buckets, final String text) {
        return buckets[calculateHash(text, buckets.length)];
    }

    private static final BigInteger p = new BigInteger("1000000007");
    private static final BigInteger x = new BigInteger("263");

    private static int calculateHash(final String text, final int buckets) {
        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < text.length(); i++) {
            final BigInteger s = BigInteger.valueOf((int) text.charAt(i));
            total = total.add(s.multiply(x.pow(i)));
        }

        return total.mod(p).intValue() % buckets;
    }
}
