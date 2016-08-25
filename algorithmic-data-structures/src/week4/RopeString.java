package week4;

import java.util.Scanner;

class RopeString {

    static class Node {
        private int start, offset, length, totalLength;
        private Node parent, left, right;
    }

    static class NodePair {
        private final Node left, right;

        NodePair() {
            this(null, null);
        }

        NodePair(final Node left, final Node right) {
            this.left = left;
            this.right = right;
        }
    }

    static class Rope {
        private Node root;
        private String text;

        Rope(final String text) {
            this.root = new Node();
            this.root.length = text.length();
            this.root.totalLength = root.length;
            this.text = text;
        }

        void process(final int i, final int j, final int k) {
            final NodePair leftN = stSplit(i, root);
            Node left = leftN.left, n = leftN.right;

            Node right = null;
            if((j+1) < text.length()) {
              final NodePair nRight = stSplit(j+1-i, n);
              n = nRight.left; right = nRight.right;
            }

            final Node t = stMerge(left, right);
            if (k < (t.offset + t.totalLength)) {
              final NodePair c = stSplit(k, t);
              left = c.left; right = c.right;
            } else {
              right = null;
              left = t;
            }

            root = stMerge(left, n);
            root = stMerge(root, right);
        }

        public String result() {
            final StringBuilder result = new StringBuilder();
            stTraverse(root, result);
            return result.toString();
        }

        void stTraverse(final Node root, final StringBuilder result) {
            if (root != null) {
                stTraverse(root.left, result);
                result.append(text.substring(root.start, root.start + root.length));
                stTraverse(root.right, result);
            }
        }

        Node find(final int offset, final Node root) {
            if (root == null) {
                return null;
            } else if ((offset >= root.offset) && (offset < (root.offset + root.length))) {
                return root;
            } else if (offset < root.offset) {
                return find(offset, root.left);
            } else {
                return find(offset - root.offset - root.length, root.right);
            }
        }

        Node stFind(final int offset, final Node root) {
            final Node n = find(offset, root);
            splay(n);

            if (n.offset < offset) {
                final Node newNode = new Node();
                newNode.start = n.start;
                newNode.length = offset - n.offset;

                n.start = newNode.start + newNode.length;
                n.length -= newNode.length;
                newNode.left = n.left;

                update(newNode);
                n.left = newNode;
                update(n);
            }

            return n;
        }

        NodePair stSplit(final int offset, final Node root) {
            if (root == null) {
                return new NodePair();
            }

            final Node n = stFind(offset, root);
            final Node left = n.left;
            if (left != null) {
                left.parent = null;
            }

            n.left = null;
            update(n);

            return new NodePair(left, n);
        }

        Node stMerge(final Node left, final Node right) {
            if (left == null) {
                return right;
            }

            final Node root = findLast(left);
            splay(root);
            root.right = right;
            update(root);
            return root;
        }

        Node findLast(final Node root) {
            if (root == null) {
                return null;
            }

            Node ret = root;
            while (ret.right != null) {
                ret = ret.right;
            }

            return ret;
        }

        void update(final Node n) {
            if (n != null) {
                n.offset = 0;
                n.totalLength = n.length;

                if (n.left != null) {
                    n.left.parent = n;
                    n.offset = n.left.offset + n.left.totalLength;
                }

                if (n.right != null) {
                    n.right.parent = n;
                    n.totalLength += n.right.totalLength + n.right.offset;
                }
            }
        }

        void zig(final Node n) {
            if (n.parent != null) {
                final Node parent = n.parent, grandparent = n.parent.parent;

                if (parent.left == n) {
                    parent.left = n.right;
                    n.right = parent;
                } else {
                    parent.right = n.left;
                    n.left = parent;
                }

                update(parent);
                update(n);

                if (grandparent != null) {
                    if (grandparent.left == parent) {
                        grandparent.left = n;
                    } else {
                        grandparent.right = n;
                    }
                }

                n.parent = grandparent;
                update(grandparent);
            }
        }

        void zigZigZag(final Node n) {
            if    (((n.parent.left == n) && (n.parent.parent.left == n.parent))
                || ((n.parent.right == n) && (n.parent.parent.right == n.parent))) {

                zig(n.parent);
                zig(n);
            } else {
                zig(n);
                zig(n);
            }
        }

        void splay(final Node n) {
            if (n != null) {
                while (n.parent != null) {
                    if (n.parent.parent == null) {
                        zig(n);
                        break;
                    }

                    zigZigZag(n);
                }
            }
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final Rope rope = new Rope(in.next());
            final int ops = in.nextInt();
            for (int i = 0; i < ops; i++) {
                rope.process(in.nextInt(), in.nextInt(), in.nextInt());
            }

            System.out.println(rope.result());
        }
    }
}