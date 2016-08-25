package week4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class MinimumCost {

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

            final int u = in.nextInt(), v = in.nextInt();

            final int[] distances = new int[vc + 1];
            Arrays.fill(distances, 10000000);
            distances[u] = 0;

            for (int i = 1; i < vc; i++) {
                for (final Edge edge : graph) {
                    if ((distances[edge.u] + edge.w) < distances[edge.v]) {
                        distances[edge.v] = distances[edge.u] + edge.w;
                    }
                }
            }

            System.out.println((distances[v] >= 10000000) ? -1 : distances[v]);
        }
    }
}
