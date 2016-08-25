package week3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SuffixTreeFromArray {

    static class SuffixTreeNode {
    	private static final AtomicInteger nodeIndexer = new AtomicInteger(0);

    	private SuffixTreeNode parent;
    	private final Map<Character, SuffixTreeNode> children;

    	private List<SuffixTreeNode> list;

    	private final int node;
    	private int stringDepth, edgeStart, edgeEnd;

    	SuffixTreeNode(final int stringDepth, final int start, final int end) {
    		this(null, stringDepth, start, end);
        }

    	SuffixTreeNode(
    			final SuffixTreeNode parent, 
    			final int stringDepth, 
    			final int start, 
    			final int end) {

    		this.children = new HashMap<>();

    		this.node = nodeIndexer.getAndIncrement();
    		this.parent = parent;
            this.edgeStart = start;
            this.edgeEnd = end;
        }

		public SuffixTreeNode get(final int index) {
			if (list == null) {
				list = new ArrayList<>(children.values());
			}

			return list.get(index);
		}

		public int size() {
			return children.size();
		}
    }

    static Map<Integer, SuffixTreeNode> SuffixTreeFromSuffixArray(
			                                            final int[] order,
			                                            final int[] lcpArray,
			                                            final String text) {

    	final Map<Integer, SuffixTreeNode> tree = new HashMap<>();
    	final SuffixTreeNode root = new SuffixTreeNode(0, -1, -1);

    	tree.put(root.node, root);
    	int lcpPrev = 0;

    	SuffixTreeNode curNode = root;
    	for (int i = 0; i < text.length(); i++) {
    		final int suffix = order[i];
    		while (curNode.stringDepth > lcpPrev) {
    			curNode = curNode.parent;
    		}

    		if (curNode.stringDepth == lcpPrev) {
    			createNewLeaf(curNode, text, suffix);
    			tree.put(curNode.node, curNode);
    		} else {
    			final int edgeStart = order[i - 1] + curNode.stringDepth;
    			final int offset = lcpPrev - curNode.stringDepth;
    			final SuffixTreeNode midNode = breakEdge(curNode, text, edgeStart, offset);
    			curNode = createNewLeaf(midNode, text, suffix);
    			tree.put(curNode.node, curNode);
    		}

    		if (i < text.length() - 1) {
    			lcpPrev = lcpArray[i];
    		}
    	}

        return tree;
    }


    private static SuffixTreeNode breakEdge(
    									final SuffixTreeNode node, 
    									final String text, 
    									final int start, 
    									final int offset) {

    	final char startChar = text.charAt(start);
    	final char midChar = text.charAt(start + offset);
    	final SuffixTreeNode midNode = new SuffixTreeNode(
    													node,
    													node.stringDepth + offset,
    													start + offset - 1,
    													node.children.get(startChar).edgeEnd);

    	midNode.children.put(midChar, node.children.get(startChar));
    	node.children.get(startChar).parent = midNode;
    	node.children.get(startChar).edgeStart += offset;
    	node.children.put(startChar, midNode);

    	return midNode;
	}


	private static SuffixTreeNode createNewLeaf(final SuffixTreeNode node, final String text, final int suffix) {
    	final SuffixTreeNode leaf = new SuffixTreeNode(
    											node, 
    											text.length() - suffix, 
    											suffix + node.stringDepth, 
    											text.length() - 1);

    	node.children.put(text.charAt(leaf.edgeStart), leaf);
    	return leaf;
	}


	static public void main(final String[] args) {
        try (final Scanner in = new Scanner("AAA$ 3 2 1 0 0 1 2")) {
            final String text = in.next();
            final int[] suffixArray = new int[text.length()];
            for (int i = 0; i < suffixArray.length; ++i) {
                suffixArray[i] = in.nextInt();
            }

            int[] lcpArray = new int[text.length() - 1];
            for (int i = 0; i + 1 < text.length(); ++i) {
                lcpArray[i] = in.nextInt();
            }

            System.out.println(text);
            final Map<Integer, SuffixTreeNode> suffixTree = SuffixTreeFromSuffixArray(suffixArray, lcpArray, text);
            final List<String> result = new ArrayList<String>();

            final int[] nodeStack = new int[text.length()];
            final int[] edgeIndexStack = new int[text.length()];

            int stackSize = 1;
            while (stackSize > 0) {
                final int node = nodeStack[stackSize - 1];
                int edgeIndex = edgeIndexStack[stackSize - 1];

                stackSize -= 1;
                if (suffixTree.get(node) != null) {
                    if (edgeIndex + 1 < suffixTree.get(node).size()) {
                        nodeStack[stackSize] = node;
                        edgeIndexStack[stackSize] = edgeIndex + 1;
                        stackSize++;
                    }

                    result.add(
                            suffixTree.get(node).get(edgeIndex).edgeStart 
                            + " " 
                            + suffixTree.get(node).get(edgeIndex).edgeEnd);

                    nodeStack[stackSize] = suffixTree.get(node).get(edgeIndex).node;
                    edgeIndexStack[stackSize] = 0;
                    stackSize++;
                }
            }

            result.forEach(System.out::println);
        }
    }
}