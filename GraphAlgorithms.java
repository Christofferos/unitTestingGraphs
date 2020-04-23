package se.kth.graph;
import javafx.beans.binding.IntegerBinding;
import java.util.*;

/**
 * An example implementation of depth first search.
 *
 * @author Kristopher Werlinder
 * @version 2019-02-12
 */
public class GraphAlgorithms {
    /**
     * Builds an undirected graph and prints the components to stdout.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args) {

        // ALT+SHIFT+F10, Right, "Edit", Enter, Tab, enter your command line parameters, Enter.
        int size = 0;
        Graph g = null;

        if (args.length == 1) {
            size = Integer.parseInt(args[0]);
            g = new MatrixGraph(size);
        }
        else if (args.length > 1) {
            System.out.println("Too many arguments have been provided, only the first argument was executed");
            size = Integer.parseInt(args[0]);
            g = new MatrixGraph(size);
        }
        else {
            System.out.println("No argument has been provided");
        }

        // Assign n random edges to the n vertices.
        for(int i = 0; i < size; i++) {
            int randomInt = (int)(Math.random() * size);
            int randomInt2 = (int)(Math.random() * size);
            if(g != null) {
                g.add(randomInt, randomInt2);
            }
        }

        // %s is a command for toString() method on a given vertex "v" in graph "g".
        System.out.printf("A graph: %s%n", g);
        System.out.printf("%n%s%n", "Its components:");
        printComponents(g);



        /* Ric wrote this code.
        int size = 8;
        Graph g = new MatrixGraph(size);
        g.addBi(0, 1); //
        g.addBi(2, 2); //   0---1   2---
        g.addBi(0, 3); //   |   |   |  |
        g.addBi(1, 4); //   3---4   ----
        g.addBi(3, 4); //   |
        g.addBi(5, 3); //   5       6---7
        g.addBi(6, 7); //

        System.out.printf("A graph: %s%n", g);
        System.out.printf("%n%s%n", "Its components:");
        printComponents(g);*/
    }

    /**
     * Prints the components of g to stdout. Each component is written on a
     * separate line.
     */
    private static void printComponents(Graph g) {
        VertexAction printVertex = new VertexAction() {

            @Override
            public void act(Graph g, int v) {
                System.out.print(v + " ");
            }

        };

        int n = g.numVertices();
        boolean[] visited = new boolean[n];

        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                dfs(g, v, visited, printVertex);
                System.out.println();
            }
        }

    }

    /**
     * Traverses the nodes of g that have not yet been visited. The nodes are
     * visited in depth-first order starting at v. The act() method in the
     * VertexAction object is called once for each node.
     *
     * @param g
     *            an undirected graph
     * @param v
     *            start vertex
     * @param visited
     *            visited[i] is true if node i has been visited
     */
    private static void dfs(Graph g, int v, boolean[] visited, VertexAction action) {
        if (visited[v])
            return;
        visited[v] = true;
        action.act(g, v);
        for (VertexIterator it = g.neighbors(v); it.hasNext();) {
            dfs(g, it.next(), visited, action);
        }
    }
}
