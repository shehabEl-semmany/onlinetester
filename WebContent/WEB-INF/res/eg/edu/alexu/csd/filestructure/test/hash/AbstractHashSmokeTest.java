package eg.edu.alexu.csd.filestructure.test.hash;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.filestructure.hash.*;

public abstract class AbstractHashSmokeTest {
    
    abstract int[] getImplementationSpecificInformation(int testId);
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @org.junit.Test(timeout = 1000)
    public void testPutAndGetAndContains() {
        IHash instance = (IHash)TestRunner.getImplementationInstance();
        int key = (int)(Math.random() * 1000);
        String v = "Value";
        assertFalse("Found non existing values", instance.contains(key));
        instance.put(key, v);
        assertTrue("Failed to find an existing value", instance.contains(key));
        assertEquals("Failed to return an existing value", v, instance.get(key));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @org.junit.Test(timeout = 1000)
    public void testDeleteAndEmpty() {
        IHash instance = (IHash)TestRunner.getImplementationInstance();
        int key = (int)(Math.random() * 1000);
        assertTrue("Empty Hashtable", instance.isEmpty());
        instance.put(key, "xxxx");
        assertFalse("Empty Hashtable", instance.isEmpty());
        instance.delete(key);
        assertTrue("Empty Hashtable", instance.isEmpty());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fillAndCheck(int testId, int count){
        IHash instance = (IHash)TestRunner.getImplementationInstance();
        Set<Integer> keys = new HashSet<Integer>();
        for(int i=0; i<count; i++){
            int key = (i+100000) * 12345;
            keys.add(key);
            instance.put(key, String.valueOf(i));
        }
        assertEquals("Invalid size Count", count, instance.size());
        
        int[] expected = getImplementationSpecificInformation(testId);
        assertEquals("Invalid Collisions Count", expected[0], instance.collisions());
        Iterable hashKeys = instance.keys();
        assertNotNull("Keys Iterable is null", hashKeys);
        Iterator hashKeysItr = hashKeys.iterator();
        assertNotNull("Keys Iterator is null", hashKeysItr);
        while(hashKeysItr.hasNext()){
            assertTrue("Non existing key returned", keys.remove(hashKeysItr.next()));
        }
        assertTrue("Some keys were missing", keys.isEmpty());
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCollesionsAndCapacity() {
        fillAndCheck(1, 1000);
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCollesionsAndCapacityAfterRehash() {
        fillAndCheck(2, 10000);
    }
}
