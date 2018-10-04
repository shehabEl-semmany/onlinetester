package eg.edu.alexu.csd.filestructure.test.hash;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.hash.*;

public class QuadraticProbingIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IHashQuadraticProbing.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IHashQuadraticProbing instance = (IHashQuadraticProbing)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
