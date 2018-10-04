package eg.edu.alexu.csd.datastructure.test.maze;

import static org.junit.Assert.*;

import java.io.File;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.maze.IMazeSolver;

public class SmokeTest {
    
    private final String testDir = TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/datastructure/test/maze/res/";
    
    public static Class<?> getSpecifications(){
        return IMazeSolver.class;
    }

    @org.junit.Test(timeout = 1000)
    public void testNotExistingFile() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File("nonexistingfile" + System.currentTimeMillis()));
            fail("BFS: processing a not existing file");
        } catch (Exception e) {
        }
        try {
            instance.solveDFS(new File("nonexistingfile" + System.currentTimeMillis()));
            fail("DFS: processing a not existing file");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testInvalidFile() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File(testDir + "invalid.txt"));
            fail("BFS: processing an invalid file");
        } catch (Exception e) {
        }
        try {
            instance.solveDFS(new File(testDir + "invalid.txt"));
            fail("DFS: processing an invalid file");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testValid() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File(testDir + "valid.txt"));
        } catch (Exception e) {
            fail("BFS: failed to process a valid file");
        }
        try {
            instance.solveDFS(new File(testDir + "valid.txt"));
        } catch (Exception e) {
            fail("DFS: failed to process a valid file");
        }   
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidDimentions() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File(testDir + "invalidDim.txt"));
            fail("BFS: processing an invalid dimensions file");
        } catch (Exception e) {
        }
        try {
            instance.solveDFS(new File(testDir + "invalidDim.txt"));
            fail("DFS: processing an invalid dimensions file");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidNoEntry() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File(testDir + "invalidNoEntry.txt"));
            fail("BFS: processing an invalid file with no entry point");
        } catch (Exception e) {
        }
        try {
            instance.solveDFS(new File(testDir + "invalidNoEntry.txt"));
            fail("DFS: processing an invalid file with no entry point");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidNoExit() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        try {
            instance.solveBFS(new File(testDir + "invalidNoExit.txt"));
            fail("BFS: processing an invalid file with no exit point");
        } catch (Exception e) {
        }
        try {
            instance.solveDFS(new File(testDir + "invalidNoExit.txt"));
            fail("DFS: processing an invalid file with no exit point");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testNoPath() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        assertNull("BFS: Found a path in a file with no solution", instance.solveBFS(new File(testDir + "noPath.txt")));
        assertNull("DFS: Found a path in a file with no solution", instance.solveDFS(new File(testDir + "noPath.txt")));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testLinePath() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        assertNotNull("BFS: Failed to find a linear path", instance.solveBFS(new File(testDir + "linePath.txt")));
        assertNotNull("DFS: Failed to find a linear path", instance.solveDFS(new File(testDir + "linePath.txt")));
    }

    @org.junit.Test(timeout = 1000)
    public void testPathSnake() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        assertNotNull("BFS: Failed to find a snake path", instance.solveBFS(new File(testDir + "snakePath.txt")));
        assertNotNull("DFS: Failed to find a snake path", instance.solveDFS(new File(testDir + "snakePath.txt")));
    }
}
