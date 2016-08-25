package week2;
public class DisjointSetsQuiz {

    public static void main(final String[] args) {
        final int[] disjointSet = new int[61], rank = new int[61];
        for (int i = 1; i <= 60; i++) {
            disjointSet[i] = i;
        }

        for (int i = 1; i <= 30; i++) {
            union(i, 2*i, disjointSet, rank);
        }

        for (int i = 1; i <= 20; i++) {
            union(i, 3*i, disjointSet, rank);
        }

        for (int i = 1; i <= 12; i++) {
            union(i, 5*i, disjointSet, rank);
        }

        for (int i = 1; i <= 60; i++) {
            find(i, disjointSet);
        }

        int max = 0;
        for (int i = 1; i <= 60; i++) {
            max = Math.max(max, height(i, disjointSet));
        }

        System.out.println(max);
    }

    private static int height(final int i, final int[] disjointSet) {
        if (disjointSet[i] == i) {
            return 0;
        }

        return 1 + height(disjointSet[i], disjointSet);
    }

    private static void union(final int i, final int j, final int[] disjointSet, final int[] rank) {
        final int iid = find(i, disjointSet), jid = find(j, disjointSet);
        disjointSet[jid] = iid;
    }

    private static int find(final int i, final int[] disjointSet) {
        if (i != disjointSet[i]) {
            disjointSet[i] = find(disjointSet[i], disjointSet);
        }

        return disjointSet[i];
    }

}
