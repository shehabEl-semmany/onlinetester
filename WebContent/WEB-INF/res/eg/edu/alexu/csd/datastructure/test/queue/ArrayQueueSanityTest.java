package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.IArrayBased;

public class ArrayQueueSanityTest {

    public static Class<?> getSpecifications() {
        return IArrayBased.class;
    }

    @org.junit.Test(timeout = 10000)
    public void testEnqueueDeueueLarge() {
        IQueue queue = (IQueue) TestRunner.getImplementationInstance(10000000);
        for (int i = 0; i < 10000000; i++)
            queue.enqueue(i);
        for (int i = 0; i < 10000000; i++)
            assertEquals(i, queue.dequeue());
    }

    @org.junit.Test(timeout = 10000)
    public void testSize() {
        IQueue queue = (IQueue) TestRunner.getImplementationInstance(100000);
        for (int i = 0; i < 100000; i++) {
            assertEquals(i, queue.size());
            queue.enqueue(i);
            assertEquals(i + 1, queue.size());
        }
        for (int i = 100000-1; i >= 0; i--) {
            assertEquals(i + 1, queue.size());
            assertEquals(100000 - 1 - i, queue.dequeue());
            assertEquals(i, queue.size());
        }
    }
    
}
