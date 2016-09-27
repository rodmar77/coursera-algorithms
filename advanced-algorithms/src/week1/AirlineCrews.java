package week1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AirlineCrews {

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

        public FlowGraph(final int n) {
            this.graph = new ArrayList[n];
            for (int i = 0; i < n; ++i) {
                this.graph[i] = new ArrayList<>();
            }

            this.edges = new ArrayList<>();
        }

        void findDfsPath(final List<Integer> path, final List<Integer> edgeIndices) {
            visitedVertices = new boolean[this.size()];
            path.clear();
            edgeIndices.clear();
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

    public static void main(String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            boolean[][] bipartiteGraph = readData(in);
            int[] matching = findMatching(bipartiteGraph);
            writeResponse(matching);
        }
    }

    private static boolean[][] readData(final Scanner in) {
        final int numLeft = in.nextInt(), numRight = in.nextInt();
        final boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i) {
            for (int j = 0; j < numRight; ++j) {
                adjMatrix[i][j] = (in.nextInt() == 1);
            }
        }

        return adjMatrix;
    }

    private static int[] findMatching(final boolean[][] adjMatrix) {
        final int numLeft = adjMatrix.length, numRight = adjMatrix[0].length;
        final int[] matching = new int[numLeft];
        Arrays.fill(matching, -1);

        final FlowGraph graph = new FlowGraph(2 + numLeft + numRight);
        for (int i = 0; i < numLeft; i++) {
            graph.addEdge(0, 1 + i, 1);
        }

        for (int i = 0; i < numLeft; i++) {
            for (int j = 0; j < numRight; j++) {
                if (adjMatrix[i][j]) {
                    graph.addEdge(1 + i, 1 + numLeft + j, 1);
                }
            }
        }

        int sinkIndex = 1 + numLeft + numRight;
        for (int i = 0; i < numRight; i++) {
            graph.addEdge(1 + numLeft + i, sinkIndex, 1);
        }

        final List<Integer> path = new ArrayList<>(), edges = new ArrayList<>();
        while(true) {
            graph.findDfsPath(path, edges);
            if (path.isEmpty()) {
                break;
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
        }

        for (int i = 0; i < numLeft; i++) {
            graph.addEdge(0, 1 + i, 1);
            final List<Integer> edgeIds = graph.getIds(1 + i);
            for (final int eid : edgeIds) {
                final Edge edge = graph.getEdge(eid);
                if ((edge.flow == 1) && (edge.capacity == 1)) {
                    matching[i] = edge.to - numLeft - 1;
                }
            }
        }

        return matching;
    }

    private static void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                System.out.print(" ");
            }

            if (matching[i] == -1) {
                System.out.print("-1");
            } else {
                System.out.print(matching[i] + 1);
            }
        }

        System.out.println();
    }
}