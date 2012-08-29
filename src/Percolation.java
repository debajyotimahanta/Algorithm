/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-27th 2012
 * This program is used to test weather a grid percolates or not using
 *  	- the grid size N of the percolation system.
 *    	- Creates an N-by-N grid of sites (initially all blocked)
 *    	- Reads in a sequence of sites (row i, column j) to open.
 * ---------------------------------------------------------*/

public class Percolation {
	// used to store status of grid
	private boolean[] id;

	// initiate quickfind using virtual top and bottom
	private WeightedQuickUnionUF objFind;
	// Intimate quick find without virtual top and bottom to prevent bckwash
	private WeightedQuickUnionUF objFind2;
	// store local variable to capture grid size
	private int Nmax;

	private boolean isFirstJoinCalled = false;

	/*---------------------------------------------------------
	 *This is a constructor that takes in gride size and constructs an array of size N*N
	 * ---------------------------------------------------------*/

	public Percolation(int N) {
		// added 2 for 2 virtual top
		id = new boolean[N * N + 2];

		for (int i = 0; i < N * N + 2; i++) {

			id[i] = false;

		}
		// we are using +2 because we want to use virutal top and bottom
		objFind = new WeightedQuickUnionUF(N * N + 2);
		objFind2 = new WeightedQuickUnionUF(N * N);

		Nmax = N;
	}

	/*---------------------------------------------------------
	 *open site (row i, column j) if it is not already
	 *This will trow excepcption if the index are out of bound
	 *will check the sourding cells to see if then are 
	 * ---------------------------------------------------------*/
	public void open(int i, int j) {
		if (i <= 0 || i > Nmax)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > Nmax)
			throw new IndexOutOfBoundsException("row index j out of bounds");

		// set value of indexs
		id[Nmax * (i - 1) + j] = true;
		boolean isJoinSuccess = false;
		isJoinSuccess = shouldJoin(i - 1, j, i, j);
		isJoinSuccess = shouldJoin(i + 1, j, i, j);
		isJoinSuccess = shouldJoin(i, j - 1, i, j);
		isJoinSuccess = shouldJoin(i, j + 1, i, j);

		if (isJoinSuccess && !isFirstJoinCalled) {
			// Initiate and connect virtual top and bottom
			for (int i1 = 1; i1 <= Nmax; i1++) {
				objFind.union(0, i1);
			}
			for (int i1 = Nmax * Nmax; i1 > Nmax * Nmax - Nmax; i1--) {
				objFind.union(i1, Nmax * Nmax + 1);
			}
			isFirstJoinCalled = true;
		}
	}

	/*---------------------------------------------------------
	 * is site (row i, column j) open
	 * returns the array where grid is open or closed is checked
	 * ---------------------------------------------------------*/
	public boolean isOpen(int i, int j) {
		if (i <= 0 || i > Nmax)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > Nmax)
			throw new IndexOutOfBoundsException("row index j out of bounds");
		return id[Nmax * (i - 1) + j];
	}

	/*---------------------------------------------------------
	 *is site (row i, column j) full
	 *used the second find object to check if is full
	 *it checks with all the elements in the top row
	 * ---------------------------------------------------------*/
	public boolean isFull(int i, int j) {
		if (i <= 0 || i > Nmax)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > Nmax)
			throw new IndexOutOfBoundsException("row index j out of bounds");
		if (!isOpen(i, j))
			return false;
		// return objFind.connected(0,Nmax*(i-1)+j);
		boolean isConnected = false;
		for (int i1 = 0; i1 < Nmax; i1++) {
			isConnected = objFind2.connected(i1, Nmax * (i - 1) + j - 1);
			if (isConnected)
				break;
		}
		return isConnected;
	}

	/*---------------------------------------------------------
	 *does the system percolate?
	 *it uses the first object, with virutal top and bottom to see if they are connected
	 * ---------------------------------------------------------*/
	public boolean percolates() {
		return objFind.connected(0, Nmax * Nmax + 1);
	}

	/*---------------------------------------------------------
	 *this is a private method that will first check if the two joins can be joined
	 *if they can be joined, then the join from two object instance is called
	 * ---------------------------------------------------------*/
	private boolean shouldJoin(int l, int m, int x, int y) {
		if (l <= 0 || l > Nmax)
			return false;
		if (m <= 0 || m > Nmax)
			return false;

		int lm = Nmax * (l - 1) + m;
		int xy = Nmax * (x - 1) + y;

		if (isOpen(l, m)) {
			objFind.union(lm, xy);
			objFind2.union(lm - 1, xy - 1);
		}
		return true;

	}
}