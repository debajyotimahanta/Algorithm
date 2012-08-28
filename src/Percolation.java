/*---------------------------------------------------------
 *Name: Debajyoti Mahanta
 * login: debajyoti.mahanta@gmail.com
 * date: Aug-27th 2012
 * This program is used to test weather a grid percolates or not using
 *  	- the grid size N of the percolation system.
 *    	- Creates an N-by-N grid of sites (intially all blocked)
 *    	- Reads in a sequence of sites (row i, column j) to open.
 * ---------------------------------------------------------*/

public class Percolation {
	// used to store status of grid
	private boolean[] id;

	// initiate quickfind using virtual top and bottom
	private WeightedQuickUnionUF objFind;
	// intitate quick find wihtout virutal top and bottom to prevent bckwash
	private WeightedQuickUnionUF objFind2;
	// store local vairable to capture grid size
	private int NMax;

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

		// Initiate and connect virtual top and bottom
		for (int i = 1; i <= N; i++) {
			objFind.union(0, i);
		}
		for (int i = N * N; i > N * N - N; i--) {
			objFind.union(i, N * N + 1);
		}
		NMax = N;
	}

	/*---------------------------------------------------------
	 *open site (row i, column j) if it is not already
	 *This will trow excepcption if the index are out of bound
	 *will check the sourding cells to see if then are 
	 * ---------------------------------------------------------*/
	public void open(int i, int j) {
		if (i <= 0 || i > NMax)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > NMax)
			throw new IndexOutOfBoundsException("row index j out of bounds");

		// set value of indexs
		id[NMax * (i - 1) + j] = true;
		shouldJoin(i - 1, j, i, j);
		shouldJoin(i + 1, j, i, j);
		shouldJoin(i, j - 1, i, j);
		shouldJoin(i, j + 1, i, j);

	}

	/*---------------------------------------------------------
	 * is site (row i, column j) open
	 * returns the array where grid is open or closed is checked
	 * ---------------------------------------------------------*/
	public boolean isOpen(int i, int j) {
		return id[NMax * (i - 1) + j];
	}

	/*---------------------------------------------------------
	 *is site (row i, column j) full
	 *used the second find object to check if is full
	 *it checks with all the elements in the top row
	 * ---------------------------------------------------------*/
	public boolean isFull(int i, int j) {
		if (!isOpen(i, j))
			return false;
		// return objFind.connected(0,NMax*(i-1)+j);
		boolean isConnected = false;
		for (int i1 = 0; i1 < NMax; i1++) {
			isConnected = objFind2.connected(i1, NMax * (i - 1) + j - 1);
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
		return objFind.connected(0, NMax * NMax + 1);
	}

	/*---------------------------------------------------------
	 *this is a private method that will first check if the two joins can be joined
	 *if they can be joined, then the join from two object instance is called
	 * ---------------------------------------------------------*/
	private void shouldJoin(int l, int m, int x, int y) {
		if (l <= 0 || l > NMax)
			return;
		if (m <= 0 || m > NMax)
			return;

		int lm = NMax * (l - 1) + m;
		int xy = NMax * (x - 1) + y;

		if (isOpen(l, m)) {
			objFind.union(lm, xy);
			objFind2.union(lm - 1, xy - 1);
		}

	}
}