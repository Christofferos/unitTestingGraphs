package se.kth.graph;

import java.util.NoSuchElementException;

/**
 * An iterator over vertices in a Graph object.
 *
 * @author Kristopher Werlinder
 * @version 2019-02-12
 */
// DO NOT MODIFY INTERFACE
public interface VertexIterator {
    /**
     * Returns true if the iteration has more elements.
     *
     * @return true if the iteration has more elements
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException
     *             if the iteration has no more elements
     */
    int next() throws NoSuchElementException;
}
