package eg.edu.alexu.csd.filestructure.test.hash;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.hash.*;

public class ChainingIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IHashChaining.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IHashChaining instance = (IHashChaining)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
