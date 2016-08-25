package week1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

class ConstructSuffixTreeNaive {

    static class Node {
        private int start, end;
        private Map<Character, Node> next;

        Node(final int start) {
            this(start, start + 1);
        }

        Node(final int start, final int end) {
            this.next = new HashMap<>();
            this.start = start;
            this.end = end;
        }

        void compact() {
            if (!next.isEmpty()) {
                while (next.size() == 1) {
                    final Node nextNode = next.values().iterator().next();
                    this.end = nextNode.end;
                    this.next = nextNode.next;
                }

                if (next.size() > 1) {
                    for (final Node nextNode : next.values()) {
                        nextNode.compact();
                    }
                }
            }
        }

        public String toString() {
            return this.start + "->" + this.end;
        }
    }

    static class SuffixTree {
        private final char[] text;
        private final Node root;

        SuffixTree(final String text) {
            this.root = new Node(0, 0);
            this.text = text.toCharArray();

            createTree();
        }

        private void createTree() {
            for (int i = 0; i < text.length; i++) {
                add(root, i);
            }

            this.root.compact();
        }

        void add(Node current, final int arrayIndex) {
            for (int i = arrayIndex; i < text.length; i++) {
                final int index = i;
                current = current.next.computeIfAbsent(text[index], h -> new Node(index));
            }
        }

        List<String> print() {
            final Queue<Node> q = new LinkedList<>(root.next.values());
            final List<String> result = new ArrayList<>();
            while (!q.isEmpty()) {
                final Node n = q.poll();
                result.add(new String(text, n.start, n.end - n.start));
                q.addAll(n.next.values());
            }

            Collections.sort(result);
            return result;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final SuffixTree tree = new SuffixTree(in.next());
            for (final String suffix : tree.print()) {
                System.out.println(suffix);
            }
        }
    }
}
