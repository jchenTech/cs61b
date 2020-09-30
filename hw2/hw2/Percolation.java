package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

/** Models a percolation system
 *
 * @author Jianjun Chen
 * @note All method calls except the constructor should work in constant time.
 */

public class Percolation {
    private int N;
    private boolean[] openGridIn1D;
    private int nOpen = 0;
    private WeightedQuickUnionUF connectedGrids;
    private Set<Integer> topFullIDs = new HashSet<>();
    private Set<Integer> bottomFullIDs = new HashSet<>();
    private boolean percolated = false;



    /* Returns 1D index corresponding to the given grid*/
    private int xyTo1D(int row, int col) {
        return N * row + col;
    }

    /** create N-by-N grid, with all sites initially blocked
     * @throws IllegalArgumentException if {N <= 0}
     */
    public Percolation(int N) throws IllegalArgumentException {
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N should be greater than 0 but given N = " + N + "."
            );
        }
        this.N = N;
        openGridIn1D = new boolean[N * N];
        for (int i = 0; i < N * N; i++){
            openGridIn1D[i] = false;
        }
        connectedGrids = new WeightedQuickUnionUF(N * N);
    }

    /* Check if the given coordinate is invalid*/
    private boolean isInvalidIndex(int row, int col) {
        if (row > N - 1 || col > N - 1 || row < 0 || col < 0) {
            return true;
        }
        return false;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) throws java.lang.IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid argument given: row = " + row + "col = " + col + "."
            );
        }
        int indexIn1D = xyTo1D(row, col);
        openGridIn1D[indexIn1D] = true;
        nOpen += 1;
        int id = connectedGrids.find(indexIn1D);
        if (indexIn1D < N) { //Add grid's Id to topFullIDs if it's in the top layer
            topFullIDs.add(id);
        }
        if (N * N - indexIn1D <= N) {//Add grid's Id to bottomFullIDs if it's in the bottom layer
            bottomFullIDs.add(id);
        }

        // Update connection and full conditions both from top and bottom with neighbors around
        updateConnection(row, col, row - 1, col); //North
        updateConnection(row, col, row, col - 1); //East
        updateConnection(row, col, row + 1, col); //South
        updateConnection(row, col, row, col + 1); //West

        //Update percolation condition
        int newId = connectedGrids.find(indexIn1D);
        if (topFullIDs.contains(newId) && bottomFullIDs.contains(newId)) {
            percolated = true;
        }
    }

    /**
     * Updates connection of a grid at (row, col) and (neighborRow, neighborCol),
     * and updates full conditions of them
     */
    private void updateConnection(int row, int col, int neighborRow, int neighborCol) {
        int indexIn1D = xyTo1D(row, col);
        if (!isInvalidIndex(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
            int id = connectedGrids.find(indexIn1D);
            int neighborIndexIn1D = xyTo1D(neighborRow, neighborCol);
            int oldId = connectedGrids.find(neighborIndexIn1D);
            connectedGrids.union(indexIn1D, neighborIndexIn1D);
            int newId = connectedGrids.find(neighborIndexIn1D);

            // Update full from top conditions
            if (topFullIDs.contains(id)) {
                topFullIDs.remove(id);
                topFullIDs.add(newId);
            }
            if (topFullIDs.contains(oldId)) {
                topFullIDs.remove(oldId);
                topFullIDs.add(newId);
            }

            //update full from bottom conditions
            if (bottomFullIDs.contains(id)) {
                bottomFullIDs.remove(id);
                bottomFullIDs.add(newId);
            }
            if (bottomFullIDs.contains(oldId)) {
                bottomFullIDs.remove(oldId);
                bottomFullIDs.add(newId);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid argument given: row = " + row + "col = " + col + "."
            );
        }
        int indexIn1D = xyTo1D(row, col);
        return openGridIn1D[indexIn1D];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IndexOutOfBoundsException {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid argument given: row = " + row + "col = " + col + "."
            );
        }

        int indexIn1D = xyTo1D(row, col);
        return topFullIDs.contains(connectedGrids.find(indexIn1D));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
