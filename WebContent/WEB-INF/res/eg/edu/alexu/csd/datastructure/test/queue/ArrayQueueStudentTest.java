package eg.edu.alexu.csd.datastructure.test.queue;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.queue.IQueue;
import eg.edu.alexu.csd.datastructure.queue.IArrayBased;

public class ArrayQueueStudentTest {

    public static Class<?> getSpecifications() {
        return IArrayBased.class;
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test1Size() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(1);
        l.enqueue(5);
        assertEquals(1, l.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test3SetandRemove() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(1);
        l.enqueue(5);
        assertEquals(5, l.dequeue());
        l.enqueue(6);
        assertEquals(6, l.dequeue());
        l.enqueue(7);
        assertEquals(7, l.dequeue());
        assertEquals(0, l.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test4EmptyQueue() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(1);
        l.enqueue(5);
        assertEquals(5, l.dequeue());

        try {
            assertEquals(6, l.dequeue());
            fail("Queue Is Empty! You should not be able to dequeue");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test5FullQueue() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(1);
        l.enqueue(5);
        assertEquals(1, l.size());
        try {
            l.enqueue(8);
            fail("Queue Is Full! You should not be able to enqueue");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test6EmptyQueueMethod() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(1);
        l.enqueue(5);
        assertEquals(1, l.size());
        assertEquals(5, l.dequeue());
        assertEquals(0, l.size());
        assertTrue(l.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test7FullQueueAgain() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(5);
        l.enqueue(1);
        l.enqueue(2);
        l.enqueue(3);
        l.enqueue(4);
        l.enqueue(5);
        assertEquals(5, l.size());
        assertFalse(l.isEmpty());
        try {
            l.enqueue(8);
            fail("Queue Is Full! You should not be able to enqueue");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test8Circle() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(5);
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
        assertTrue(l.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs61Test9CircleAgain() {
        IQueue l = (IQueue)TestRunner.getImplementationInstance(5);
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
        l.enqueue(1);
        assertEquals(2, l.size());
        l.enqueue(2);
        l.enqueue(3);
        l.enqueue(4);
        assertEquals(5, l.size());
        assertFalse(l.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs57TestDeQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
        assertEquals(4, q.dequeue());
        assertEquals(5, q.dequeue());
    }
    
    @org.junit.Test(timeout = 20000)
    public void cs57TestManyDE_ENQueue() {
        try {
            IQueue q = (IQueue)TestRunner.getImplementationInstance(1);
            for(int i=0 ; i<1000000000 ; i++){
                q.enqueue(1);
                assertEquals(1, q.dequeue());
            }
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs57TestEmptyExceptionQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
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
    public void cs57TestFullQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        try {
            q.enqueue(6);
            fail("enqueue succeeded on full queue");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs57TestSizeQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
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
    public void cs57TestEmptyQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
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
    public void cs57TestCircularQueue() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
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
    
    @org.junit.Test(timeout = 1000)
    public void cs57TestCircularQueueTwice() {
        IQueue q = (IQueue)TestRunner.getImplementationInstance(5);
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
}
