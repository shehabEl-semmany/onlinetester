package eg.edu.alexu.csd.filestructure.test.graphs;

import static org.junit.Assert.*;

import java.io.File;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class SmokeTest {

    String[] testGraphs = {"graph_5_5.txt", "graph_50_25.txt", "graph_500_250.txt", "graph_1250_625.txt"};
    int[] testGraphsSizes = new int[] { 11, 1045, 26217, 144583};
    int[] testGraphsVertices = new int[] { 5, 50, 500, 1250};
    int[] testGraphsNeighbors = new int[] { 3, 24, 76, 125};
    String testDir = TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/graphs/res/";
    
    public static Class<?> getSpecifications(){
        return IGraph.class;
    }
    
    @org.junit.Test(timeout = 2000)
    public void testInvalidGraph() {
        IGraph graph = (IGraph)TestRunner.getImplementationInstance();
        try {
            graph.readGraph(new File("" + System.currentTimeMillis()));
            fail("Accepted reading non existing file");
        } catch (Exception e) {
        }
        try {
            graph.readGraph(new File(testDir + "graph_incomplete.txt"));
            fail("Accepted invalid file with incomplete graph information");
        } catch (Exception e) {
        }
        try {
            graph.readGraph(new File(testDir + "graph_extra.txt"));
            fail("Accepted invalid file with inconsistent dimensions/edges");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 2000)
    public void testGraphSize() {
        for(int i=0; i<testGraphs.length; i++){
            IGraph graph = (IGraph)TestRunner.getImplementationInstance();
            graph.readGraph(new File(testDir + testGraphs[i]));
            assertEquals("Wrong graph size", testGraphsSizes[i], graph.size());
        }
    }
    
    @org.junit.Test(timeout = 2000)
    public void testGraphVertices() {
        for(int i=0; i<testGraphs.length; i++){
            IGraph graph = (IGraph)TestRunner.getImplementationInstance();
            graph.readGraph(new File(testDir + testGraphs[i]));
            assertEquals("Wrong vertices count", testGraphsVertices[i], graph.getVertices().size());
        }
    }
    
    @org.junit.Test(timeout = 2000)
    public void testGraphNeighbors() {
        for(int i=0; i<testGraphs.length; i++){
            IGraph graph = (IGraph)TestRunner.getImplementationInstance();
            graph.readGraph(new File(testDir + testGraphs[i]));
            assertEquals("Wrong vertices count", testGraphsNeighbors[i], graph.getNeighbors(0).size());
        }
    }
}
