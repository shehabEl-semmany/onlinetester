package eg.edu.alexu.csd.filestructure.test.hash;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.hash.*;

public class LinearProbingIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IHashLinearProbing.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IHashLinearProbing instance = (IHashLinearProbing)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
