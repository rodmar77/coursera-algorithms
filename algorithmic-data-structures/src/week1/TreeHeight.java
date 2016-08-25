package week1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TreeHeight {

    static class Node {
        private List<Node> children;

        Node() {
            this.children = new ArrayList<>();
        }

        void addChild(final Node node) {
            this.children.add(node);
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final Node[] tree = new Node[in.nextInt()];
            int rootIndex = -1;
            for (int i = 0; i < tree.length; i++) {
                final int parent = in.nextInt();
                if (parent == -1) {
                    if (tree[i] == null) {
                        tree[i] = new Node();
                    }

                    rootIndex = i;
                } else {
                    if (tree[parent] == null) {
                        tree[parent] = new Node();
                    }

                    tree[i] = (tree[i] == null) ? new Node() : tree[parent];
                    tree[parent].addChild(tree[i]);
                }
            }

            int height = 1;

            List<Node> toProcess = new ArrayList<>(tree[rootIndex].children);
            do {
                height++;

                final List<Node> next = new ArrayList<>();
                for (final Node child : toProcess) {
                    next.addAll(child.children);
                }

                toProcess = next;
            } while (!toProcess.isEmpty());

            System.out.println(height);
        }
    }
}
