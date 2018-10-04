package eg.edu.alexu.csd.datastructure.test.stack;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IExpressionEvaluator;

public class EvaluatorIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IExpressionEvaluator.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
