package week1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class ConstructSuffixTree {

    static class Node {
        private final Node[] next;
        private int start, end, depth;

        Node(final int depth, final int start) {
            this(depth, start, start + 1);
        }

        Node(final int depth, final int start, final int end) {
            this.next = new Node[6];

            this.depth = depth;
            this.start = start;
            this.end = end;
        }

        int getDepth() {
            return depth;
        }

        boolean contains(final char c) {
            return (next[indexOf(c)] != null);
        }

        Node get(final char c) {
            return next[indexOf(c)];
        }

        void put(final char c, final Node child) {
            next[indexOf(c)] = child;
        }

        void putAll(final Node[] nodes) {
            for (int i = 0; i < 5; i++) {
                this.next[i] = nodes[i];
            }
        }

        void clear() {
            for (int i = 0; i < 5; i++) {
                this.next[i] = null;
            }
        }

        int indexOf(final char c) {
            switch (c) {
                case 'A': return 0;
                case 'C': return 1;
                case 'T': return 2;
                case 'G': return 3;
                default: return 4;
            }
        }

        List<Node> values() {
            return Arrays.asList(this.next);
        }

        boolean isEmpty() {
            for (int i = 0; i < 5; i++) {
                if (next[i] != null) {
                    return false;
                }
            }

            return true;
        }

        public String toString() {
            return this.start + "->" + this.end;
        }
    }

    static class SuffixTree {
        private final char[] text;
        private final Node root;

        SuffixTree(final char[] text) {
            this.root = new Node(-1, -1, -1);
            this.text = text;

            createTree();
        }

        private void createTree() {
            for (int i = text.length - 1; i >= 0; i--) {
                add(root, i);
            }
        }

        void add(final Node root, final int arrayIndex) {
            if (root.contains(text[arrayIndex])) {
                final Node changing = root.get(text[arrayIndex]);

                int cut = -1;
                for (int inc = 1; ((cut < 0) && (changing.start + inc < changing.end)); inc++) {
                    if (text[arrayIndex + inc] != text[changing.start + inc]) {
                        cut = inc;
                    }
                }

                if (cut > 0) {
                    final Node descendingNode = new Node(changing.depth + 1, changing.start + cut, changing.end);
                    descendingNode.putAll(changing.next);
                    changing.clear();
                    changing.put(text[descendingNode.start], descendingNode);

                    final Node newNode = new Node(changing.depth + 1, arrayIndex + cut, text.length);
                    changing.put(text[newNode.start], newNode);

                    changing.end = changing.start + cut;
                } else if (changing.end < text.length) {
                    add(changing, arrayIndex + (changing.end - changing.start));
                }
            } else {
                root.put(text[arrayIndex], new Node(root.depth + 1, arrayIndex, text.length));
            }
        }

        List<String> getSuffixes() {
            final Queue<Node> q = new LinkedList<>(root.values());
            final List<String> result = new ArrayList<>();
            while (!q.isEmpty()) {
                final Node n = q.poll();
                if (n != null) {
                    result.add(new String(text, n.start, n.end - n.start));
                    q.addAll(n.values());
                }
            }

            return result;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final SuffixTree tree = new SuffixTree(in.next().toCharArray());
            for (final String suffix : tree.getSuffixes()) {
                System.out.println(suffix);
            }
        }
    }
}
