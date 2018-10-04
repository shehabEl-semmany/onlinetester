package eg.edu.alexu.csd.datastructure.test.calculator;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.calculator.ICalculator;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return ICalculator.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        ICalculator instance = (ICalculator)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
