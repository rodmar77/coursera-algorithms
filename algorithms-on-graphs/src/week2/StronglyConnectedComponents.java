package week2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class StronglyConnectedComponents {

    static class Vertex {
        private final int id;
        private int index, lowlink;
        private boolean onstack;

        private final Set<Vertex> incoming;
        private final Set<Vertex> outgoing;

        Vertex(final int id) {
            this.id = id;
            this.index = -1;

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

            final Deque<Vertex> stack = new ArrayDeque<>();
            final AtomicInteger index = new AtomicInteger(0);
            final Set<Set<Vertex>> groups = new HashSet<>();
            for (final Vertex v : graph.values()) {
                if (v.index == -1) {
                    strongConnect(v, stack, index, groups);
                }
            }

            System.out.println(groups.size());
        }
    }

    private static void strongConnect(
                                final Vertex v, 
                                final Deque<Vertex> stack, 
                                final AtomicInteger index, 
                                final Set<Set<Vertex>> groups) {

        v.index = index.get();
        v.lowlink = index.getAndIncrement();

        stack.push(v);
        v.onstack = true;

        for (final Vertex w : v.outgoing) {
            if (w.index == -1) {
                strongConnect(w, stack, index, groups);
                v.lowlink = Integer.min(v.lowlink, w.lowlink);
            } else if (w.onstack) {
                v.lowlink = Integer.min(v.lowlink, w.index);
            }
        }

        if (v.lowlink == v.index) {
            final Set<Vertex> group = new HashSet<>();
            Vertex w;
            do {
                w = stack.pop();
                w.onstack = false;
                group.add(w);
            } while (w.id != v.id);

            groups.add(group);
        }
    }
}
