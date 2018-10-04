package eg.edu.alexu.csd.datastructure.test.linkedList;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

public class ListIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return ILinkedList.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
