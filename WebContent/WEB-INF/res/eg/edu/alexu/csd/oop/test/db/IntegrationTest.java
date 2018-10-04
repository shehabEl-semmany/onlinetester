package eg.edu.alexu.csd.oop.test.db;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.TestRunner;

public class IntegrationTest {

    public static Class<?> getSpecifications(){
        return Database.class;
    }
    
    @Test
    public void test() {
        Assert.assertNotNull("Failed to create DBMS implemenation",  (Database)TestRunner.getImplementationInstance());
    }

}
