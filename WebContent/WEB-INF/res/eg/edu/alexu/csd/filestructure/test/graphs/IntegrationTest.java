package eg.edu.alexu.csd.filestructure.test.graphs;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IGraph.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IGraph instance = (IGraph)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
