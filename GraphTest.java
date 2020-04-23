package se.kth.graph;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Abstract test glass for graphs. Implementing test classes must override the
 * getEmptyGraph method.
 *
 * @author Kristopher Werlinder
 * @version 2019-02-12
 */
public abstract class GraphTest {
    private Graph g0;
    private Graph g1;
    private Graph g5;
    private Graph graph;

    private Graph threeBiEdgeGraph;
    private final int numVertices = 5;
    private final int cost = 42;
    private final int fromADegree = 2;
    private final int toADegree = 1;
    private final int toBDegree = 2;
    int[] oorVertices = {-1, -10, numVertices, 2 * numVertices};
    int fromA = 2;
    int toA = 4;
    int fromB = 1;
    int toB = 3;
    int fromC = 3;
    int toC = 2;

    private final int g0Edges = 0;
    private final int g0Vertices = 0;
    private final int g1Edges = 1;
    private final int g1Vertices = 1;
    private final int g5Edges = 3;
    private final int g5Vertices = 5;

    /**
     * @param numVertices The amount of vertices in the graph.
     * @return A Graph instance with no edges.
     */
    protected abstract Graph getEmptyGraph(int numVertices);

    @Before
    public void setUp() {
        g0 = getEmptyGraph(0);
        g1 = getEmptyGraph(1);
        g5 = getEmptyGraph(5);

        g1.addBi(0, 0);
        g5.addBi(0, 1);
        g5.add(2, 3, 1);

        graph = getEmptyGraph(numVertices);

        threeBiEdgeGraph = getEmptyGraph(numVertices);
        threeBiEdgeGraph.addBi(fromA, toA, cost);
        threeBiEdgeGraph.addBi(fromB, toB, cost);
        threeBiEdgeGraph.addBi(fromC, toC, cost);
    }

    /**
     * Tests for add
     */

    @Test
    public void addOnceIncreasesNumEdgesByOne() {
        // Arrange
        int expectedNumEdges = 1;

        // Act
        graph.add(2, 3);
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addFiveUniqueEdgesIncreasesnumEdgesByFive() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int expectedNumEdges = numVertices / 2;

        // Act
        for (int i = 0; i < expectedNumEdges; i++) {
            graph.add(i, i + 1);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addDuplicateEdgesDoesNotIncreasenumEdges() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int from = 0;
        int to = 1;
        graph.add(from, to);
        int expectedNumEdges = 1;

        // Act
        for (int i = 0; i < numVertices; i++) {
            graph.add(from, to);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addOnceWithCostIncreasesNumEdgesByOne() {
        // Arrange
        int numVertices = 5;
        int cost = 56;
        Graph graph = getEmptyGraph(numVertices);
        int expectedNumEdges = 1;

        // Act
        graph.add(2, 3, cost);
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addFiveUniqueEdgesWithCostIncreasesnumEdgesByFive() {
        // Arrange
        int numVertices = 10;
        int cost = 56;
        Graph graph = getEmptyGraph(numVertices);
        int expectedNumEdges = numVertices / 2;

        // Act
        for (int i = 0; i < expectedNumEdges; i++) {
            graph.add(i, i + 1, cost);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addDuplicateEdgesWithCostDoesNotIncreasenumEdges() {
        // Arrange
        int numVertices = 10;
        int cost = 24;
        Graph graph = getEmptyGraph(numVertices);
        int from = 0;
        int to = 1;
        graph.add(from, to);
        int expectedNumEdges = 1;

        // Act
        for (int i = 0; i < numVertices; i++) {
            graph.add(from, to, cost);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addWithCostExceptionWhenCostIsNegative() {
        // Arrange
        Graph graph = getEmptyGraph(10);

        for (int i = 1; i < 10; i++) {
            try {
                // Act
                graph.add(0, 1, -i);
                fail("Expected IllegalArgumentException on add with negative cost!\n"
                    + "Failed with cost: " + i);
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addExceptionWhenFromIsOutOfRange() {
        // Arrange
        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.add(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.add(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addWithCostExceptionWhenFromIsOutOfRange() {
        // Arrange
        int cost = 10;

        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.add(from, to, cost);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.add(%d, %d, %d) with %d total vertices",
                    from, to, cost, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addExceptionWhenToIsOutOfRange() {
        // Arrange

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.add(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.add(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addWithCostExceptionWhenToisOutOfRange() {
        // Arrange
        int cost = 10;

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.add(from, to, cost);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.add(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for addBi
     */

    @Test
    public void addBiOnceIncreasesNumEdgesByTwo() {
        // Arrange
        int expectedNumEdges = 2;

        // Act
        graph.addBi(2, 3);
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiFiveUniqueEdgesIncreasesnumEdgesByTen() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int addBiEdges = numVertices / 2;
        int expectedNumEdges = addBiEdges * 2;

        // Act
        for (int i = 0; i < addBiEdges; i++) {
            graph.addBi(i, i + 1);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiDuplicateEdgesDoesNotIncreasenumEdges() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int from = 0;
        int to = 1;
        graph.add(from, to);
        int expectedNumEdges = 2;

        // Act
        for (int i = 0; i < numVertices; i++) {
            graph.addBi(from, to);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiOnceWithCostIncreasesNumEdgesByOne() {
        // Arrange
        int numVertices = 5;
        int cost = 56;
        Graph graph = getEmptyGraph(numVertices);
        int expectedNumEdges = 2;

        // Act
        graph.addBi(2, 3, cost);
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiFiveUniqueEdgesWithCostIncreasesnumEdgesByTen() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int addBiEdges = numVertices / 2;
        int expectedNumEdges = addBiEdges * 2;

        // Act
        for (int i = 0; i < addBiEdges; i++) {
            graph.addBi(i, i + 1);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiDuplicateEdgesWithCostDoesNotIncreasenumEdges() {
        // Arrange
        int numVertices = 10;
        Graph graph = getEmptyGraph(numVertices);
        int from = 0;
        int to = 1;
        graph.add(from, to);
        int expectedNumEdges = 2;
        int cost = 10;

        // Act
        for (int i = 0; i < numVertices; i++) {
            graph.addBi(from, to, cost);
        }
        int actualNumEdges = graph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void addBiWithCostExceptionWhenCostIsNegative() {
        Graph graph = getEmptyGraph(10);
        for (int i = 1; i < 10; i++) {
            try {
                graph.addBi(0, 1, -i);
                fail("Expected IllegalArgumentException on add with negative cost!\n"
                    + "Failed with cost: " + i);
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addBiExceptionWhenFromIsOutOfRange() {
        // Arrange

        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.addBi(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.addBi(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addBiWithCostExceptionWhenFromIsOutOfRange() {
        // Arrange
        int cost = 10;

        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.addBi(from, to, cost);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.addBi(%d, %d, %d) with %d total vertices",
                    from, to, cost, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addBiExceptionWhenToIsOutOfRange() {
        // Arrange

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.addBi(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.addBi(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void addBiWithCostExceptionWhenToisOutOfRange() {
        // Arrange
        int cost = 10;

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.addBi(from, to, cost);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.addBi(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for add/addBi/hasEdge
     */

    @Test
    public void hasEdgeIsTrueWhenEdgeWasAddedWithAdd() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;

        graph.add(fromA, fromA);
        graph.add(fromB, toB);

        // Act
        boolean hasEdgeA = graph.hasEdge(fromA, fromA);
        boolean hasEdgeB = graph.hasEdge(fromB, toB);

        // Assert
        assertThat(hasEdgeA, equalTo(true));
        assertThat(hasEdgeB, equalTo(true));
    }

    @Test
    public void hasEdgeIsTrueWhenEdgeWasAddedWithAddBi() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;

        graph.addBi(fromA, fromA);
        graph.addBi(fromB, toB);

        // Act
        boolean hasEdgeA = graph.hasEdge(fromA, fromA);
        boolean hasEdgeB = graph.hasEdge(fromB, toB);
        boolean hasEdgeBReverse = graph.hasEdge(toB, fromB);

        // Assert
        assertThat(hasEdgeA, equalTo(true));
        assertThat(hasEdgeB, equalTo(true));
        assertThat(hasEdgeBReverse, equalTo(true));
    }

    @Test
    public void hasEdgeIsTrueWhenEdgeWasAddedWithAddWithCost() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;
        int cost = 10;

        graph.add(fromA, fromA, cost);
        graph.add(fromB, toB, cost);

        // Act
        boolean hasEdgeA = graph.hasEdge(fromA, fromA);
        boolean hasEdgeB = graph.hasEdge(fromB, toB);

        // Assert
        assertThat(hasEdgeA, equalTo(true));
        assertThat(hasEdgeB, equalTo(true));
    }

    @Test
    public void hasEdgeIsTrueWhenEdgeWasAddedWithAddBiWithCost() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;
        int cost = 10;

        graph.addBi(fromA, fromA, cost);
        graph.addBi(fromB, toB, cost);

        // Act
        boolean hasEdgeA = graph.hasEdge(fromA, fromA);
        boolean hasEdgeB = graph.hasEdge(fromB, toB);
        boolean hasEdgeBReverse = graph.hasEdge(toB, fromB);

        // Assert
        assertThat(hasEdgeA, equalTo(true));
        assertThat(hasEdgeB, equalTo(true));
        assertThat(hasEdgeBReverse, equalTo(true));
    }

    @Test
    public void hasEdgeIsFalseWhenEdgeIsNotAssigned() {
        // Arrange
        int from = 3;
        int to = 1;
        graph.add(from, to);

        // Act
        boolean hasOtherEdge = graph.hasEdge(from - 1, to);
        boolean hasReverseEdge = graph.hasEdge(to, from);

        // Assert
        assertThat(hasOtherEdge, is(false));
        assertThat(hasReverseEdge, is(false));
    }

    @Test
    public void hasEdgeExceptionWhenFromIsOutOfRange() {
        // Arrange
        int cost = 10;

        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.hasEdge(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.hasEdge(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void hasEdgeExceptionWhenToIsOutOfRange() {
        // Arrange
        int cost = 10;

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.hasEdge(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.hasEdge(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for add/addBi/cost
     */

    @Test
    public void costIsNO_COSTWhenEdgeWasAddedWithAdd() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;

        graph.add(fromA, fromA);
        graph.add(fromB, toB);

        // Act
        int costA = graph.cost(fromA, fromA);
        int costB = graph.cost(fromB, toB);

        // Assert
        assertThat(costA, equalTo(Graph.NO_COST));
        assertThat(costB, equalTo(Graph.NO_COST));
    }

    @Test
    public void costIsNO_COSTWhenEdgeIsNotAssigned() {
        // Arrange
        int from = 3;
        int to = 1;
        graph.add(from, to);

        // Act
        int otherEdgeCost = graph.cost(from - 1, to);
        int reverseEdgeCost = graph.cost(to, from);

        // Assert
        assertThat(otherEdgeCost, equalTo(Graph.NO_COST));
        assertThat(reverseEdgeCost, equalTo(Graph.NO_COST));
    }

    @Test
    public void costIsCorrectWhenEdgeWasAddedWithAddWithCost() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;
        int expectedCost = 43;

        graph.add(fromA, fromA, expectedCost / 2);
        // overwrite cost
        graph.add(fromA, fromA, expectedCost);
        graph.add(fromB, toB, expectedCost);

        // Act
        int actualCostA = graph.cost(fromA, fromA);
        int actualCostB = graph.cost(fromB, toB);

        // Assert
        assertThat(actualCostA, equalTo(expectedCost));
        assertThat(actualCostB, equalTo(expectedCost));
    }

    @Test
    public void costIsNO_COSTWhenEdgeWasAddedWithAddBi() {
        // Arrange
        int from = 3;
        int to = 1;
        graph.addBi(from, to);

        // Act
        int otherEdgeCost = graph.cost(from - 1, to);
        int reverseEdgeCost = graph.cost(to, from);

        // Assert
        assertThat(otherEdgeCost, equalTo(Graph.NO_COST));
        assertThat(reverseEdgeCost, equalTo(Graph.NO_COST));
    }

    @Test
    public void costIsCorrectWhenEdgeWasAddedWithAddBiWithCost() {
        // Arrange
        int fromA = 0;
        int fromB = 0;
        int toB = 3;
        int expectedCost = 43;

        graph.addBi(fromA, fromA, expectedCost / 2);
        // overwrite cost
        graph.addBi(fromA, fromA, expectedCost);
        graph.addBi(fromB, toB, expectedCost);

        // Act
        int actualCostA = graph.cost(fromA, fromA);
        int actualCostB = graph.cost(fromB, toB);
        int actualCostReverseB = graph.cost(toB, fromB);

        // Assert
        assertThat(actualCostA, equalTo(expectedCost));
        assertThat(actualCostB, equalTo(expectedCost));
        assertThat(actualCostReverseB, equalTo(expectedCost));
    }

    @Test
    public void costExceptionWhenFromIsOutOfRange() {
        // Arrange
        int cost = 10;

        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.cost(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.cost(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void costEdgeExceptionWhenToIsOutOfRange() {
        // Arrange
        int cost = 10;

        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.cost(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.cost(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for remove
     */

    @Test
    public void removeOnceOnEdgeInGraphDecreasesNumEdgesByOne() {
        // Arrange
        int expectedNumEdges = 5; // two bi-edges -> 6 edges
        // Act
        threeBiEdgeGraph.remove(fromA, toA);
        int actualNumEdges = threeBiEdgeGraph.numEdges();
        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeFiveTimesOnDifferentEdgesInGraphDeacreasesNumEdgesByFive() {
        // Arrange
        int expectedNumEdges = 1;

        // Act
        threeBiEdgeGraph.remove(fromA, toA);
        threeBiEdgeGraph.remove(toA, fromA);
        threeBiEdgeGraph.remove(fromB, toB);
        threeBiEdgeGraph.remove(toB, fromB);
        threeBiEdgeGraph.remove(fromC, toC);
        int actualNumEdges = threeBiEdgeGraph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeEdgeNotInGraphDoesNotAlterNumEdges() {
        // Arrange
        int expectedNumEdges = threeBiEdgeGraph.numEdges();
        // Act
        // threeBiEdgeGraph has no edges to or from 0
        threeBiEdgeGraph.remove(0, 0);
        threeBiEdgeGraph.remove(0, 1);
        threeBiEdgeGraph.remove(numVertices - 1, 0);
        int actualNumEdges = threeBiEdgeGraph.numEdges();
        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeExceptionWhenFromIsOutOfRange() {
        // Arrange
        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.remove(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.remove(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void removeExceptionWhenToIsOutOfRange() {
        // Arrange
        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.remove(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.remove(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for removeBi
     */

    @Test
    public void removeBiOnceOnEdgeInGraphDecreasesNumEdgesByTwo() {
        // Arrange
        int expectedNumEdges = 4; // two bi-edges -> 6 edges
        // Act
        threeBiEdgeGraph.removeBi(fromA, toA);
        int actualNumEdges = threeBiEdgeGraph.numEdges();
        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeBiThreeTimesOnDifferentEdgesInGraphDeacreasesNumEdgesBySix() {
        // Arrange
        int expectedNumEdges = 0;

        // Act
        threeBiEdgeGraph.removeBi(fromA, toA);
        threeBiEdgeGraph.removeBi(fromB, toB);
        threeBiEdgeGraph.removeBi(fromC, toC);
        int actualNumEdges = threeBiEdgeGraph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeBiOnEdgeNotInGraphDoesNotAlterNumEdges() {
        // Arrange
        int expectedNumEdges = 6;

        // Act
        // threeBiEdgeGraph has no vertices from or to 0
        threeBiEdgeGraph.removeBi(0, 1);
        threeBiEdgeGraph.removeBi(1, 0);
        threeBiEdgeGraph.removeBi(numVertices - 1, 0);
        int actualNumEdges = threeBiEdgeGraph.numEdges();

        // Assert
        assertThat(actualNumEdges, equalTo(expectedNumEdges));
    }

    @Test
    public void removeBiExceptionWhenFromIsOutOfRange() {
        // Arrange
        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.removeBi(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.removeBi(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void removeBiExceptionWhenToIsOutOfRange() {
        // Arrange
        int from = 0;
        for (int to : oorVertices) {
            // Act
            try {
                graph.removeBi(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.removeBi(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    /**
     * Tests for remove/removeBi/hasEdge
     */

    @Test
    public void hasEdgeFalseWhenEdgeWasRemovedWithRemove() {
        // Act
        threeBiEdgeGraph.remove(fromA, toA);
        threeBiEdgeGraph.remove(toB, fromB);
        boolean hasEdgeA = threeBiEdgeGraph.hasEdge(fromA, toA);
        boolean hasReverseEdgeB = threeBiEdgeGraph.hasEdge(toB, fromB);

        // Assert
        assertThat(hasEdgeA, is(false));
        assertThat(hasReverseEdgeB, is(false));
    }

    @Test
    public void hasEdgeFalseWhenEdgeWasRemovedWithRemoveBi() {
        // Act
        threeBiEdgeGraph.removeBi(fromA, toA);
        threeBiEdgeGraph.removeBi(fromB, toB);
        boolean hasEdgeA = threeBiEdgeGraph.hasEdge(fromA, toA);
        boolean hasReverseEdgeA = threeBiEdgeGraph.hasEdge(toA, fromA);
        boolean hasEdgeB = threeBiEdgeGraph.hasEdge(fromB, toB);
        boolean hasReverseEdgeB = threeBiEdgeGraph.hasEdge(toB, fromB);

        // Assert
        assertThat(hasEdgeA, is(false));
        assertThat(hasReverseEdgeA, is(false));
        assertThat(hasEdgeB, is(false));
        assertThat(hasReverseEdgeB, is(false));
    }

    /**
     * Tests for remove/removeBi/cost
     */

    @Test
    public void costIsNO_COSTWhenEdgeWasRemovedWithRemove() {
        // Act
        threeBiEdgeGraph.remove(fromA, toA);
        threeBiEdgeGraph.remove(toB, fromB);
        int costA = threeBiEdgeGraph.cost(fromA, toA);
        int costReverseB = threeBiEdgeGraph.cost(toB, fromB);

        // Assert
        assertThat(costA, equalTo(Graph.NO_COST));
        assertThat(costReverseB, equalTo(Graph.NO_COST));
    }

    @Test
    public void costIsNO_COSTWhenEdgeWasRemovedWithRemoveBi() {
        // Act
        threeBiEdgeGraph.removeBi(fromA, toA);
        threeBiEdgeGraph.removeBi(fromB, toB);
        int costA = threeBiEdgeGraph.cost(fromA, toA);
        int costReverseA = threeBiEdgeGraph.cost(toA, fromA);
        int costB = threeBiEdgeGraph.cost(fromB, toB);
        int costReverseB = threeBiEdgeGraph.cost(toB, fromB);

        // Assert
        assertThat(costA, equalTo(Graph.NO_COST));
        assertThat(costReverseA, equalTo(Graph.NO_COST));
        assertThat(costB, equalTo(Graph.NO_COST));
        assertThat(costReverseB, equalTo(Graph.NO_COST));
    }

    /**
     * Tests for degree and methods affecting degree
     */

    @Test
    public void degreeExceptionWhenFromIsOutOfRange() {
        // Arrange
        int to = 0;
        for (int from : oorVertices) {
            // Act
            try {
                graph.add(from, to);
                fail(String.format(
                    "Expected IllegalArgumentException on Graph.add(%d, %d) with %d total vertices",
                    from, to, numVertices));
            } catch (IllegalArgumentException e) {
                // pass
            }
        }
    }

    @Test
    public void degreeIsCorrectForVerticesWithOutEdges() {
        // Act
        int actualFromADegree = threeBiEdgeGraph.degree(fromA);
        int actualToADegree = threeBiEdgeGraph.degree(toA);
        int actualToBDegree = threeBiEdgeGraph.degree(toB);
        // Assert
        assertThat(actualFromADegree, equalTo(fromADegree));
        assertThat(actualToADegree, equalTo(toADegree));
        assertThat(actualToBDegree, equalTo(toBDegree));
    }

    @Test
    public void degreeIs0ForVerticesWithoutEdges() {
        // Act
        int actualDegree = threeBiEdgeGraph.degree(0);
        // Assert
        assertThat(actualDegree, equalTo(0));
    }

    @Test
    public void degreeIs0ForVerticesWithOnlyInEdges() {
        // Arrange
        int fromA = 0;
        int fromB = 3;
        int to = 1;
        graph.add(fromA, to);
        graph.add(fromB, to);

        // Act
        int degree = graph.degree(to);
        // Assert
        assertThat(degree, equalTo(0));
    }

    @Test
    public void degreeIs0WhenAllOutEdgesHaveBeenRemoved() {
        // Arrange
        threeBiEdgeGraph.removeBi(fromA, toA);
        threeBiEdgeGraph.removeBi(fromB, toB);
        threeBiEdgeGraph.removeBi(fromC, toC);

        // Act
        IntStream.range(0, numVertices)
            .map(i -> threeBiEdgeGraph.degree(i))
            .forEach(degree -> assertThat(degree, equalTo(0)));
    }

    /**
     * Tests for numVertices
     */

    @Test
    public void testNumVertices() {
        assertThat(g0.numVertices(), equalTo(g0Vertices));
        assertThat(g1.numVertices(), equalTo(g1Vertices));
        assertThat(g5.numVertices(), equalTo(g5Vertices));
    }

    /**
     * Tests for neighbors
     */

    /**
     * I'm sorry I was out of time here so I just took the old test that does
     * not conform to the AAA pattern.
     *
     * TODO FOR FUTURE ROUNDS: Break this test up!
     */
    @Test
    public void testNeighbors() {
        VertexIterator pi = g1.neighbors(0);
        assertTrue(pi.hasNext());
        assertEquals(0, pi.next());
        pi = g5.neighbors(0);
        assertEquals(1, pi.next());
        assertFalse(pi.hasNext());
        g5.add(0, 0);
        g5.add(0, 1);
        g5.add(0, 2);
        g5.add(0, 3);
        pi = g5.neighbors(0);
        Set<Integer> s = new HashSet<Integer>();
        for (int i = 0; i < 4; i++) {
            s.add(pi.next());
        }
        assertEquals(4, s.size());
        for (int i = 0; i < 4; i++) {
            assertTrue(s.contains(i));
        }
        assertFalse(pi.hasNext());
        try {
            pi.next();
            fail();
        } catch (NoSuchElementException e) {
        }
        pi = g5.neighbors(4);
        assertFalse(pi.hasNext());
        try {
            pi.next();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Tests for toString
     */
    @Test
    public void toStringIsBracesWhenGraphIsEmpty() {
        // Arrange
        String expected = "{}";
        // Act
        String actual = g0.toString();
        // Assert
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void toStringIsCorrectWhenGraphIsNotEmpty() {
        // Arrange
        g5.remove(0, 1);
        String g1Expected = "{(0,0)}";
        String g5CandidateA = "{(1,0), (2,3,1)}";
        String g5CandidateB = "{(2,3,1), (1,0)}";

        // Act
        String g1Actual = g1.toString();
        String g5Actual = g5.toString();

        // Assert
        assertThat(g1Actual, equalTo(g1Expected));
        assertThat(g5Actual, either(equalTo(g5CandidateA)).or(equalTo(g5CandidateB)));
    }
}
