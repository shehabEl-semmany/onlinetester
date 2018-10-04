package eg.edu.alexu.csd.datastructure.test.maze;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.maze.IMazeSolver;

public class SanityTest {
    
    private final String testDir = TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/datastructure/test/maze/res/";

    public static Class<?> getSpecifications(){
        return IMazeSolver.class;
    }
    
    private String printPath(int[][] path){
        StringBuffer str = new StringBuffer();
        String separator = "";
        for(int[] point : path){
            str.append(separator).append(Arrays.toString(point));
            separator = "->";           
        }
        return str.toString();
    }

    private String printPoint(int[] point) {
        return "{" + point[0] + ", " + point[1] + "}";
    }
    
    @org.junit.Test(timeout = 1000)
    public void testDFS() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        int[][] result = instance.solveDFS(new File(testDir + "maze.txt"));
        int[][] solution = new int[][] {
                                              {0, 4}, 
                                              {1, 4}, 
                                              {2, 4}, 
                                              {3, 4}, 
                                        {3, 3}, 
                                        {4, 3}, 
                                  {4, 2}, 
                            {4, 1}, 
                            {3, 1},
                      {3, 0},
                    };
        assertEquals("Invalid Path Length", solution.length, result.length);
        for(int i=0; i<result.length; i++){
            assertEquals("Incorrect Point " + i + " in the Path (solution=" + printPoint(solution[i]) + " | result=" + printPoint(result[i]) + ")", solution[i][0], result[i][0]);
            assertEquals("Incorrect Point " + i + " in the Path (solution=" + printPoint(solution[i]) + " | result=" + printPoint(result[i]) + ")", solution[i][1], result[i][1]);
        }   
    }

    @org.junit.Test(timeout = 1000)
    public void testBFS() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        int[][] result = instance.solveBFS(new File(testDir + "maze.txt"));
        int[][] solution = new int[][] {
                                              {0, 4},
                                           {0, 3},
                                       {0, 2},
                                    {0, 1},
                                    {1, 1},
                                 {1, 0},
                                 {2, 0},
                                 {3, 0},
        };
        assertEquals("Invalid Path Length", solution.length, result.length);
        for(int i=0; i<result.length; i++){
            assertEquals("Incorrect Point " + i + " in the Path (solution=" + printPoint(solution[i]) + " | result=" + printPoint(result[i]) + ")", solution[i][0], result[i][0]);
            assertEquals("Incorrect Point " + i + " in the Path (solution=" + printPoint(solution[i]) + " | result=" + printPoint(result[i]) + ")", solution[i][1], result[i][1]);
        }   
    }

}
