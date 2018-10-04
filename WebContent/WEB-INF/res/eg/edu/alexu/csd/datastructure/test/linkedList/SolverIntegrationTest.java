package eg.edu.alexu.csd.datastructure.test.linkedList;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;

public class SolverIntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IPolynomialSolver.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCreation() {
        IPolynomialSolver instance = (IPolynomialSolver)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
