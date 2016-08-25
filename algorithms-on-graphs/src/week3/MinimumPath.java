package week3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class MinimumPath {

    static class Vertex {
        private final int id;

        private int level;
        private final Set<Vertex> outgoing;

        Vertex(final int id) {
            this.id = id;
            this.level = -1;
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
    }

    public static void main(final String[] args) throws Exception {
        try (final Scanner in = new Scanner(System.in)) {
            in.nextInt(); 

            final int e = in.nextInt();
            final Map<Integer, Vertex> graph = new HashMap<>();
            for (int i = 0; i < e; i++) {    
                graph
                    .computeIfAbsent(in.nextInt(), k -> new Vertex(k))
                    .connectTo(
                        graph
                            .computeIfAbsent(in.nextInt(), k -> new Vertex(k)));
            }

            final int u = in.nextInt(), v = in.nextInt();
            if ((graph.containsKey(u)) && (graph.containsKey(v))) {
                final Vertex source = graph.get(u), target = graph.get(v);
                source.level = 0;

                int currentLevel = 0;
                Set<Vertex> toVisit = source.outgoing;
                while ((!toVisit.isEmpty()) && (!toVisit.contains(target))) {
                    Set<Vertex> nextLevel = new HashSet<>();
                    for (final Vertex next : toVisit) {
                        next.level = currentLevel + 1;
                        for (final Vertex toAdd : next.outgoing) {
                            if (toAdd.level < 0) {
                                nextLevel.add(toAdd);
                            }
                        }
                    }

                    toVisit = nextLevel;
                    currentLevel++;
                }

                System.out.println((toVisit.contains(target)) ? (currentLevel + 1) : -1);
            } else {
                System.out.println(-1);
            }
        }
    }
}
