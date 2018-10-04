package eg.edu.alexu.csd.datastructure.test.hangman;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.hangman.IHangman;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IHangman.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IHangman instance = (IHangman)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
