package week3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

class CheckBipartite {

    static class Vertex {
        private final int id;

        private int color;
        private final Set<Vertex> outgoing;

        Vertex(final int id) {
            this.id = id;
            this.color = -1;
            this.outgoing = new HashSet<>();
        }

        void connectTo(final Vertex that) {
            this.outgoing.add(that);
            that.outgoing.add(this);
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

        public String toString() {
            return "[" + this.id + "]";
        }
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            in.nextInt(); final int e = in.nextInt();
            final Map<Integer, Vertex> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {    
                graph
                    .computeIfAbsent(in.nextInt(), k -> new Vertex(k))
                    .connectTo(
                        graph
                            .computeIfAbsent(in.nextInt(), k -> new Vertex(k)));
            }

            boolean isBipartite = true;
            int currentColor = 0;
            final Deque<Vertex> stack = new ArrayDeque<>();
            while (isBipartite) {
                if (stack.isEmpty()) {
                    final Optional<Vertex> o = graph
                                                    .values()
                                                    .stream()
                                                    .limit(1)
                                                    .filter(n -> n.color < 0)
                                                    .findFirst();
                    if (o.isPresent()) {
                        final Vertex v = o.get();
                        v.color = currentColor;
                        currentColor = ((currentColor + 1) % 2);
                        stack.push(v);
                    } else {
                        break;
                    }
                } else {
                    final List<Vertex> nextVertices = new ArrayList<>();
                    while (!stack.isEmpty()) {
                        final Vertex current = stack.pop();
                        for (final Vertex next : current.outgoing) {
                            if (next.color < 0) {
                                next.color = currentColor;
                                nextVertices.add(next);
                            } else if (next.color != currentColor) {
                                isBipartite = false;
                                break;
                            }
                        }
                    }

                    currentColor = ((currentColor + 1) % 2);
                    stack.addAll(nextVertices);
                }
            }

            System.out.println((isBipartite) ? 1 : 0);
        }
    }
}
