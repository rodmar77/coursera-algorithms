import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StockCharts {

    static class Stock {
        private final int[] values;

        Stock(final int[] values) {
            this.values = values;
            Arrays.sort(values);
        }

        boolean intersectsWith(final Stock that) {
            final int max = that.values[that.values.length - 1];
            for (int i = 0; (i < values.length) && (values[i] <= max); i++) {
                final int index = Arrays.binarySearch(that.values, this.values[i]);
                if (index >= 0) {
                    return true;
                }
            }

            return false;
        }
    }

    static class Stocks {
        private final List<Stock> stocks;

        Stocks() {
            this.stocks = new ArrayList<>();
        }

        public Stocks(final Stock stock) {
            this();
            this.stocks.add(stock);
        }

        boolean add(final Stock stock) {
            if (canAdd(stock)) {
                this.stocks.add(stock);
                return true;
            }

            return false;
        }

        boolean canAdd(final Stock stock) {
            for (final Stock s : stocks) {
                if (stock.intersectsWith(s)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        try (final Scanner in = new Scanner("3 4 1 2 3 4 2 3 4 6 6 5 4 3")) {
            final int stockCount = in.nextInt(), stockSize = in.nextInt();

            final List<Stocks> stocks = new ArrayList<>();
            for (int i = 0; i < stockCount; i++) {
                final Stock stock = new Stock(readArray(in, stockSize));
                boolean added = false;
                for (final Stocks ss : stocks) {
                    if (ss.add(stock)) {
                        added = true;
                        break;
                    }
                }

                if (!added) {
                    stocks.add(new Stocks(stock));
                }
            }

            System.out.println(stocks.size());
        }
    }

    private static int[] readArray(final Scanner in, final int stockSize) {
        final int[] result = new int[stockSize];
        for (int i = 0; i < stockSize; i++) {
            result[i] = in.nextInt();
        }

        return result;
    }
}