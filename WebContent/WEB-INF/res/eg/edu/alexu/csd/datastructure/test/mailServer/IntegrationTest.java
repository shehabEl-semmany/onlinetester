package eg.edu.alexu.csd.datastructure.test.mailServer;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.mailServer.IApp;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IApp.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
    	IApp instance = (IApp)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
