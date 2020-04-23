package se.kth.graph;
import java.util.ArrayList;
/*
    Generates a graph of n nodes, and then randomly assign n edges to the nodes
    Calculates the number of components in the graph using DFS
    Calculates the largest component
* @author Kristopher Werlinder
* @version 2019-02-12
 */
public class RandomGraphGenerator {
    static ArrayList<Integer> componentSizes = new ArrayList<>();
    static int compontentSize;

    public static void main(String[] args) {
        // ALT+SHIFT+F10, Right, "Edit", Enter, Tab, enter your command line parameters, Press Enter.

        //AVERAGE TIME - EMPIRICAL ANALYSIS
        long avrTimeCost = 0;
        long testRepitions = 100;

        for(int n = 0; n < testRepitions; n++) {
            boolean matrixG = true;
            int size = 0;
            Graph g = null;

            if (args.length == 1) { // Default.
                size = Integer.parseInt(args[0]);
                g = new MatrixGraph(size);
            }
            else if (args.length == 2) {
                size = Integer.parseInt(args[0]);
                if(args[1].equals("matrix")) {
                    g = new MatrixGraph(size);
                }
                else {
                    g = new HashGraph(size);
                    matrixG = false;
                }
            }
            else if (args.length > 2) {
                System.out.println("Too many arguments have been provided, program can only handle two. These first two arguments were executed below: ");
                size = Integer.parseInt(args[0]);
                if(args[1].equals("matrix")) {
                    g = new MatrixGraph(size);
                }
                else {
                    g = new HashGraph(size);
                    matrixG = false;
                }
            }
            else {
                System.out.println("No arguments have been provided. Provide <size> <graph_type>");
            }

            // Assign n random edges to the n vertices.
            for (int i = 0; i < size; i++) {
                int randomInt = (int) (Math.random() * size);
                int randomInt2 = (int) (Math.random() * size);
                if (g != null) {
                    g.addBi(randomInt, randomInt2);
                }
            }

            // %s is a command for toString() method on a given vertex "v" in graph "g".
            //System.out.printf("A graph: %s%n%n", g);
            System.out.println();
            System.out.println("For a " + (matrixG == true ? "proximity matrix graph" : "hash graph") + " with " + size + " nodes and " + size + " randomly assigned edges: ");
            //System.out.printf("%n%s%n", "Its components:");

            long t0 = System.nanoTime();
            int componentCount = printComponents(g);
            long t1 = System.nanoTime();
            long timecost = t1 - t0;
            avrTimeCost += timecost;

            System.out.println(" * Number of components: " + componentCount);
            int max = componentSizes.get(0);
            for(int i : componentSizes) {
                if(i > max) {
                    max = i;
                }
            }
            System.out.println(" * Largest component: " + max);
        }
        avrTimeCost = avrTimeCost / testRepitions;
        System.out.println(" * Running time: " + avrTimeCost + " ns");
    }



    /**
     * Prints the components of g to stdout. Each component is written on a
     * separate line.
     */
    private static int printComponents(Graph g) {
        VertexAction printVertex = new VertexAction() {
            @Override
            public void act(Graph g, int v) {
                //System.out.print(v + " ");
                compontentSize++;
            }
        };
        int componentCount = 0;
        int n = g.numVertices();
        boolean[] visited = new boolean[n];

        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                dfs(g, v, visited, printVertex);
                componentSizes.add(compontentSize);
                compontentSize = 0;
                componentCount++;
            }
        }
        return componentCount;
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
