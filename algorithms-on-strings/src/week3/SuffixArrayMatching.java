package week3;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class SuffixArrayMatching {

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final String text = in.next() + "$";
            final int[] suffixArray = buildSuffixArray(text);

            final Set<Integer> values = new HashSet<>();
            final int patternCount = in.nextInt();
            for (int i = 0; i < patternCount; i++) {
                addOccurrences(in.next(), text, suffixArray, values);
            }

            boolean first = true;
            for (final int value : values) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(" ");
                }

                System.out.print(value);
            }

            System.out.println();
        }
    }

    private static int[] buildSuffixArray(final String text) {
        int[] order = sortCharacters(text);
        int[] classes = computeCharClasses(text, order);
        int l = 1;
        while (l < text.length()) {
            order = sortDoubled(text, l, order, classes);
            classes = updateClasses(order, classes, l);
            l *= 2;
        }

        return order;
    }

    private static int[] sortCharacters(final String text) {
        final int[] order = new int[text.length()], count = new int[5];
        for (int i = 0; i < text.length(); i++) {
            count[indexOf(text.charAt(i))]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = text.length() - 1; i >= 0; i--) {
            order[--count[indexOf(text.charAt(i))]] = i;
        }

        return order;
    }

    private static int[] computeCharClasses(final String text, final int[] order) {
        final int[] result = new int[text.length()];
        for (int i = 1; i < result.length; i++) {
            if (text.charAt(order[i]) == text.charAt(order[i - 1])) {
                result[order[i]] = result[order[i - 1]];
            } else {
                result[order[i]] = result[order[i - 1]] + 1;
            }
        }

        return result;
    }

    private static int[] sortDoubled(
                                final String text, 
                                final int l, 
                                final int[] order, 
                                final int[] classes) {

        final int length = text.length();
        final int[] count = new int[length], newOrder = new int[length];
        for (int i = 0; i < length; i++) {
            count[classes[i]]++;
        }

        for (int i = 1; i < length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = length - 1; i >= 0; i--) {
            final int start = (order[i] - l + length) % length;
            newOrder[--count[classes[start]]] = start;
        }

        return newOrder;
    }

    private static int[] updateClasses(final int[] newOrder, final int[] classes, final int l) {
        final int n = newOrder.length;
        final int[] newClass = new int[n];
        for (int i = 1; i < n; i++) {
            final int cur = newOrder[i], prev = newOrder[i - 1];
            final int mid = (cur + l) % n, midPrev = (prev + l) % n;
            if ((classes[cur] != classes[prev]) || (classes[mid] != classes[midPrev])) {
                newClass[cur] = newClass[prev] + 1;
            } else {
                newClass[cur] = newClass[prev];
            }
        }

        return newClass;
    }

    private static void addOccurrences(
                                    final String pattern, 
                                    final String text, 
                                    final int[] suffixArray, 
                                    final Set<Integer> result) {

        final int textLength = text.length(), pattLength = pattern.length();
        int minIndex = 0, maxIndex = textLength;
        while(minIndex < maxIndex) {
            final int midIndex = (minIndex + maxIndex) / 2;
            final String substring = text.substring(
                                                suffixArray[midIndex], 
                                                suffixArray[midIndex] + 
                                                    min(
                                                      textLength - suffixArray[midIndex], 
                                                      pattLength));

            final int flag = substring.compareTo(pattern);
            if (flag < 0) {
                minIndex = midIndex + 1;
            } else {
                maxIndex = midIndex;
            }
        }

        maxIndex = textLength;
        int start = minIndex;
        while(minIndex < maxIndex) {
            final int midIndex = (minIndex + maxIndex) / 2;
            final String substring = text.substring(
                                                suffixArray[midIndex], 
                                                suffixArray[midIndex] + 
                                                    min(
                                                      textLength - suffixArray[midIndex], 
                                                      pattLength));

            final int flag = substring.compareTo(pattern);
            if (flag > 0) {
                maxIndex = midIndex;
            } else {
                minIndex = midIndex + 1;
            }
        }

        int end = maxIndex;
        if (start <= end) {
            for (int i = start; i < end; i++) {
                result.add(suffixArray[i]);
            }
        }
    }

    private static int min(final int a, final int b) {
        return (a < b) ? a : b;
    }

    private static int indexOf(final char c) {
        switch (c) {
            case '$': return 0; 
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: return -1;
        }
    }
}