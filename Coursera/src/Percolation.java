public class Percolation {
    private final int size;
    private final int TOP = 0, BOTTOM;
    private int[] parent;
    // private int[] rank;
    private boolean[][] opened;
    private WeightedQuickUnionUF uf;

    /**
     * create n-by-n parent, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) { throw new IndexOutOfBoundsException(); }
        this.size = n;
        BOTTOM = n * n + 1;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.opened = new boolean[n][n];
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(1, 2);
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validate(row, col);
        opened[row - 1][col - 1] = true;
        final int index = index(row, col);
        if (row == 1) {uf.union(TOP, index);}
        if (row == this.size) { uf.union(BOTTOM, index);}

        if (col > 1 && isOpen(row, col - 1)) {uf.union(index, index(row, col - 1));}
        if (col < this.size && isOpen(row, col + 1)) {uf.union(index, index(row, col + 1));}
        if (row > 1 && isOpen(row - 1, col)) {uf.union(index, index(row - 1, col));}
        if (row < this.size && isOpen(row + 1, col)) {uf.union(index, index(row + 1, col));}
    }

    private void validate(final int row, final int col) {
        if (row < 1 || row > size) { throw new IndexOutOfBoundsException(); }
        if (col < 1 || col > size) { throw new IndexOutOfBoundsException(); }
    }

    private int index(final int row, final int col) {
        return this.size * (row - 1) + col - 1 + (1); // TOP's offset
    }

    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row - 1][col - 1];
    }

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.connected(TOP, index(row, col));
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean[] booleans : opened) {
            for (boolean b : booleans) {
                if (b) { count += 1; }
            }
        }
        return count;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {return uf.connected(TOP, BOTTOM);}
}

class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of sites in subtree rooted at i
    private int count;      // number of components
    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
