package eg.edu.alexu.csd.datastructure.test.stack;

import static org.junit.Assert.*;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IExpressionEvaluator;

public class EvaluatorSmokeTest {

    public static Class<?> getSpecifications(){
        return IExpressionEvaluator.class;
    }

    @org.junit.Test(timeout = 1000)
    public void testInvalidInfixToPostfix() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        try {
            instance.infixToPostfix("");
            fail("Evaluator accepted empty postfix expression");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix(null);
            fail("Evaluator accepted null postfix expression");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("5 ++ 6");
            fail("Evaluator accepted invalid postfix expression with two operators");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("5 + -6");
            fail("Evaluator accepted postfix expression with unary operator");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("5 / 6 -");
            fail("Evaluator accepted invalid postfix expression with trainling operator");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("x ^ y ( / (5 * z) + 10");
            fail("Evaluator accepted invalid postfix expression with wrong braces");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("x ^ y / ((5 * z) + 18");
            fail("Evaluator accepted invalid postfix expression with missing braces");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 20000)
    public void testVeryLongEvaluate() {
        String expression = "1";
        int count = (int)(100000 + Math.random() * 1000);
        for(int i=0; i<count; i++)
            expression += "+1";
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        assertEquals(count + 1, instance.evaluate(instance.infixToPostfix(expression)));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidEvaluate() {
        IExpressionEvaluator instance = (IExpressionEvaluator)TestRunner.getImplementationInstance();
        try {
            instance.infixToPostfix("");
            fail("Evaluator accepted empty postfix expression");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix(null);
            fail("Evaluator accepted null postfix expression");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("5 6 ++");
            fail("Evaluator accepted invalid postfix expression with two operators");
        } catch (Exception e) {
        }
        try {
            instance.infixToPostfix("5 -6 +");
            fail("Evaluator accepted postfix expression with unary operator");
        } catch (Exception e) {
        }
    }
    
}
