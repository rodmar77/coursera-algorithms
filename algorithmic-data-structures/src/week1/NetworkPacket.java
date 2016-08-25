package week1;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class NetworkPacket {

    static class Item implements Comparable<Item> {
        private final int index, arrival, time;

        Item(final int index, final int arrival, final int time) {
            this.index = index;
            this.arrival = arrival;
            this.time = time;
        }

        public int compareTo(final Item that) {
            return (this.arrival == that.arrival) ?
                            Integer.compare(this.index, that.index) :
                            Integer.compare(this.arrival, that.arrival);
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int maxSize = in.nextInt();
            final Item[] items = new Item[in.nextInt()];
            if (items.length > 0) {
                final int[] answers = new int[items.length];

                for (int i = 0; i < items.length; i++) {
                    items[i] = new Item(i, in.nextInt(), in.nextInt());
                }

                Arrays.sort(items);

                final Queue<Item> queue = new LinkedList<>();
                int currentIndex = 0, time = 0;
                while ((!queue.isEmpty()) || (currentIndex < items.length)) {
                    if (queue.isEmpty()) {
                        time = items[currentIndex].arrival;
                        queue.offer(items[currentIndex++]);
                    } else {
                        final Item it = queue.peek();
                        answers[it.index] = time;
                        time += it.time;

                        while((currentIndex < items.length) && (items[currentIndex].arrival < time)) {
                            if (queue.size() < maxSize) {
                                queue.offer(items[currentIndex]);
                            } else {
                                answers[currentIndex] = -1;
                            }

                            currentIndex++;
                        }

                        queue.poll();
                    }
                }

                for (int i = 0; i < answers.length; i++) {
                    if (i > 0) {
                        System.out.print(" ");
                    }

                    System.out.print(answers[i]);
                }

                System.out.println();
            }
        }
    }
}
