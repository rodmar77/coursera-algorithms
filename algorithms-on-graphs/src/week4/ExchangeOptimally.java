package week4;
import static java.lang.Double.isFinite;
import static java.lang.Double.isInfinite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class ExchangeOptimally {

    static class Edge {
        private final Vertex u, v;
        private final int w;
        Edge(final Vertex u, final Vertex v, final int w) {
            this.u = u;
            this.v = v;
            this.w = w;

            u.connectTo(v);
        }
    }

    static class Vertex {
        private final Set<Vertex> outgoing;
        private final int id;

        Vertex(final int id) {
            this.id = id;
            this.outgoing = new HashSet<>();
        }

        public void connectTo(final Vertex that) {
            this.outgoing.add(that);
        }

        public boolean equals(final Object other) {
            if (!(other instanceof Vertex)) {
                return false;
            }

            return (this.id == (((Vertex) other).id));
        }

        public int hashCode() {
            return this.id;
        }

        public String toString() {
            return String.valueOf(this.id);
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            final int vc = in.nextInt(), e = in.nextInt();

            final Map<Integer, Vertex> graph = new HashMap<>();
            final List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < e; i++) {
                edges.add(
                        new Edge(
                            graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k)),
                            graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k)),
                            in.nextInt()));
            }

            final int u = in.nextInt();

            final double[] distances = new double[vc + 1];
            Arrays.fill(distances, Double.POSITIVE_INFINITY);
            distances[u] = 0;

            for (int i = 1; i < vc; i++) {
                for (final Edge edge : edges) {
                    if ((isFinite(distances[edge.u.id])) 
                            && ((isInfinite(distances[edge.v.id])) || (distances[edge.u.id] + edge.w < distances[edge.v.id]))) {

                        distances[edge.v.id] = distances[edge.u.id] + edge.w;
                    }
                }
            }

            final Set<Vertex> relaxed = new HashSet<>();
            for (final Edge edge : edges) {
                if ((isFinite(distances[edge.u.id])) 
                        && ((isInfinite(distances[edge.v.id])) || (distances[edge.u.id] + edge.w < distances[edge.v.id]))) {

                    distances[edge.v.id] = distances[edge.u.id] + edge.w;
                    relaxed.add(edge.v);
                }
            }

            final Queue<Vertex> q = new LinkedList<>(relaxed);
            while (!q.isEmpty()) {
                final Vertex v = q.poll();
                distances[v.id] = Double.NEGATIVE_INFINITY;
                for (final Vertex n : v.outgoing) {
                    if ((!q.contains(n)) && (distances[n.id] != Double.NEGATIVE_INFINITY)) {
                        q.offer(n);
                    }
                }
            }

            for (int i = 1; i < distances.length; i++) {
                if (distances[i] == Double.POSITIVE_INFINITY) {
                    System.out.println("*");
                } else if (distances[i] == Double.NEGATIVE_INFINITY) {
                    System.out.println("-");
                } else {
                    System.out.println((long) distances[i]);
                }
            }
        }
    }
}
