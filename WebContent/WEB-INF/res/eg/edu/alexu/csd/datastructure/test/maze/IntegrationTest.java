package eg.edu.alexu.csd.datastructure.test.maze;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.maze.IMazeSolver;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IMazeSolver.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IMazeSolver instance = (IMazeSolver)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
