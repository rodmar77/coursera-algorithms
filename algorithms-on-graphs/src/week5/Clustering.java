package week5;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Clustering {

    static class Edge {
        private final Vertex u, v;
        private final double weight;

        Edge(final Vertex u, final Vertex v) {
            this.u = u;
            this.v = v;
            this.weight = u.distanceTo(v);
        }
    }

    static class Tree {
        private List<Vertex> nodes;
        private List<Edge> edges;

        Tree(final Vertex root) {
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
            this.nodes.add(root);
        }

        void add(final Vertex v) {
            if (!nodes.contains(v)) {
                nodes.add(v);
            }
        }

        void add(final Edge edge) {
            edges.add(edge);
            add(edge.u);
            add(edge.v);

            edge.u.connections.add(edge.v);
        }

        Edge minDistance(final Vertex v) {
            double min = v.distanceTo(nodes.get(0));
            Vertex u = nodes.get(0);
            for (int i = 1; i < nodes.size(); i++) {
                final double distance = v.distanceTo(nodes.get(i));
                if (distance < min) {
                    min = distance;
                    u = nodes.get(i);
                }
            }

            return new Edge(u, v);
        }

        public Edge largestEdge() {
            if (this.edges.isEmpty()) {
                return null;
            }

            Edge result = edges.get(0);
            for (int i = 1; i < edges.size(); i++) {
                if (edges.get(i).weight > result.weight) {
                    result = edges.get(i);
                }
            }

            return result;
        }

        Tree splitAt(final Edge edge) {
            final Tree result = new Tree(edge.v);
            edge.u.connections.remove(edge.v);
            this.edges.remove(edge);

            final Queue<Vertex> q = new LinkedList<>();
            q.add(edge.v);
            while (!q.isEmpty()) {
                final Vertex v = q.poll();
                result.add(v);
                this.nodes.remove(v);

                final List<Edge> edges = removeAllEdges(v);
                result.edges.addAll(edges);

                q.addAll(v.connections);
            }

            return result;
        }

        List<Edge> removeAllEdges(final Vertex v) {
            final List<Edge> result = new ArrayList<>();
            for (final Iterator<Edge> it = edges.iterator(); it.hasNext(); ) {
                final Edge edge = it.next();
                if ((edge.u == v) || (edge.v == v)) {
                    result.add(edge);
                    it.remove();
                }
            }

            return result;
        }

        double distance(final Tree that) {
            double distance = Double.MAX_VALUE;
            for (final Vertex u : this.nodes) {
                for (final Vertex v : that.nodes) {
                    distance = Math.min(distance, u.distanceTo(v));
                }
            }

            return distance;
        }
    }

    static class Vertex {
        private final int x, y, hashCode;
        private final List<Vertex> connections;

        Vertex(final int x, final int y) {
            this.x = x;
            this.y = y;
            this.connections = new ArrayList<>();
            this.hashCode = (x + ":" + y).hashCode();
        }

        double distanceTo(final Vertex that) {
            return Math.sqrt((this.x - that.x)*(this.x - that.x) + (this.y - that.y)*(this.y - that.y));
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

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int vc = in.nextInt();
            final List<Vertex> graph = new ArrayList<>();
            for (int i = 1; i <= vc; i++) {
                graph.add(new Vertex(in.nextInt(), in.nextInt()));
            }

            final List<Tree> clusters = splitIntoClusters(graph, in.nextInt());
            System.out.println(getMinimumClusterDistance(clusters));
        }
    }

    private static double getMinimumClusterDistance(final List<Tree> clusters) {
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < clusters.size() - 1; i++) {
            final Tree first = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                final Tree second = clusters.get(j);
                minDistance = Math.min(minDistance, first.distance(second));
            }
        }
        return minDistance;
    }

    private static Tree generateMinimumSpanningTree(final List<Vertex> graph) {
        final Tree tree = new Tree(graph.remove(0));
        while (!graph.isEmpty()) {
            Edge md = tree.minDistance(graph.get(0));
            for (int i = 1; i < graph.size(); i++) {
                final Edge nd = tree.minDistance(graph.get(i));
                if (nd.weight < md.weight) {
                    md = nd;
                }
            }

            graph.remove(md.u);
            graph.remove(md.v);
            tree.add(md);
        }

        return tree;
    }

    private static List<Tree> splitIntoClusters(final List<Vertex> graph, final int clusterCount) {
        final Tree tree = generateMinimumSpanningTree(graph);
        final List<Tree> clusters = new ArrayList<>();
        clusters.add(tree);
        while (clusters.size() < clusterCount) {
            Tree treeToSplit = clusters.get(0);
            Edge largestEdge = treeToSplit.largestEdge();
            for (int i = 1; i < clusters.size(); i++) {
                final Tree nextTreeToSplit = clusters.get(i);
                final Edge nextLargestEdge = nextTreeToSplit.largestEdge();
                if ((largestEdge == null) || ((nextLargestEdge != null) && (nextLargestEdge.weight > largestEdge.weight))) {
                    largestEdge = nextLargestEdge;
                    treeToSplit = nextTreeToSplit;
                }
            }

            clusters.add(treeToSplit.splitAt(largestEdge));
        }

        return clusters;
    }
}
