package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.ILinkedBased;

public class LinkedQueueIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return ILinkedBased.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IQueue instance = (IQueue)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
