package eg.edu.alexu.csd.oop.test.calculator;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.oop.calculator.Calculator;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return Calculator.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        Calculator instance = (Calculator)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
