package week1;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class AddExits {

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
            final int vc = in.nextInt(), e = in.nextInt();
            final Map<Integer, Vertex> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {
                final Vertex u = graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k));
                final Vertex v = graph.computeIfAbsent(in.nextInt(), k -> new Vertex(k));

                u.addNeighbour(v);
                v.addNeighbour(u);
            }

            final Deque<Vertex> stack = new ArrayDeque<>();
            int components = 0;
            for (int i = 1; i <= vc; i++) {
                if (!graph.containsKey(i)) {
                    components++;
                } else {
                    final Vertex u = graph.get(i);
                    if (!u.isVisited()) {
                        components++;
                        stack.add(u);
                        while (!stack.isEmpty()) {
                            final Vertex top = stack.pop();
                            top.visit();
                            for (final Vertex neighbour : top.neighbours) {
                                if (!neighbour.isVisited()) {
                                    stack.offer(neighbour);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(components);
        }
    }
}
