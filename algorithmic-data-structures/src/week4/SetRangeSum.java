package week4;

import java.util.Scanner;

class SetRangeSum {

    static class Vertex {
        private final int key;
        private long sum;

        private Vertex left, right, parent;
        Vertex(
            final int key, 
            final long sum, 
            final Vertex left, 
            final Vertex right, 
            final Vertex parent) {

            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    static class VertexPair {
        Vertex left, right;
        VertexPair() {
        }

        VertexPair(final Vertex left, final Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    private static Vertex root = null;
    private static final int MODULO = 1000000001;

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            int n = in.nextInt();
            int lastSumResult = 0;
            for (int i = 0; i < n; i++) {
                final String type = in.next();
                switch (type) {
                    case "+" : {
                        int x = in.nextInt();
                        insert((x + lastSumResult) % MODULO);
                        break;
                    } 
                    case "-" : {
                        int x = in.nextInt();
                        erase((x + lastSumResult) % MODULO);
                        break;
                    }
                    case "?" : {
                        int x = in.nextInt();
                        System.out.println(find((x + lastSumResult) % MODULO) ? "Found" : "Not found");
                        break;
                    } 
                    case "s" : {
                        final int l = in.nextInt(), r = in.nextInt();
                        final long res = sum((l + lastSumResult) % MODULO, (r + lastSumResult) % MODULO);
                        System.out.println(res);

                        lastSumResult = (int)(res % MODULO);
                    }
                }
            }
        }
    }

    static void update(final Vertex v) {
        if (v != null) {
            v.sum = v.key 
                    + ((v.left != null) ? v.left.sum : 0)
                    + ((v.right != null) ? v.right.sum : 0);

            if (v.left != null) {
                v.left.parent = v;
            }

            if (v.right != null) {
                v.right.parent = v;
            }
        }
    }

    static void smallRotation(final Vertex v) {
        Vertex parent = v.parent;
        if (parent != null) {
            final Vertex grandparent = v.parent.parent;
            if (parent.left == v) {
                parent.left = v.right;
                v.right = parent;
            } else {
                parent.right = v.left;
                v.left = parent;
            }

            update(parent);
            update(v);

            v.parent = grandparent;
            if (grandparent != null) {
                if (grandparent.left == parent) {
                    grandparent.left = v;
                } else {
                    grandparent.right = v;
                }
            }
        }
    }

    static void bigRotation(Vertex v) {
        if ((v.parent.left == v) && (v.parent.parent.left == v.parent)) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);

        } else if ((v.parent.right == v) && (v.parent.parent.right == v.parent)) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);

        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    static Vertex splay(final Vertex v) {
        if (v == null) {
            return null;
        }

        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }

            bigRotation(v);
        }

        return v;
    }

    static VertexPair find(final Vertex root, final int key) {
        Vertex v = root, last = root, next = null;
        while (v != null) {
            if ((v.key >= key) && ((next == null) || (v.key < next.key))) {
                next = v;
            }

            last = v;
            if (v.key == key) {
                break;
            }

            v = (v.key < key) ? v.right : v.left;
        }

        return new VertexPair(next, splay(last));
    }

    static VertexPair split(final Vertex root, final int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);

        final Vertex newRoot = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = newRoot;
            return result;
        }

        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }

        update(result.left);
        update(result.right);
        return result;
    }

    static Vertex merge(Vertex left, Vertex right) {
          if (left == null) {
              return right;
          } else if (right == null) {
              return left;
          } else {
              Vertex minRight = right;
              while (minRight.left != null) {
                minRight = minRight.left;
              }

              right = splay(minRight);
              right.left = left;
    
              update(right);
              return right;
          }
    }

    static void insert(final int x) {
        Vertex newVertex = null;
        final VertexPair leftRight = split(root, x);
        final Vertex left = leftRight.left, right = leftRight.right;
        if ((right == null) || (right.key != x)) {
            newVertex = new Vertex(x, x, null, null, null);
        }

        root = merge(merge(left, newVertex), right);
    }

    static void deleteNode(final Vertex n) {
        if (n.right == null) {
            root = n.left;
        } else {
            root = n.right;
            root.left = n.left;
        }

        if (root != null) {
            root.parent = null;
        }

        update(root);
    }


    static void erase(final int x) {
        final VertexPair first = find(root, x + 1);
        root = first.right;
        if (first.left != null) {
            root = splay(first.left);
        }

        final VertexPair second = find(root, x);
        root = second.right;
        if ((second.left != null) && (second.left.key == x)) {
            deleteNode(second.left);
        }
    }

    static boolean find(final int x) {
        final VertexPair vp = find(root, x);
        root = vp.right;
        return ((vp.left != null) && (vp.left.key == x));
    }

    static long sum(final int from, final int to) {
        final VertexPair leftMiddle = split(root, from);
        final VertexPair middleRight = split(leftMiddle.right, to + 1);
        final Vertex left = leftMiddle.left,
                     middle = middleRight.left, 
                     right = middleRight.right;

        long ans = 0;
        if (left!= null) {
            update(left);
        }

        if (right!= null) {
            update(right);
        }

        if (middle != null) {
            update(middle);
            if (middle.sum != 0) {
              ans = middle.sum;
            }
        }

        root = merge(merge(left, middle), right);
        if (root != null) {
            update(root);
        }

        return ans;
    }
}