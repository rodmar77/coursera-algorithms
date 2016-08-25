package week3.java;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class PointsSegments {

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            final int segmentCount = in.nextInt(), tests = in.nextInt();

            final int[][] segments = new int[2*segmentCount + tests][];
            for (int i = 0; i < 2*segmentCount; i += 2) {
                segments[i]     = new int[] { in.nextInt(), 1 };
                segments[i + 1] = new int[] { in.nextInt(), 3 };
            }

            final Map<Integer, List<Integer>> pointMap = new HashMap<>();
            for (int i = 2*segmentCount; i < segments.length; i++) {
                final int point = in.nextInt();
                segments[i] = new int[] { point, 2 };
                pointMap.computeIfAbsent(point, k -> new ArrayList<>()).add(i - 2*segmentCount);
            }

            Arrays.sort(segments, (a, b) -> (a[0] == b[0]) ? 
                                                    Integer.compare(a[1], b[1]) : 
                                                    Integer.compare(a[0], b[0]));

            final int[] count = new int[tests];
            int total = 0;
            for (final int[] segment : segments) {
                if (segment[1] == 1) {
                    total++;
                } else if (segment[1] == 3) {
                    total--;
                } else {
                    final List<Integer> indexes = pointMap.get(segment[0]);
                    for (final int index : indexes) {
                        count[index] = total;
                    }
                }
            }

            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tests; i++) {
                if (i > 0) {
                    sb.append(" ");
                }

                sb.append(count[i]);
            }

            System.out.println(sb.toString());
        }
    }
}