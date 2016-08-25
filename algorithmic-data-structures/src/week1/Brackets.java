package week1;
import java.util.ArrayDeque;
import java.util.Scanner;

class Brackets {
    static class Item {
        private final int index;
        private final char c;

        Item(final int index, final char c) {
            this.index = index;
            this.c = c;
        }
    }

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.nextLine();

            int index = -1;
            final ArrayDeque<Item> q = new ArrayDeque<>();
            for (int i = 0; (index < 0) && (i < text.length()); i++) {
                final char c = text.charAt(i);
                switch(c) {
                    case '[': 
                    case '(':
                    case '{': q.addFirst(new Item(i, c)); break;
                    case ']': index = ((!q.isEmpty()) && (q.poll().c == '[')) ? -1 : i; break;
                    case ')': index = ((!q.isEmpty()) && (q.poll().c == '(')) ? -1 : i; break;
                    case '}': index = ((!q.isEmpty()) && (q.poll().c == '{')) ? -1 : i; break;
                    default :
                }
            }

            if ((index >= 0) || (q.isEmpty())) {
                System.out.println((index < 0) ? "Success" : (index + 1));
            } else {
                System.out.println(q.poll().index + 1);
            }
        }
    }
}