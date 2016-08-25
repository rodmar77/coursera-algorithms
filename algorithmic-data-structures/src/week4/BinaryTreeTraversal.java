package week4;
import java.util.Scanner;

class BinaryTreeTraversal {

    static class Node {
        private boolean visited;
        private Node left, right, parent;

        private int id;

        Node() {
            this(-1);
        }

        Node(final int id) {
            this.id = id;
        }

        void setParent(final Node parent) {
            this.parent = parent;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final Node[] tree = new Node[in.nextInt()];

            Node root = null;
            for (int i = 0; i < tree.length; i++) {
                final int id = in.nextInt();
                final Node node = (tree[i] == null) ? (tree[i] = new Node(id)) : tree[i];
                if (node.id == -1) {
                    node.id = id;
                }

                final int leftIndex = in.nextInt();
                node.left = (leftIndex < 0) ? null : ((tree[leftIndex] == null) ? (tree[leftIndex] = new Node()) : tree[leftIndex]);
                if (leftIndex > 0) {
                    node.left.parent = node;
                }

                final int rightIndex = in.nextInt();
                node.right = (rightIndex < 0) ? null : ((tree[rightIndex] == null) ? (tree[rightIndex] = new Node()) : tree[rightIndex]);
                if (rightIndex > 0)  {
                    node.right.parent = node;
                }

                if (i == 0) {
                    root = node;
                }
            }

            System.out.println(printVisitInOrder(root));
            for (final Node node : tree) node.visited = false;

            System.out.println(printVisitInPreOrder(root));
            for (final Node node : tree) node.visited = false;

            System.out.println(printVisitInPostOrder(root));
        }
    }

    private static String printVisitInPreOrder(final Node root) {
        final StringBuilder text = new StringBuilder();

        boolean canVisit = true;

        Node current = root;
        do {
            if (!current.visited) {
                visitNode(text, current);
            } else if ((current.left != null) && (!current.left.visited)) {
                current = current.left;
            } else if ((current.right != null) && (!current.right.visited)) {
                current = current.right;
            } else if (current.parent != null) {
                current = current.parent;
            } else {
                canVisit = false;
            }
        } while (canVisit);

        return text.toString();
    }

    private static String printVisitInOrder(final Node root) {
        final StringBuilder text = new StringBuilder();

        boolean canVisit = true;

        Node current = root;
        do {
            if ((current.left != null) && (!current.left.visited)) {
                current = current.left;
            } else if (!current.visited) {
                visitNode(text, current);
            } else if ((current.right != null) && (!current.right.visited)) {
                current = current.right;
            } else if (current.parent != null) {
                current = current.parent;
            } else {
                canVisit = false;
            }
        } while (canVisit);

        return text.toString();
    }

    private static String printVisitInPostOrder(final Node root) {
        final StringBuilder text = new StringBuilder();

        boolean canVisit = true;

        Node current = root;
        do {
            if ((current.left != null) && (!current.left.visited)) {
                current = current.left;
            } else if ((current.right != null) && (!current.right.visited)) {
                current = current.right;
            } else if (!current.visited) {
                visitNode(text, current);
            } else if (current.parent != null) {
                current = current.parent;
            } else {
                canVisit = false;
            }
        } while (canVisit);

        return text.toString();
    }

    private static void visitNode(final StringBuilder text, final Node current) {
        if (text.length() > 0) {
            text.append(" ");
        }

        text.append(current.id);
        current.visited = true;
    }
}
