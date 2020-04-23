package se.kth.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 *
 * @author Kristopher Werlinder
 * @version 2019-02-12
 */
public class HashGraph implements Graph {

/*
    public static void main(String args[]) {
        HashGraph directedGraph = new HashGraph(10);
        directedGraph.addEdge(0,1,101);
        directedGraph.addEdge(0,2,100);
        directedGraph.addEdge(2,3,120);
        directedGraph.addEdge(4,5,110);
        directedGraph.addEdge(6,7,150);
        System.out.println(directedGraph.toString());
    }
*/

    /**
     * The map edges[v] contains the key-value pair (w, c) if there is an edge
     * from v to w; c is the cost assigned to this edge. The maps may be null
     * and are allocated only when needed.
     */
    private final Map<Integer, Integer>[] edges;
    private final static int INITIAL_MAP_SIZE = 4;

    /** Number of edges in the graph. */
    private int numEdges;

    /**
     * Constructs a HashGraph with n vertices and no edges. Time complexity:
     * O(n)
     *
     * @throws IllegalArgumentException
     *             if n < 0
     */
    public HashGraph(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n = " + n);

        // The array will contain only Map<Integer, Integer> instances created
        // in addEdge(). This is sufficient to ensure type safety.
        @SuppressWarnings("unchecked") Map<Integer, Integer>[] a = new HashMap[n];
        edges = a;
    }

    /**
     * Add an edge without checking parameters.
     * Time complexity: O(1).
     */
    private void addEdge(int from, int to, int cost) {
        if (edges[from] == null)
            edges[from] = new HashMap<Integer, Integer>(INITIAL_MAP_SIZE);
        if (edges[from].put(to, cost) == null)
            numEdges++;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public int numVertices() {
        return edges.length;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public int numEdges() {
        return numEdges;
    }

    /**
     * {@inheritDoc Graph}  Time complexity: O(1).
     */
    @Override
    public int degree(int v) throws IllegalArgumentException {
        checkVertexParameter(v);

        if(arraySlotIsNotNull(v)) {
            return edges[v].size();
        }
        return 0;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(n), where n is the number of vertices.
     */
    /**
     * {@inheritDoc Graph}
     */
    @Override
    public VertexIterator neighbors(int v) {
        checkVertexParameter(v);
        return new NeighborIterator(v);
    }

    private class NeighborIterator implements VertexIterator {
        int size;
        int nextPos = -1;
        Integer[] row;
        final int n;

        NeighborIterator(int v) {
            if(edges[v] == null) {
                n = -1;
                size = 0;
                row = new Integer[0];
            }
            else {
                n = edges[v].size();
                size = n;
                row = edges[v].keySet().toArray(new Integer[size]);
            }
            findNext();
        }

        private void findNext() {
            if (nextPos == -1) {
                nextPos = 0;
                return;
            }
            nextPos ++;
        }

        @Override
        public boolean hasNext() {
            return nextPos < size;
        }

        @Override
        public int next() {
            int pos = nextPos;
            if (pos < size) {
                findNext();
                return row[pos];
            }
            throw new NoSuchElementException("No more elements in this iterator.");
        }
    }


    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public boolean hasEdge(int from, int to) throws IllegalArgumentException {
        checkVertexParameters(from, to);

        if (arraySlotIsNotNull(from))
            return edges[from].containsKey(to);
        return false;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public int cost(int from, int to) throws IllegalArgumentException {
        checkVertexParameters(from, to);

        if(arraySlotIsNotNull(from)) {
            if (edges[from].get(to) != null) {
                return edges[from].get(to);
            }
        }
        return NO_COST;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void add(int from, int to) throws IllegalArgumentException {
        checkVertexParameters(from, to);

        addEdge(from, to, NO_COST);
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void add(int from, int to, int c) throws IllegalArgumentException {
        checkVertexParameters(from, to);
        checkNonNegativeCost(c);

        addEdge(from, to, c);
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void addBi(int v, int w) throws IllegalArgumentException {
        checkVertexParameters(v, w);

        addEdge(v, w, NO_COST);
        if (v == w)
            return;
        addEdge(w, v, NO_COST);
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void addBi(int v, int w, int c) throws IllegalArgumentException {
        checkNonNegativeCost(c);
        checkVertexParameters(v, w);

        addEdge(v, w, c);
        if (v == w)
            return;
        addEdge(w, v, c);
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void remove(int from, int to) throws IllegalArgumentException {
        checkVertexParameters(from, to);

        removeEdge(from, to);
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public void removeBi(int v, int w) throws IllegalArgumentException {
        checkVertexParameters(v, w);

        removeEdge(v, w);
        if (v == w)
            return;
        removeEdge(w, v);
    }

    /**
     * Returns a string representation of this graph.
     *
     * Should represent the graph in terms of edges (order does not matter).
     * For example, if a graph has edges (2,3) and (1,0) with NO_COST, the
     * representaiton should be:
     *
     * "{(1,0), (2,3)}" or "{(2,3), (1,0)}"
     *
     * If an edge has a cost (say, 5), it is added as a third value, like so:
     *
     * "{(1,0,5)}"
     *
     * An empty graph is just braces:
     *
     * "{}"
     *
     * Time complexity: O(n^2).
     *
     * @return a String representation of this graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int from = 0; from < numVertices(); from++) {
            if(arraySlotIsNotNull(from)) {
                for(Integer key : edges[from].keySet()) {
                    int to = key;
                    int value = edges[from].get(key);
                    switch (value) {
                        case NO_COST:
                            sb.append("(" + from + "," + to + "), ");
                            break;
                        default:
                            sb.append("(" + from + "," + to + "," + value + "), ");
                    }
                }
            }

        }
        if (numEdges > 0)
            sb.setLength(sb.length() - 2); // Remove trailing ", "
        sb.append("}");
        return sb.toString();
    }



/*HELPER METHODS:*/

    /**
     * Checks a single vertex parameter v.
     * Time complexity: O(1).
     *
     * @throws IllegalArgumentException
     *             if v is out of range
     */
    private void checkVertexParameter(int v) {
        if (v < 0 || v >= numVertices())
            throw new IllegalArgumentException("Out of range: v = " + v + ".");
    }


    /**
     * Checks two vertex parameters v and w.
     * Time complexity: O(1).
     *
     * @throws IllegalArgumentException
     *             if v or w is out of range
     */
    private void checkVertexParameters(int v, int w) {
        if (v < 0 || v >= numVertices() || w < 0 || w >= numVertices())
            throw new IllegalArgumentException("Out of range: v = " + v + ", w = " + w + ".");
    }


    /**
     * Checks a single vertex parameter v.
     * Time complexity: O(1).
     *
     * @return true if array slot is not empty.
     */
    private boolean arraySlotIsNotNull(int v) {
        if(edges[v] == null) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the cost c is non-negative.
     * Time complexity: O(1).
     *
     * @throws IllegalArgumentException
     *             if c < 0
     */
    private void checkNonNegativeCost(int c) {
        if (c < 0)
            throw new IllegalArgumentException("Illegal cost: c = " + c + ".");
    }


    /**
     * Remove an edge without checking parameters.
     * Time complexity: O(1).
     */
    private void removeEdge(int from, int to) {
        if(arraySlotIsNotNull(from))
            if(edges[from].containsKey(to)) {
                edges[from].remove(to);
                numEdges--;
            }
    }

}
