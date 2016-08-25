package week5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class KruskalTree {

    static class Tree {
        private double weight;
        private List<Vertex> nodes;

        Tree(final Vertex root) {
            this.nodes = new ArrayList<>();
            this.nodes.add(root);
            this.weight = 0;
        }

        void add(final Vertex v) {
            this.add(v, minDistance(v));
        }

        public void add(final Vertex v, final double distance) {
            this.weight += distance;
            nodes.add(v);
        }

        double minDistance(final Vertex v) {
            double min = distance(v, nodes.get(0));
            for (int i = 1; i < nodes.size(); i++) {
                min = Math.min(min, distance(v, nodes.get(i)));
            }

            return min;
        }

        double distance(final Vertex u, final Vertex v) {
            return Math.sqrt((u.x - v.x)*(u.x - v.x) + (u.y - v.y)*(u.y - v.y));
        }
    }

    static class Vertex {
        private final int x, y, hashCode;

        Vertex(final int x, final int y) {
            this.x = x;
            this.y = y;
            this.hashCode = (x + ":" + y).hashCode();
        }

        public boolean equals(final Object other) {
            if (!(other instanceof Vertex)) {
                return false;
            }

            final Vertex that = (Vertex) other;
            return ((this.x == that.x) && (this.y == that.y));
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return "[" + this.x + ", " + this.y + "]";
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            final int vc = in.nextInt();
            final List<Vertex> graph = new ArrayList<>();
            for (int i = 1; i <= vc; i++) {
                graph.add(new Vertex(in.nextInt(), in.nextInt()));
            }

            final Tree tree = new Tree(graph.remove(0));
            while (!graph.isEmpty()) {
                int mi = 0;
                double md = tree.minDistance(graph.get(0));
                for (int i = 1; i < graph.size(); i++) {
                    final double nd = tree.minDistance(graph.get(i));
                    if (nd < md) {
                        md = nd;
                        mi = i;
                    }
                }

                tree.add(graph.remove(mi), md);
            }

            System.out.println(tree.weight);
        }
    }
}
