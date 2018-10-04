package eg.edu.alexu.csd.filestructure.test.avl;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.avl.IAVLTree;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return IAVLTree.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        IAVLTree instance = (IAVLTree)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

}
