package week4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

class CheckAnomaly {

    static class Edge {
        private final int u, v, w;
        Edge(final int u, final int v, final int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            final int vc = in.nextInt(), e = in.nextInt();
            final List<Edge> graph = new ArrayList<>();

            for (int i = 0; i < e; i++) {
                graph.add(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
            }

            boolean hasAnomaly = false;
            final BitSet toVisit = new BitSet(vc + 1);
            toVisit.set(1, vc);
            while ((!hasAnomaly) && (!toVisit.isEmpty())) {
                final int[] distances = new int[vc + 1];
                Arrays.fill(distances, 10000000);

                final int u = toVisit.nextSetBit(0);
                distances[u] = 0;
                toVisit.clear(u);

                for (int i = 1; i < vc; i++) {
                    for (final Edge edge : graph) {
                        if ((distances[edge.u] + edge.w) < distances[edge.v]) {
                            distances[edge.v] = distances[edge.u] + edge.w;
                        }
                    }
                }

                for (final Edge edge : graph) {
                    if ((distances[edge.u] + edge.w) < distances[edge.v]) {
                        hasAnomaly = true;
                        break;
                    } else if ((distances[edge.u] < 10000000) && (toVisit.get(edge.u))) {
                        toVisit.clear(edge.u);
                    } else if ((distances[edge.v] < 10000000) && (toVisit.get(edge.v))) {
                        toVisit.clear(edge.v);
                    }
                }
            }

            System.out.println((hasAnomaly) ? 1 : 0);
        }
    }
}
