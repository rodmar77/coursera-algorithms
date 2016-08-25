package week1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ImplementTrie {

    static class Node {
        private final Map<Character, Node> next;

        Node() {
            this.next = new HashMap<>();
        }
    }

    static class Trie {
        private final Node root;

        Trie() {
            this.root = new Node();
        }

        void add(final String text) {
            add(root, 0, text.toCharArray());
        }

        void add(final Node root, final int idx, final char[] text) {
            if (idx == text.length) {
                root.next.put('$', null);
            } else {
                final Node next = root.next.computeIfAbsent(text[idx], h -> new Node());
                add(next, idx + 1, text);
            }
        }

        List<Integer> getIndexes(final String text) {
            final List<Integer> result = new ArrayList<>();
            final char[] array = text.toCharArray();
            for (int i = 0; i < array.length; i++) {
                if (hasMatch(array, i)) {
                    result.add(i);
                }
            }

            return result;
        }

        boolean hasMatch(final char[] array, final int index) {
            return hasMatch(array, root, index);
        }

        boolean hasMatch(final char[] array, final Node current, final int index) {
            if (current.next.containsKey('$')) {
                return true;
            } else if ((index == array.length) || (!current.next.containsKey(array[index]))) {
                return false;
            } else {
                return hasMatch(array, current.next.get(array[index]), index + 1);
            }
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next();

            final int patterns = in.nextInt();
            final Trie trie = new Trie();
            for (int i = 0; i < patterns; i++) {
                trie.add(in.next());
            }

            final List<Integer> indexes = trie.getIndexes(text);
            for (int i = 0; i < indexes.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(indexes.get(i));
            }

            System.out.println();
        }
    }
}
