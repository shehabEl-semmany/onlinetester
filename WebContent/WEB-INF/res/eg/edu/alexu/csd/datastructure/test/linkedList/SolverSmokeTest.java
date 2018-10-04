package eg.edu.alexu.csd.datastructure.test.linkedList;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;

public class SolverSmokeTest {
    
    public static Class<?> getSpecifications(){
        return IPolynomialSolver.class;
    }

    @org.junit.Test(timeout = 1000)
    public void testSetUnorderedAndNegativePloynomial() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        try {
            instance.setPolynomial('A', new int[][] {{1,1}, {10,10}});
        } catch (Exception e) {
        	fail("Solver should accept unordered input");
        }
        try {
            instance.setPolynomial('B', new int[][] {{1,-1}});
        } catch (Exception e) {
        	fail("Solver should accept polynomial with negative exponent");
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSetAndClearPloynomial() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        assertNull("Polynomial is not set yet", instance.print('A'));
        instance.setPolynomial('A', new int[][] {{1,1}});
        assertNotNull("Polynomial is set and returning null", instance.print('A'));
        instance.clearPolynomial('A');
        assertNull("Polynomial is cleared", instance.print('A'));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalidPloynomialName() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        try {
            instance.setPolynomial('Z', new int[][] {{1,1}});
            fail("Solver accepted setting polynomial with name 'Z'");
        } catch (Exception e) {
        }
        try {
            instance.setPolynomial('A', null);
            fail("Solver accepted setting polynomial with null input");
        } catch (Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testOperateOnUnsetOrInvalidPloynomial() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        try {
            instance.evaluatePolynomial('A', 5);
            fail("Solver evaluated unseted polynomial");
        } catch (Exception e) {
        }
        try {
            instance.add('A', 'B');
            fail("Solver added unseted polynomial");
        } catch (Exception e) {
        }
        try {
            instance.subtract('A', 'B');
            fail("Solver added unseted polynomial");
        } catch (Exception e) {
        }
        try {
            instance.multiply('A', 'B');
            fail("Solver added unseted polynomial");
        } catch (Exception e) {
        }
    }
}
