package eg.edu.alexu.csd.datastructure.test.mailServer;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.mailServer.IPriorityQueue;

public class PriorityQueueSanityTest {

    public static Class<?> getSpecifications() {
        return IPriorityQueue.class;
    }

    private IPriorityQueue queue;

    @org.junit.Before
    public void init() {
        queue = (IPriorityQueue) TestRunner.getImplementationInstance();
    }

    @org.junit.Test(timeout = 20000)
    public void testEnqueueDeueueLarge() {
        for (int i = 1; i <= 10000000; i++)
            queue.insert(i, i);
        for (int i = 1; i <= 10000000; i++)
            assertEquals(i, queue.removeMin());
    }

    @org.junit.Test(timeout = 20000)
    public void testSize() {
        for (int i = 0; i <= 100000; i++) {
            assertEquals(i, queue.size());
            queue.insert(i, i + 1);
            assertEquals(i + 1, queue.size());
        }
        for (int i = 100000; i >= 0; i--) {
            assertEquals(i + 1, queue.size());
            assertEquals(100000 - i, queue.removeMin());
            assertEquals(i, queue.size());
        }
    }
    
}
