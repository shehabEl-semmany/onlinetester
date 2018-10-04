package eg.edu.alexu.csd.datastructure.test.stack;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IExpressionEvaluator;

public class EvaluatorSanityTest {

    public static Class<?> getSpecifications(){
        return IExpressionEvaluator.class;
    }

    private void infixToPostfix(String infix, String postfix){
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        assertEquals(postfix, instance.infixToPostfix(infix));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInfixToPostfixNumeric() {
        infixToPostfix("2 + 3 * 4", "2 3 4 * +");
        infixToPostfix("(1 + 2) * 7", "1 2 + 7 *");
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInfixToPostfixSymbolic() {
        infixToPostfix("a * b / c", "a b * c /");
        infixToPostfix("a * b + 5", "a b * 5 +");
        infixToPostfix("(a / (b - c + d)) * (e - a) * c", "a b c - d + / e a - * c *");
        infixToPostfix("a / b - c + d * e - a * c", "a b / c - d e * + a c * -");
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEvaluateNumeric() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        int result = instance.evaluate("16 2 / 3 14 2 * + -");
        assertEquals(-23, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInfixPostFixEvaluateNumeric() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        int result = instance.evaluate(instance.infixToPostfix("((5+3)*2/3)*3-4*2-16/2"));
        assertEquals(0, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEvaluateNumericWithTruncate() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        int result = instance.evaluate("26 3 / 3 14 + 2 / -");
        assertEquals(0, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEvaluateSymbolic() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        try {
            instance.evaluate("a b / c - d e * + a c * -");
            fail("Evalutator evaluated a symbolic expression!");
        } catch (Exception e) {
        }
    }
}
