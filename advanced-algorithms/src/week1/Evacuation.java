package week1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Evacuation {

    static class Edge {
        private final int from, to, capacity;
        private int flow;

        public Edge(final int from, final int to, final int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        public int getResidual() {
            return capacity - flow;
        }
    }

    static class FlowGraph {
        private List<Edge> edges;
        private List<Integer>[] graph;
        private boolean[] visitedVertices;

        public FlowGraph(int n) {
            this.graph = new ArrayList[n];
            for (int i = 0; i < n; ++i) {
                this.graph[i] = new ArrayList<>();
            }

            this.edges = new ArrayList<>();
        }

        void findDfsPath(final List<Integer> path, final List<Integer> edgeIndices) {
            visitedVertices = new boolean[this.size()];
            dfsPathHelper(0, path, edgeIndices);
        }

        boolean dfsPathHelper(final int vertex, final List<Integer> path, final List<Integer> edgeIndices) {
            visitedVertices[vertex] = true;
            if ((vertex < 0) || (vertex >= graph.length)) {
                return false;
            }

            if (vertex == (graph.length - 1)) {
                path.add(vertex);
                return true;
            }

            final List<Integer> ids = getIds(vertex);
            for (final int id : ids) {
                final Edge e = edges.get(id);
                int residual_flow = e.getResidual();
                if (residual_flow > 0) {
                    if (!visitedVertices[e.to]) {
                        if (dfsPathHelper(e.to, path, edgeIndices)) {
                            path.add(vertex);
                            edgeIndices.add(id);
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        void addEdge(final int from, final int to, final int capacity) {
            graph[from].add(edges.size());
            edges.add(new Edge(from, to, capacity));

            graph[to].add(edges.size());
            edges.add(new Edge(to, from, 0));
        }

        int size() {
            return graph.length;
        }

        List<Integer> getIds(final int from) {
            return graph[from];
        }

        Edge getEdge(final int id) {
            return edges.get(id);
        }

        void addFlow(final int id, final int flow) {
            /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
             * due to the described above scheme. On the other hand, when we have to get a "backward"
             * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
             * should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through! */
            edges.get(id).flow += flow;
            edges.get(id ^ 1).flow -= flow;
        }
    }

    public static void main(String[] args) throws IOException {
        try (final Scanner in = new Scanner(System.in)) {
            final FlowGraph graph = readGraph(in);
            System.out.println(maxFlow(graph, 0, graph.size() - 1));
        }
    }

    private static int maxFlow(final FlowGraph graph, final int from, final int to) {
        int flow = 0;
        while(true) {
            final List<Integer> path = new ArrayList<>(), edges = new ArrayList<>();

            graph.findDfsPath(path, edges);
            if (path.isEmpty()) {
                return flow;
            }

            int minCap = Integer.MAX_VALUE;
            for (final int eid : edges) {
                if (graph.getEdge(eid).getResidual() < minCap) {
                    minCap = graph.getEdge(eid).getResidual();
                }
            }

            for (final int e : edges) {
                graph.addFlow(e, minCap);
            }

            flow += minCap;
        }
    }

    static FlowGraph readGraph(final Scanner in) {
        final int vertexCount = in.nextInt(), edgeCount = in.nextInt();
        final FlowGraph graph = new FlowGraph(vertexCount);
        for (int i = 0; i < edgeCount; ++i) {
            graph.addEdge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
        }

        return graph;
    }
}