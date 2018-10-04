package eg.edu.alexu.csd.filestructure.test.graphs;

import java.io.File;

import eg.edu.alexu.csd.TestRunner;

import eg.edu.alexu.csd.filestructure.graphs.IGraph;
import eg.edu.alexu.csd.filestructure.test.GraphUtil;

import static org.junit.Assert.*;

public class SanityTest {

    String[] testGraphs = {"graph_5_5.txt", "graph_50_25.txt", "graph_500_250.txt", "graph_1250_625.txt"};
    String testDir = TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/graphs/res/";
    String solutionDir = testDir + "solution/";
    String dijkstraOrderDir = solutionDir + "dijkstra_order/";

    public static Class<?> getSpecifications(){
        return IGraph.class;
    }
    
    private boolean testGraphBellmanFordRunner(String testCaseFileName, int src) {
        IGraph solver = (IGraph)TestRunner.getImplementationInstance();
        File inputFile = new File(testDir + testCaseFileName);
        File solutionFile = new File(solutionDir + testCaseFileName);
        int n = GraphUtil.readGraphSize(inputFile);
        int[] distances = new int[n];
        solver.readGraph(inputFile);
        solver.runBellmanFord(src, distances);
        return GraphUtil.validateShortestPath(distances, solutionFile);
    }

    private boolean testGraphDijkstraRunner(String testCaseFileName, int src, boolean testOrder) {
        IGraph solver = (IGraph)TestRunner.getImplementationInstance();
        File inputFile = new File(testDir + testCaseFileName);
        File solutionFile = new File(solutionDir + testCaseFileName);
        File orderFile = new File(dijkstraOrderDir + testCaseFileName);
        int n = GraphUtil.readGraphSize(inputFile);
        int[] distances = new int[n];
        solver.readGraph(inputFile);
        solver.runDijkstra(src, distances);
        if (!GraphUtil.validateShortestPath(distances, solutionFile))
            return false;
        if (testOrder && !GraphUtil.validateDijkstraProcessingOrder(solver.getDijkstraProcessedOrder(), orderFile))
            return false;
        return true;

    }

    @org.junit.Test(timeout = 2000)
    public void testNegativeEdges() {
        String testCaseFileName = "graph_negative_edges.txt";
        int src = 0;
        assertTrue(testGraphBellmanFordRunner(testCaseFileName, src));
    }

    @org.junit.Test(timeout = 2000)
    public void testNegativeCycle() {
        IGraph solver = (IGraph)TestRunner.getImplementationInstance();
        String testCaseFileName = "graph_negative_cycle.txt";
        File inputFile = new File(testDir + testCaseFileName);
        int n = GraphUtil.readGraphSize(inputFile);
        int[] distances = new int[n];
        int src = 0;
        solver.readGraph(inputFile);
        assertFalse(solver.runBellmanFord(src, distances));
    }

    @org.junit.Test(timeout = 2000)
    public void testDijkstra1() {
        assertTrue(testGraphDijkstraRunner(testGraphs[0], 0, true));
    }

    @org.junit.Test(timeout = 2000)
    public void testDijkstra2() {
        assertTrue(testGraphDijkstraRunner(testGraphs[1], 0, false));
    }

    @org.junit.Test(timeout = 2000)
    public void testDijkstra3() {
        assertTrue(testGraphDijkstraRunner(testGraphs[2], 0, false));
    }

    @org.junit.Test(timeout = 2000)
    public void testDijkstra4() {
        assertTrue(testGraphDijkstraRunner(testGraphs[3], 0, false));
    }

    @org.junit.Test(timeout = 7000)
    public void testBellmanFord1() {
        assertTrue(testGraphBellmanFordRunner(testGraphs[0], 0));
    }

    @org.junit.Test(timeout = 7000)
    public void testBellmanFord2() {
        assertTrue(testGraphBellmanFordRunner(testGraphs[1], 0));
    }

    @org.junit.Test(timeout = 7000)
    public void testBellmanFord3() {
        assertTrue(testGraphBellmanFordRunner(testGraphs[2], 0));
    }

    @org.junit.Test(timeout = 7000)
    public void testBellmanFord4() {
        assertTrue(testGraphBellmanFordRunner(testGraphs[3], 0));
    }

}
