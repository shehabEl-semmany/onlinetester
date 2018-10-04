package eg.edu.alexu.csd.datastructure.test.iceHockey;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.iceHockey.IPlayersFinder;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IPlayersFinder.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IPlayersFinder instance = (IPlayersFinder)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
