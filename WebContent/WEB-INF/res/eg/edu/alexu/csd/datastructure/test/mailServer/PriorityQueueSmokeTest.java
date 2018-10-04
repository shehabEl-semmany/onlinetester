package eg.edu.alexu.csd.datastructure.test.mailServer;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.mailServer.IPriorityQueue;

public class PriorityQueueSmokeTest {

    public static Class<?> getSpecifications() {
        return IPriorityQueue.class;
    }

    private IPriorityQueue queue;

    @org.junit.Before
    public void init() {
        queue = (IPriorityQueue) TestRunner.getImplementationInstance();
    }

    @org.junit.Test(timeout = 1000)
    public void testKey() {
        try {
        	queue.insert(55, -5);
            fail("Key should be from 1 to max integer");
        } catch (Exception e) {
        }
        
        try {
        	queue.insert(55, 0);
            fail("Key should be from 1 to max integer");
        } catch (Exception e) {
        }
        
        try {
        	queue.insert(55, Integer.MAX_VALUE);
        } catch (Exception e) {
        	fail("Key should be from 1 to max integer");
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInsertRemoveSmall() {
        for (int i = 1; i <= 10; i++)
            queue.insert(i, i);
        for (int i = 1; i <= 10; i++)
            assertEquals(i, queue.removeMin());
    }
    
    @org.junit.Test(timeout = 1000)
    public void testRemoveMin() {
        queue.insert("test1", 5);
        queue.insert("test2", 2);
        queue.insert("test3", 1);
        queue.insert("test4", 4);
        
        assertEquals("test3", queue.removeMin());
        assertEquals("test2", queue.removeMin());
        assertEquals("test4", queue.removeMin());
        assertEquals("test1", queue.removeMin());
        assertEquals("Wrong size", 0, queue.size());
    }

    @org.junit.Test(timeout = 1000)
    public void testMin() {
        queue.insert("test1", 5);
        queue.insert("test2", 2);
        queue.insert("test3", 1);
        queue.insert("test4", 2);
        
        assertEquals("test3", queue.min());
        assertEquals("test3", queue.removeMin());
        assertEquals("test2", queue.removeMin());
        assertEquals("test4", queue.min());
        assertEquals("test4", queue.removeMin());
        assertEquals("test1", queue.min());
        assertEquals("min should not remove the element!", 1, queue.size());
        assertEquals("test1", queue.removeMin());
        assertEquals("Wrong size", 0, queue.size());
    }
    
    @org.junit.Test(timeout = 1000)
    public void testDataTypes() {
        queue.insert(1, 1);
        queue.insert(2.0f, 2);
        queue.insert(3.0d, 3);
        queue.insert(4l, 4);
        queue.insert("5", 5);
        queue.insert('6', 6);
        assertTrue(queue.removeMin() instanceof Integer);
        assertTrue(queue.removeMin() instanceof Float);
        assertTrue(queue.removeMin() instanceof Double);
        assertTrue(queue.removeMin() instanceof Long);
        assertTrue(queue.removeMin() instanceof String);
        assertTrue(queue.removeMin() instanceof Character);
    }

    @org.junit.Test(timeout = 1000)
    public void testEmpty() {
        try {
            queue.removeMin();
            fail("removeMin succeeded on empty queue");
        } catch (Exception e) {
        }
        
        try {
            queue.min();
            fail("min succeeded on empty queue");
        } catch (Exception e) {
        }
        
        assertEquals("Wrong size", 0, queue.size());
        queue.insert("something", 5);
        queue.removeMin();
        try {
            queue.removeMin();
            fail("removeMin succeeded on empty queue");
        } catch (Exception e) {
        }
        queue.insert("something", 3);
        assertFalse(queue.isEmpty());
        queue.removeMin();
        assertTrue(queue.isEmpty());
    }
}
