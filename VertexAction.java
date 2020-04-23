package se.kth.graph;

/**
 * This interface contains a single act method intended to be called during a
 * traversal of a graph.
 *
 * @author Kristopher Werlinder
 * @version 2019-02-12
 */

public interface VertexAction {
    /**
     * An action performed when a search of the graph g visits node v.
     *
     * @param g
     *            a graph
     * @param v
     *            a vertex in the graph
     */
    void act(Graph g, int v);
}
