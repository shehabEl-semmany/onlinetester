package eg.edu.alexu.csd.datastructure.test.stack;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IStack;

public class StackIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IStack.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IStack instance = (IStack)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
