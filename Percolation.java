/**
 * Created with IntelliJ IDEA.
 * User: rshen
 * Date: 7/13/14
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class Percolation {
    private boolean[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF backWash;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {
       if (N <= 0) {
           throw new IllegalArgumentException();
       }
       grid = new boolean[N+1][N+1];
       gridSize = N;
       unionFind = new WeightedQuickUnionUF(N*N+2);
       backWash = new WeightedQuickUnionUF(N*N+1);
       virtualTop = N*N;
       virtualBottom = N*N+1;

       for (int i = 1; i <= gridSize; i++) {
           for (int j = 1; j < gridSize; j++) {
               grid[i][j] = false;
           }
       }
    }

    public void open(int i, int j) {
        if (i > gridSize || j > gridSize || i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException();
        }
         if (!grid[i][j]) {
             grid[i][j] = true;
             if (i == 1) {
                unionFind.union(mapToSite(i, j), virtualTop);
                backWash.union(mapToSite(i, j), virtualTop);
             }
             if (i == gridSize) {
                 unionFind.union(mapToSite(i, j), virtualBottom);
             }
             if (i-1 > 0 && isOpen(i-1, j)) {
                unionFind.union(mapToSite(i, j), mapToSite(i-1, j));
                backWash.union(mapToSite(i, j), mapToSite(i-1, j));
             }
             if (i+1 <= gridSize && isOpen(i+1, j)) {
                 unionFind.union(mapToSite(i, j), mapToSite(i+1, j));
                 backWash.union(mapToSite(i, j), mapToSite(i+1, j));
             }
             if (j-1 > 0 && isOpen(i, j-1)) {
                 unionFind.union(mapToSite(i, j-1), mapToSite(i, j));
                 backWash.union(mapToSite(i, j-1), mapToSite(i, j));
             }
             if (j+1 <= gridSize && isOpen(i, j+1)) {
                 unionFind.union(mapToSite(i, j+1), mapToSite(i, j));
                 backWash.union(mapToSite(i, j+1), mapToSite(i, j));
             }
         }
    }

    public boolean isOpen(int i, int j) {
        if (i > gridSize || j > gridSize || i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException();
        }
        return grid[i][j];
    }

    public boolean isFull(int i, int j) {
        if (i > gridSize || j > gridSize || i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException();
        }
        return unionFind.connected(mapToSite(i, j), virtualTop) && backWash.connected(mapToSite(i, j), virtualTop);
    }

    public boolean percolates() {
        return unionFind.connected(virtualTop, virtualBottom);
    }

    private int mapToSite(int i, int j) {
        return (i-1)*gridSize + j-1;
    }
}
