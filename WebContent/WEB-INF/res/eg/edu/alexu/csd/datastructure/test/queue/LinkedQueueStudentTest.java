package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.ILinkedBased;

public class LinkedQueueStudentTest {

    public static Class<?> getSpecifications() {
        return ILinkedBased.class;
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test10SizeList() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance();
        l.enqueue(5);
        assertEquals(1, l.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test12SetandRemoveList() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance();
        l.enqueue(5);
        assertEquals(5, l.dequeue());
        l.enqueue(6);
        assertEquals(6, l.dequeue());
        l.enqueue(7);
        assertEquals(7, l.dequeue());
        assertEquals(0, l.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test13EmptyQueueList() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance();
        l.enqueue(5);
        assertEquals(5, l.dequeue());

        try {
            assertEquals(6, l.dequeue());
            fail("Queue Is Empty! You should not be able to dequeue");
        } catch (Exception e) {

        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test14EmptyQueueMethodList() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance();
        l.enqueue(5);
        assertEquals(1, l.size());
        assertEquals(5, l.dequeue());
        assertEquals(0, l.size());
        assertTrue(l.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test15List() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance();
        l.enqueue(1);
        l.enqueue(2);
        l.enqueue(3);
        l.enqueue(4);
        l.enqueue(5);
        assertEquals(5, l.size());
        assertFalse(l.isEmpty());
        assertEquals(1, l.dequeue());
        assertEquals(4, l.size());
        assertEquals(2, l.dequeue());
        assertEquals(3, l.size());
        assertEquals(3, l.dequeue());
        assertEquals(2, l.size());
        assertEquals(4, l.dequeue());
        assertEquals(1, l.size());
        assertEquals(5, l.dequeue());
        assertEquals(0, l.size());
        assertTrue(l.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs57DeQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(1, q.dequeue());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.dequeue());
            assertEquals(4, q.dequeue());
            assertEquals(5, q.dequeue());
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 20000)
    public void cs57ManyDE_ENQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        try {
            for(int i=0 ; i<1000000000 ; i++){
                q.enqueue(1);
                assertEquals(1, q.dequeue());
            }
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs57CircularQueueTwice() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(5, q.size());
            assertEquals(1, q.dequeue());
            assertEquals(4, q.size());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.size());
            assertEquals(3, q.dequeue());
            assertEquals(2, q.size());
            q.enqueue(6);
            assertEquals(3, q.size());
            q.enqueue(7);
            assertEquals(4, q.size());
            assertEquals(4, q.dequeue());
            assertEquals(3, q.size());
            assertEquals(5, q.dequeue());
            assertEquals(2, q.size());
            assertEquals(6, q.dequeue());
            assertEquals(1, q.size());
            assertEquals(7, q.dequeue());
            assertEquals(0, q.size());
            q.enqueue(1);
            q.enqueue(2);
            q.enqueue(3);
            q.enqueue(4);
            q.enqueue(5);
            for(int i=1 ; i<=5 ;i++){
                assertEquals(i, q.dequeue());
            }
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs57EmptyExceptionQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(1, q.dequeue());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.dequeue());
            assertEquals(4, q.dequeue());
            assertEquals(5, q.dequeue());
            q.dequeue();
            fail("dequeue succeeded on empty queue");
        } catch (Exception e) {
        }
    }
        
    @org.junit.Test(timeout = 1000)
    public void cs57SizeQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(1, q.dequeue());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.dequeue());
            q.enqueue(6);
            q.enqueue(7);
            assertEquals(4, q.dequeue());
            assertEquals(5, q.dequeue());
            assertEquals(6, q.dequeue());
            assertEquals(7, q.dequeue());
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs57EmptyQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(1, q.dequeue());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.dequeue());
            assertFalse(q.isEmpty());
            assertEquals(4, q.dequeue());
            assertEquals(5, q.dequeue());
            assertTrue(q.isEmpty());
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs57CircularQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            assertEquals(5, q.size());
            assertEquals(1, q.dequeue());
            assertEquals(4, q.size());
            assertEquals(2, q.dequeue());
            assertEquals(3, q.size());
            assertEquals(3, q.dequeue());
            assertEquals(2, q.size());
            q.enqueue(6);
            assertEquals(3, q.size());
            q.enqueue(7);
            assertEquals(4, q.size());
            assertEquals(4, q.dequeue());
            assertEquals(3, q.size());
            assertEquals(5, q.dequeue());
            assertEquals(2, q.size());
            assertEquals(6, q.dequeue());
            assertEquals(1, q.size());
            assertEquals(7, q.dequeue());
            assertEquals(0, q.size());
        } catch (Exception e) {
        }
    }
}
