package eg.edu.alexu.csd.filestructure.test.hash;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.hash.*;

public class DoubleIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IHashDouble.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IHashDouble instance = (IHashDouble)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
