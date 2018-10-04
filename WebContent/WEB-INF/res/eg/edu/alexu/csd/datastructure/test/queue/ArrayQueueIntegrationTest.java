package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.IArrayBased;

public class ArrayQueueIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IArrayBased.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IQueue instance = (IQueue)TestRunner.getImplementationInstance(10);
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
