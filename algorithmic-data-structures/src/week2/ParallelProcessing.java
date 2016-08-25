package week2;
import java.util.PriorityQueue;
import java.util.Scanner;

class ParallelProcessing {

    static class Processor implements Comparable<Processor> {
        private long currentTime;

        private final int index; 

        Processor(final int index) {
            this.index = index;
        }

        String submit(final long totalTime) {
            final String result = this.index + " " + this.currentTime;
            this.currentTime += totalTime;
            return result;
        }

        public int compareTo(final Processor that) {
            return (this.currentTime == that.currentTime) ?
                            Integer.compare(this.index, that.index) :
                            Long.compare(this.currentTime, that.currentTime);
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int processors = in.nextInt();
            final PriorityQueue<Processor> pq = new PriorityQueue<>();
            for (int i = 0; i < processors; i++) {
                pq.offer(new Processor(i));
            }

            final int times = in.nextInt();
            for (int i = 0; i < times; i++) {
                final Processor next = pq.poll();
                System.out.println(next.submit(in.nextLong()));
                pq.offer(next);
            }
        }
    }
}
