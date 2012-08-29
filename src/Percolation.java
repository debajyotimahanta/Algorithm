/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-27th 2012
 * This program is used to test the grid of size N
 * It check if it percolates
 * ---------------------------------------------------------*/

public class Percolation {
    // used to store status of grid
    private boolean[] id;

    // initiate quickfind using virtual top and bottom
    private WeightedQuickUnionUF objFind;
    // Intimate quick find without virtual top and bottom to prevent bckwash
    private WeightedQuickUnionUF objFind2;
    // store local variable to capture grid size
    private int nMax;

    private boolean isFirstJoinCalled = false;

    /*---------------------------------------------------------
     *This is a constructor that takes in gride size and constructs an array of size N*N
     * ---------------------------------------------------------*/

    public Percolation(int n) {
        // added 2 for 2 virtual top
        id = new boolean[n * n + 2];

        for (int i = 0; i < n * n + 2; i++) {

            id[i] = false;

        }
        // we are using +2 because we want to use virtual top and bottom
        objFind = new WeightedQuickUnionUF(n * n + 2);
        objFind2 = new WeightedQuickUnionUF(n * n + 1);

        nMax = n;

        for (int i1 = 1; i1 <= nMax; i1++) {
            objFind.union(0, i1);
            objFind2.union(0, i1);
        }
        for (int i1 = nMax * nMax; i1 > nMax * nMax - nMax; i1--) {
            objFind.union(i1, nMax * nMax + 1);
        }
    }

    /*---------------------------------------------------------
     *open site (row i, column j) if it is not already
     *This will throw exception if the index are out of bound
     *will check the sounding cells to see if then are 
     * ---------------------------------------------------------*/
    public void open(int i, int j) {
        if (i <= 0 || i > nMax)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > nMax)
            throw new IndexOutOfBoundsException("row index j out of bounds");

        // set value of index
        id[nMax * (i - 1) + j] = true;
        boolean isJoinSuccess = false;
        isJoinSuccess = shouldJoin(i - 1, j, i, j);
        isJoinSuccess = shouldJoin(i + 1, j, i, j);
        isJoinSuccess = shouldJoin(i, j - 1, i, j);
        isJoinSuccess = shouldJoin(i, j + 1, i, j);

        isFirstJoinCalled = true;

    }

    /*---------------------------------------------------------
     * is site (row i, column j) open
     * returns the array where grid is open or closed is checked
     * ---------------------------------------------------------*/
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > nMax)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > nMax)
            throw new IndexOutOfBoundsException("row index j out of bounds");

        return id[nMax * (i - 1) + j];

    }

    /*---------------------------------------------------------
     *is site (row i, column j) full
     *used the second find object to check if is full
     *it checks with all the elements in the top row
     * ---------------------------------------------------------*/
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > nMax)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > nMax)
            throw new IndexOutOfBoundsException("row index j out of bounds");
        if (!isOpen(i, j))
            return false;

        return objFind2.connected(0, nMax * (i - 1) + j);

    }

    /*---------------------------------------------------------
     *does the system percolate?
     *it uses the first object, with virtual top and bottom to see if they are connected
     * ---------------------------------------------------------*/
    public boolean percolates() {
        if (isFirstJoinCalled)
            return objFind.connected(0, nMax * nMax + 1);
        else
            return false;
    }

    /*---------------------------------------------------------
     *this is a private method that will first check if the two joins can be joined
     *if they can be joined, then the join from two object instance is called
     * ---------------------------------------------------------*/
    private boolean shouldJoin(int l, int m, int x, int y) {
        if (l <= 0 || l > nMax)
            return false;
        if (m <= 0 || m > nMax)
            return false;

        int lm = nMax * (l - 1) + m;
        int xy = nMax * (x - 1) + y;

        if (isOpen(l, m)) {
            objFind.union(lm, xy);
            objFind2.union(lm, xy);
        }
        return true;

    }
}