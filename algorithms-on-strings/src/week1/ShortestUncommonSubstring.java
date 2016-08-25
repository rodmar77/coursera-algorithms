package week1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class ShortestUncommonSubstring {

    static class Node {
        private final Node[] next;
        private Node parent;

        private final Set<Integer> words;
        private String text;

        Node() {
            this.parent = null;
            this.words = null;
            this.next = new Node[5];
        }

        Node(
           final Node parent,
           final int word,
           final String text) {

            this.parent = parent;
            this.next = new Node[5];

            this.text = text;

            this.words = new HashSet<>();
            this.words.add(word);
        }

        Node(
           final Node parent,
           final Set<Integer> words,
           final String text) {

            this.parent = parent;
            this.next = new Node[5];

            this.text = text;
            this.words = new HashSet<>(words);
        }

        void addWord(final int word) {
            this.words.add(word);
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
                if (nodes[i] != null) {
                    nodes[i].parent = this;
                }
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

        String parentText() {
            final StringBuilder result = new StringBuilder();
            Node current = parent;
            while (current.text != null) {
                result.insert(0, current.text);
                current = current.parent;
            }

            return result.toString();
        }
    }

    static class SuffixTree {
        private final List<String> words;
        private final Node root;

        SuffixTree() {
            this.words = new ArrayList<>();
            this.root = new Node();
        }

        void addWords(final String... words) {
            for (final String word : words) {
                addWord(word);
            }
        }

        void addWord(final String word) {
            final String actualWord = word + "$" + words.size();
            final char[] text = actualWord.toCharArray();
            for (int i = word.length(); i >= 0; i--) {
                add(root, actualWord, text, i);
            }

            words.add(word);
        }

        void add(final Node root, final String word, final char[] text, final int arrayIndex) {
            if ((root.contains(text[arrayIndex])) && (text[arrayIndex] != '$')) {
                final Node changing = root.get(text[arrayIndex]);

                int cut = -1;
                for (int inc = 1; ((cut < 0) && (inc < changing.text.length())); inc++) {
                    if ((text[arrayIndex + inc] == '$') || (text[arrayIndex + inc] != changing.text.charAt(inc))) {
                        cut = inc;
                    }
                }

                if (cut > 0) {
                    final Node descendingNode = new Node(
                                                       changing,
                                                       changing.words,
                                                       changing.text.substring(cut));

                    descendingNode.putAll(changing.next);
                    changing.clear();
                    changing.put(descendingNode.text.charAt(0), descendingNode);

                    final Node newNode = new Node(
                                                changing, 
                                                words.size(),
                                                word.substring(arrayIndex + cut));

                    changing.put(newNode.text.charAt(0), newNode);
                    changing.text = changing.text.substring(0, cut);
                    changing.addWord(words.size());
                } else if (!word.endsWith(changing.text)) {
                    changing.addWord(words.size());
                    add(changing, word, text, arrayIndex + changing.text.length());
                }
            } else {
                root.put(text[arrayIndex], new Node(
                                                  root, 
                                                  words.size(),
                                                  word.substring(arrayIndex)));
            }
        }

        List<Node> getNodes() {
            final Queue<Node> q = new LinkedList<>(root.values());
            final List<Node> result = new ArrayList<>();
            while (!q.isEmpty()) {
                final Node n = q.poll();
                if (n != null) {
                    result.add(n);
                    q.addAll(n.values());
                }
            }

            return result;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final SuffixTree tree = new SuffixTree();
            tree.addWords(in.next(), in.next());

            final List<Node> nodes = tree.getNodes();
            for (final Iterator<Node> it = nodes.iterator(); it.hasNext(); ) {
                final Node node = it.next();
                if ((node.words.size() > 1) || (!node.words.contains(0)) || ("$0".equals(node.text))) {
                    it.remove();
                }
            }

            String smallest = null;
            for (final Node node : nodes) {
                final String candidate = node.parentText() + node.text.charAt(0);
                if ((smallest == null) || (candidate.length() < smallest.length())) {
                    smallest = candidate;
                }
            }

            System.out.println(smallest);
        }
    }
}