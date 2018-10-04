package eg.edu.alexu.csd.datastructure.test.stack;

import org.junit.Assert;
import org.junit.Before;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IStack;

public class StackSanityTest {

    private IStack stack;
    
    public static Class<?> getSpecifications(){
        return IStack.class;
    }
    
    @Before
    public void init(){
        stack =  (IStack)TestRunner.getImplementationInstance();
    }
    
    @org.junit.Test(timeout = 1000)
    public void testPushPop() {
        for(int i=0; i<=100000; i++)
            stack.push(i);
        for(int i=100000; i>=0; i--)
            Assert.assertEquals(i, stack.pop());
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSize() {
        for(int i=0; i<=100000; i++){
            Assert.assertEquals(i, stack.size());
            stack.push(i);
            Assert.assertEquals(i+1, stack.size());
        }
        for(int i=100000; i>=0; i--){
            Assert.assertEquals(i+1, stack.size());
            Assert.assertEquals(i, stack.pop());
            Assert.assertEquals(i, stack.size());

        }
    }

    @org.junit.Test(timeout = 1000)
    public void testDataTypes() {
        stack.push(1);
        stack.push(2.0f);
        stack.push(3.0d);
        stack.push(4l);
        stack.push("5");
        stack.push('6');
        Assert.assertTrue(stack.pop() instanceof Character);
        Assert.assertTrue(stack.pop() instanceof String);
        Assert.assertTrue(stack.pop() instanceof Long);
        Assert.assertTrue(stack.pop() instanceof Double);
        Assert.assertTrue(stack.pop() instanceof Float);
        Assert.assertTrue(stack.pop() instanceof Integer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEmpty() {
        try {
            stack.pop();
            Assert.fail();
        } catch (Exception e) {
        }
        stack.push("something");
        stack.pop();
        try {
            stack.pop();
            Assert.fail();
        } catch (Exception e) {
        }
        stack.push("something");
        stack.peek();
        Assert.assertFalse(stack.isEmpty());
        stack.pop();
        Assert.assertTrue(stack.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void testAdd() {
        stack.push('A');
        stack.push('B');
        stack.push('C');
        stack.push('D');
        Assert.assertEquals('D', stack.pop());
        Assert.assertEquals('C', stack.pop());
        Assert.assertEquals('B', stack.pop());
        Assert.assertEquals('A', stack.pop());
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidPeek() {
        try {
            stack.peek();
            Assert.fail();
        } catch (Exception e) {
        }
    }

}
