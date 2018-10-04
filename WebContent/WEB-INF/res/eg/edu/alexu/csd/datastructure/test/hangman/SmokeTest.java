package eg.edu.alexu.csd.datastructure.test.hangman;

import static org.junit.Assert.*;

import org.junit.Assert;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.hangman.IHangman;

public class SmokeTest {
    
    public static Class<?> getSpecifications(){
        return IHangman.class;
    }

    @org.junit.Test(timeout = 1000)
    public void testRandomWord() {
        IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
        
        String secret = hangman.selectRandomSecretWord();
        Assert.assertNull("Random word returned with no dictionary set!", secret);
        
        String dictionary[] = new String[] { "XXX", "YYYY" };
        hangman.setDictionary(dictionary);
        secret = hangman.selectRandomSecretWord();
        Assert.assertNotNull("Null random word returned!", secret);
        boolean found = false;
        for(int i=0; i<dictionary.length; i++)
            if(dictionary[i].equals(secret)){
                found = true;
            }
        Assert.assertTrue("The returned random word is not exist in the dictionary!", found);
    }

    @org.junit.Test(timeout = 1000)
    public void testWrongGuess() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            Assert.assertEquals("-----", hangman.guess('X'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testCorrectGuess() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            Assert.assertEquals("--Y--", hangman.guess('Y'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testCorrectGuessFirstChar() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            Assert.assertEquals("E----", hangman.guess('E'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testCorrectGuessLastChar() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            Assert.assertEquals("----T", hangman.guess('T'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCaseSensitiveLower() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            // Assert.assertTrue("E----".equalsIgnoreCase(hangman.guess('e')));
            Assert.assertEquals("E----", hangman.guess('e'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testCaseSensitiveUpper() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "egypt" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(2);
            // Assert.assertTrue("e----".equalsIgnoreCase(hangman.guess('E')));
            Assert.assertEquals("e----", hangman.guess('E'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
            e.printStackTrace();
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testFailCount() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            int max = 5;
            hangman.setMaxWrongGuesses(max);
            for(int i=0; i<max; i++){
                String result = hangman.guess('X');
                if(i<max-1)
                    Assert.assertEquals("Invalid Showing of Characters", "-----", result);
                else
                    Assert.assertNull("Game must end!", result);
            }
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testDefaultFailCount() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(null);
            String result; 
            result = hangman.guess('Y');
            Assert.assertEquals("Invalid Showing of Characters", "--Y--", result);
            result = hangman.guess('X');
            Assert.assertNull("Game must end!", result);
            result = hangman.guess('Z');
            Assert.assertNull("Game must end!", result);
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }

    @org.junit.Test(timeout = 1000)
    public void testNullGuess() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPTY" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(3);
            Assert.assertEquals("Failed to guess with null", "------", hangman.guess(null));
            Assert.assertEquals("Failed to guess with correct char", "--Y--Y", hangman.guess('Y'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
}
