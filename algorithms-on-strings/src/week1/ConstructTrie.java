package week1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class ConstructTrie {

    static class Node {
        private final int index;
        private final char value;
        private final Map<Character, Node> next;

        Node(final int index, final char value) {
            this.index = index;
            this.value = value;
            this.next = new HashMap<>();
        }
    }

    static class Trie {
        private final AtomicInteger index;
        private final Node root;

        Trie() {
            this.index = new AtomicInteger(0);
            this.root = new Node(index.getAndIncrement(), '0');
        }

        void add(final String text) {
            add(root, 0, text.toCharArray());
        }

        void add(final Node root, final int idx, final char[] text) {
            if (idx < text.length) {
                final Node next = root.next.computeIfAbsent(text[idx], h -> new Node(index.getAndIncrement(), h));
                add(next, idx + 1, text);
            }
        }

        void print() {
            for (final Node node : root.next.values()) {
                print(node, 0);
            }
        }

        private void print(final Node node, final int index) {
            System.out.println(index + "->" + node.index + ":" + node.value);
            for (final Node next : node.next.values()) {
                print(next, node.index);
            }
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int count = in.nextInt();

            final Trie trie = new Trie();
            for (int i = 0; i < count; i++) {
                trie.add(in.next());
            }

            trie.print();
        }
    }
}
