package eg.edu.alexu.csd.filestructure.test.avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;

import eg.edu.alexu.csd.filestructure.avl.IDictionary;

public class DictionaryTest {

    public static Class<?> getSpecifications(){
        return IDictionary.class;
    }

    @org.junit.Test(timeout = 7000)
    public void testLoad() {
        IDictionary dict = (IDictionary) TestRunner.getImplementationInstance();
        File input = new File(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/avl/res/dictionary.txt");
        dict.load(input);
        assertEquals(9123, dict.size()); // out of the 10,000 input words, there are only 9123 unique words
        assertEquals(16, dict.height());
    }

    @org.junit.Test(timeout = 10000)
    public void testLookup() throws FileNotFoundException {
        IDictionary dict = (IDictionary) TestRunner.getImplementationInstance();
        File dictFile = new File(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/avl/res/dictionary.txt");
        dict.load(dictFile);
        File inputFile = new File(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/avl/res/queries.txt");
        Scanner sc = new Scanner(inputFile);
        // there are 15 queries. the first 10 exist, and the remaining 5 don't
        for (int i = 0; i < 10; ++i)
            assertTrue(dict.exists(sc.next()));
        for (int i = 0; i < 5; ++i)
            assertFalse(dict.exists(sc.next()));
        sc.close();
    }

    @org.junit.Test(timeout = 7000)
    public void testDelete() throws FileNotFoundException {
        IDictionary dict = (IDictionary) TestRunner.getImplementationInstance();
        File dictFile = new File(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/avl/res/dictionary.txt");
        dict.load(dictFile);
        File inputFile = new File(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/filestructure/test/avl/res/deletions.txt");
        Scanner sc = new Scanner(inputFile);
        // there are 300 words to delete, but only 290 unique words in them
        while (sc.hasNext())
            dict.delete(sc.next());
        sc.close();
        assertEquals(9123 - 290, dict.size()); // out of the 10,000 input words, there are only 9123 unique words
        assertEquals(15, dict.height());
    }

}
