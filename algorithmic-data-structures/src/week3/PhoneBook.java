package week3;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class PhoneBook {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final Map<String, String> phoneBook = new HashMap<>();
            final int cases = in.nextInt();
            for (int i = 0; i < cases; i++) {
                final String command = in.next();
                if ("find".equals(command)) {
                    System.out.println(phoneBook.getOrDefault(in.next(), "not found"));
                } else if ("add".equals(command)) {
                    phoneBook.put(in.next(), in.next());
                } else if ("del".equals(command)) {
                    phoneBook.remove(in.next());
                }
            }
        }
    }
}
