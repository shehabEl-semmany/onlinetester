package eg.edu.alexu.csd.filestructure.test.sort;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.sort.ISort;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return ISort.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        ISort instance = (ISort)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
