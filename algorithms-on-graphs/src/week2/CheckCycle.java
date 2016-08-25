package week2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class CheckCycle {

    static class Vertex {
        private final int id;
        private final Set<Vertex> incoming;
        private final Set<Vertex> outgoing;

        Vertex(final int id) {
            this.id = id;
            this.incoming = new HashSet<>();
            this.outgoing = new HashSet<>();
        }

        void connectTo(final Vertex that) {
            this.outgoing.add(that);
            that.incoming.add(this);
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
            final int vc = in.nextInt(), e = in.nextInt();
            final Map<Integer, Vertex> graph = new HashMap<>();
            for (int i = 1; i <= vc; i++) {
                graph.put(i, new Vertex(i));
            }

            for (int i = 0; i < e; i++) {    
                graph.get(in.nextInt()).connectTo(graph.get(in.nextInt()));
            }

            final Queue<Vertex> start = new LinkedList<>();
            for (final Vertex v : graph.values()) {
                if (v.incoming.isEmpty()) {
                    start.offer(v);
                }
            }

            final List<Integer> sort = new ArrayList<>();

            while (!start.isEmpty()) {                // while S is non-empty do
                final Vertex n = start.poll();        //   remove a node n from S
                sort.add(n.id);                       //   add n to tail of L
                for (final Vertex m : n.outgoing) {   //   for each node m with an edge e from n to m do
                    m.incoming.remove(n);             //     remove edge e from the graph
                    if (m.incoming.isEmpty()) {       //     if m has no other incoming edges then
                        start.offer(m);               //       insert m into S
                    }
                }
            }

            System.out.println((sort.size() == vc) ? 0 : 1);
        }
    }
}
