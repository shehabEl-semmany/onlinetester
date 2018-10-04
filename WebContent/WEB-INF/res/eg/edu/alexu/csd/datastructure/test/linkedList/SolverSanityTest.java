package eg.edu.alexu.csd.datastructure.test.linkedList;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;

public class SolverSanityTest {
    
    public static Class<?> getSpecifications(){
        return IPolynomialSolver.class;
    }
    
    /**
     * Reference:
     * http://www.mathportal.org/calculators/polynomials-solvers/polynomials-operations-calculator.php
     */

    private void assertTwoDimensionArrayEquals(int[][] expected, int[][] actual){
        assertNotNull(actual);
        assertEquals("Result length not matched ", expected.length, actual.length);
        for(int i=0; i<expected.length; i++)
            assertArrayEquals("Row: " + i + ": ", expected[i], actual[i]);
    }

    @org.junit.Test(timeout = 1000)
    public void testSolveAdd() {
            IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
            instance.setPolynomial('C',
                            // x^7-10x^6+40x^5-96x^4+176x^3-224x^2+128x
                            new int[][]{ {1,7}, {-10,6}, {40,5}, {-96,4}, {176,3}, {-224,2}, {128,1} });
            instance.setPolynomial('B',
                            // -120x^5-146x^4-x^3+27x^2+x-1
                            new int[][]{ {-120,5}, {-146,4}, {-1,3}, {27,2}, {1,1}, {-1,0} });
            assertNull("Polynomial R is not set yet", instance.print('R'));
            int[][] result1 = instance.add('B', 'C');
            assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
            try {
                    instance.add('B', 'Z');
                    fail("Operation on polynomial Z is not permitted");
            } catch (Exception e) {
            }
            int[][] result2 = instance.add('C', 'B');
            assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
            int[][] expected = new int[][] {
                            // x^7-10x^6-80x^5-242x^4+175x^3-197x^2+129^x-1
                            {1,7}, {-10,6}, {-80,5}, {-242,4}, {175,3}, {-197,2}, {129,1}, {-1,0}
                    };
            assertTwoDimensionArrayEquals(expected, result1);
            assertTwoDimensionArrayEquals(expected, result2);
    }

    @org.junit.Test(timeout = 1000)
    public void testSolveSubtract() {
            IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
            instance.setPolynomial('A',
                            // x^7-10x^6+40x^5-96x^4+176x^3-224x^2+128x
                            new int[][]{ {1,7}, {-10,6}, {40,5}, {-96,4}, {176,3}, {-224,2}, {128,1} });
            instance.setPolynomial('B',
                            // -120x^5-146x^4-x^3+27x^2+x-1
                            new int[][]{ {-120,5}, {-146,4}, {-1,3}, {27,2}, {1,1}, {-1,0} });
            assertNull("Polynomial R is not set yet", instance.print('R'));
            int[][] result = instance.subtract('A', 'B');
            assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
            int[][] expected = new int[][] {
                            //      x^7-10x^6+160x^5+50x^4+177x^3-251x^2+127x+1
                            {1,7}, {-10,6}, {160,5}, {50,4}, {177,3}, {-251,2}, {127,1}, {1,0}
                    };
            assertTwoDimensionArrayEquals(expected, result);
    }

    @org.junit.Test(timeout = 1000)
    public void testSolveMultiply() {
            IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
            instance.setPolynomial('A',
                            // x^7-10x^6+40x^5-96x^4+176x^3-224x^2+128x
                            new int[][]{ {1,7}, {-10,6}, {40,5}, {-96,4}, {176,3}, {-224,2}, {128,1} });
            instance.setPolynomial('C',
                            // -120x^5-146x^4-x^3+27x^2+x-1
                            new int[][]{ {-120,5}, {-146,4}, {-1,3}, {27,2}, {1,1}, {-1,0} });
            assertNull("Polynomial R is not set yet", instance.print('R'));
            int[][] result1 = instance.multiply('A', 'C');
            assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
            int[][] result2 = instance.multiply('C', 'A');
            assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
            int[][] expected = new int[][] {
                            // -20x^12+1054x^11-3341x^10+5717x^9-7413x^8+2349x^7+14626x^6-13848x^5-5904x^4+3056x^3+352x^2-128x
                            {-120,12}, {1054,11}, {-3341,10}, {5717,9}, {-7413,8}, {2349,7}, {14626,6}, {-13848,5}, {-5904,4}, {3056,3}, {352,2}, {-128,1}
                    };
            assertTwoDimensionArrayEquals(expected, result1);
            assertTwoDimensionArrayEquals(expected, result2);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEvaluate() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('A', new int[][] { {1,2}, {-10,1}, {-1,0} });
        float result = instance.evaluatePolynomial('A', 10);
        assertEquals("Wrong polynomial value", -1, (int)result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testOperationOnSamePloynomial() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('A', new int[][] {{1,1}});
        int[][] result1 = instance.add('A', 'A');
        int[][] expected1 = new int[][] { {2,1} };
        assertTwoDimensionArrayEquals(expected1, result1);
        int[][] result2 = instance.subtract('A', 'A');
        int[][] expected2 = new int[][] { {0,0} };
        assertTwoDimensionArrayEquals(expected2, result2);
        int[][] result3 = instance.multiply('A', 'A');
        int[][] expected3 = new int[][] { {1,2} };
        assertTwoDimensionArrayEquals(expected3, result3);
    }

    @org.junit.Test(timeout = 1000)
    public void testPrintOrdered() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('A', new int[][] {{-5, 2},{1,1}, {3, 4}});
        String result = instance.print('A');
        assertNotNull("Polynomial is set and returning null", result);
        String expected = "3x^4-5x^2+x";
        assertTrue("You should print the polynomial in descending order", expected.equals(result));
        //assertEquals(expected, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSolveAdd2() {
    	IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('C', new int[][]{ {3, 2}, {2, -2} });
        instance.setPolynomial('B', new int[][]{ {4, 2}, {-1, 1}, {1, -2}, {6, 0} });
        assertNull("Polynomial R is not set yet", instance.print('R'));
        int[][] result = instance.add('B', 'C');
        assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
        int[][] expected = new int[][] { {7, 2}, {-1, 1}, {6, 0}, {3, -2} };
        assertTwoDimensionArrayEquals(expected, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSolveMultiply2() {
    	IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('C', new int[][]{ {2, 3} });
        instance.setPolynomial('A', new int[][]{ {1, -3} });
        assertNull("Polynomial R is not set yet", instance.print('R'));
        int[][] result = instance.multiply('C', 'A');
        assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
        int[][] expected = new int[][] { {2, 0} };
        assertTwoDimensionArrayEquals(expected, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSolveMultiply3() {
    	IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('C', new int[][]{ {2, 3}, {6, 4} });
        instance.setPolynomial('A', new int[][]{ {1, -3}, {-3, 2}, {1, -1} });
        assertNull("Polynomial R is not set yet", instance.print('R'));
        int[][] result = instance.multiply('C', 'A');
        assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
        int[][] expected = new int[][] { {-18, 6}, {-6, 5}, {6, 3}, {2, 2}, {6, 1}, {2, 0} };
        assertTwoDimensionArrayEquals(expected, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSolveSubtract2() {
    	IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('C', new int[][]{ {2, 3}, {6, 4} });
        instance.setPolynomial('A', new int[][]{ {2, 3}, {6, 4} });
        assertNull("Polynomial R is not set yet", instance.print('R'));
        int[][] result = instance.subtract('C', 'A');
        assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
        int[][] expected = new int[][] { {0, 0} };
        assertTwoDimensionArrayEquals(expected, result);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSolveSubtract3() {
    	IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        instance.setPolynomial('C', new int[][]{ {2, 3}, {0, 4} });
        instance.setPolynomial('A', new int[][]{ {2, 3}, {6, 4} });
        assertNull("Polynomial R is not set yet", instance.print('R'));
        int[][] result = instance.subtract('C', 'A');
        assertNotNull("Polynomial R must be set after evaluation", instance.print('R'));
        int[][] expected = new int[][] { {-6, 4} };
        assertTwoDimensionArrayEquals(expected, result);
    }
    
}
