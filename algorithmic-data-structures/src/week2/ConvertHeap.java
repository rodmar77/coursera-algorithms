package week2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ConvertHeap {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final long[] heap = new long[in.nextInt() + 1];
            for (int i = 1; i < heap.length; i++) {
                heap[i] = in.nextInt();
            }

            final List<String> swaps = new ArrayList<>();
            for (int i = heap.length/2; i > 0; i--) {
                siftDown(heap, i, swaps);
            }

            System.out.println(swaps.size());
            swaps.forEach(System.out::println);
        }
    }

    private static void siftDown(final long[] heap, final int index, final List<String> swaps) {
        final int size = heap.length - 1;

        int maxIndex = index, l = leftChild(index), r = rightChild(index);
        if ((l <= size) && (heap[l] < heap[maxIndex])) {
            maxIndex = l;
        }

        if ((r <= size) && (heap[r] < heap[maxIndex])) {
            maxIndex = r;
        }

        if (index != maxIndex) {
            swap(heap, index, maxIndex, swaps);
            siftDown(heap, maxIndex, swaps);
        }
    }

    private static int rightChild(final int index) {
        return 2*index + 1;
    }

    private static int leftChild(final int index) {
        return 2*index;
    }

    private static void swap(final long[] heap, final int i, final int j, final List<String> swaps) {
        swaps.add((i - 1) + " " + (j - 1));

        final long tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

}
