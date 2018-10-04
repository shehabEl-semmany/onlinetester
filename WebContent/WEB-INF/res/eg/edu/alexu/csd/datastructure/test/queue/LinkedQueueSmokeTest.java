package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.ILinkedBased;

public class LinkedQueueSmokeTest {

    public static Class<?> getSpecifications() {
        return ILinkedBased.class;
    }

    private IQueue queue;

    @org.junit.Before
    public void init() {
        queue = (IQueue) TestRunner.getImplementationInstance();
    }

    @org.junit.Test(timeout = 1000)
    public void testEnqueueDeueueSmall() {
        for (int i = 0; i <= 10; i++)
            queue.enqueue(i);
        for (int i = 0; i <= 10; i++)
            assertEquals(i, queue.dequeue());
    }

    @org.junit.Test(timeout = 1000)
    public void testDataTypes() {
        queue.enqueue(1);
        queue.enqueue(2.0f);
        queue.enqueue(3.0d);
        queue.enqueue(4l);
        queue.enqueue("5");
        queue.enqueue('6');
        assertTrue(queue.dequeue() instanceof Integer);
        assertTrue(queue.dequeue() instanceof Float);
        assertTrue(queue.dequeue() instanceof Double);
        assertTrue(queue.dequeue() instanceof Long);
        assertTrue(queue.dequeue() instanceof String);
        assertTrue(queue.dequeue() instanceof Character);
    }

    @org.junit.Test(timeout = 1000)
    public void testEmpty() {
        try {
            queue.dequeue();
            fail("dequeue succeeded on empty queue");
        } catch (Exception e) {
        }
        assertEquals("Wrong size", 0, queue.size());
        queue.enqueue("something");
        queue.dequeue();
        try {
            queue.dequeue();
            fail("dequeue succeeded on empty queue");
        } catch (Exception e) {
        }
        queue.enqueue("something");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
}
