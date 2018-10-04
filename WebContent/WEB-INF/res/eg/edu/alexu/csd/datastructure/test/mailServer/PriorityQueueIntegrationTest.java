package eg.edu.alexu.csd.datastructure.test.mailServer;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.mailServer.IPriorityQueue;

public class PriorityQueueIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IPriorityQueue.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
    	IPriorityQueue instance = (IPriorityQueue)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
