package se.kth.graph;

import org.junit.Test;
import static org.junit.Assert.fail;

/**
* @author Kristopher Werlinder
* @version 2019-02-12
 */
public class MatrixGraphTest extends GraphTest {
    @Override
    protected Graph getEmptyGraph(int numVertices) {
        return new MatrixGraph(numVertices);
    }

    @Test
    public void testConstructor() {
        try {
            new MatrixGraph(-1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
}
