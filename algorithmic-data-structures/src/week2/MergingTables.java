package week2;
import java.util.Scanner;

class MergingTables {

    static class Table {
        Table parent;
        int rank, numberOfRows;

        Table(final int numberOfRows) {
            this.numberOfRows = numberOfRows;
            this.rank = 0;
            this.parent = this;
        }

        Table getParent() {
            Table superRoot = this;
            while (superRoot != superRoot.parent) {
                superRoot = superRoot.parent;
            }

            Table temp = this;
            while (temp != superRoot) {
                final Table oldParent = temp.parent;
                temp.parent = superRoot;
                temp = oldParent;
            }

            return superRoot;
        }
    }

    private static int maximumNumberOfRows = -1;

    public static void main(final String[] args) {
        try (final Scanner in = new Scanner(System.in)) {
            final int n = in.nextInt(), m = in.nextInt();

            final Table[] tables = new Table[n];
            for (int i = 0; i < n; i++) {
                final int numberOfRows = in.nextInt();
                tables[i] = new Table(numberOfRows);
                maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
            }

            for (int i = 0; i < m; i++) {
                final int destination = in.nextInt() - 1, source = in.nextInt() - 1;
                merge(tables[destination], tables[source]);
                System.out.println(maximumNumberOfRows);
            }
        }
    }

    static void merge(final Table destination, final Table source) {
        final Table realDestination = destination.getParent();
        final Table realSource = source.getParent();
        if (realDestination != realSource) {
            if (realSource.rank <= realDestination.rank) {
                realSource.parent = realDestination;
                realDestination.numberOfRows += realSource.numberOfRows;
                realSource.numberOfRows = 0;
            } else {
                realDestination.parent = realSource;
                realSource.numberOfRows += realDestination.numberOfRows;
                realDestination.numberOfRows = 0;
            }

            if (realSource.rank == realDestination.rank) {
                realDestination.rank++;
            }

            maximumNumberOfRows = max(
                            maximumNumberOfRows, 
                            realSource.numberOfRows,
                            realDestination.numberOfRows);
        }
    }

    private static int max(final int a, final int b, final int c) {
        return ((a >= b) && (a >= c)) ? a : ((b >= c) ? b : c);
    }
}