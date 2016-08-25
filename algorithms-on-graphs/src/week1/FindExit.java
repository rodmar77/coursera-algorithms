package week1;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class FindExit {

    static class Vertex {
        private final int id;
        private final Set<Vertex> neighbours;

        private boolean visited = false;

        Vertex(final int id) {
            this.id = id;
            this.neighbours = new HashSet<>();
        }

        void addNeighbour(final Vertex that) {
            this.neighbours.add(that);
        }

        boolean isVisited() {
            return this.visited;
        }

        void visit() {
            this.visited = true;
        }

        public boolean equals(final Object other) {
            if (!(other instanceof Vertex)) {
                return false;
            }

            return (this.id == ((Vertex) other).id);
        }

        public int hashCode() {
            return this.id;
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            in.nextInt();

            final int e = in.nextInt();
            final Map<Integer, Vertex> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {
                final Vertex u = graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k));
                final Vertex v = graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k));

                u.addNeighbour(v);
                v.addNeighbour(u);
            }

            final int u = in.nextInt(), v = in.nextInt();
            final Deque<Vertex> stack = new ArrayDeque<>();

            boolean found = false;
            if ((graph.containsKey(u)) && (graph.containsKey(v))) {
                stack.add(graph.get(u));
                while ((!found) && (!stack.isEmpty())) {
                    final Vertex top = stack.pop();
                    top.visit();
                    if (top.id == v) {
                        found = true;
                    } else {
                        for (final Vertex neighbour : top.neighbours) {
                            if (!neighbour.isVisited()) {
                                stack.offer(neighbour);
                            }
                        }
                    }
                }
            }

            System.out.println((found) ? 1 : 0);
        }
    }
}
