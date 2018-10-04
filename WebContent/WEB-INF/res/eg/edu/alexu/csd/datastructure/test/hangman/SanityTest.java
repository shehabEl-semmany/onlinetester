package eg.edu.alexu.csd.datastructure.test.hangman;

import static org.junit.Assert.*;

import org.junit.Assert;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.hangman.IHangman;

public class SanityTest {
    
    public static Class<?> getSpecifications(){
        return IHangman.class;
    }

    @org.junit.Test(timeout = 1000)
    public void testFullGameWin() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "MAURITANIA" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(3);
            Assert.assertEquals("Step1- Guess was 'a'", "-A----A--A", hangman.guess('a'));
            Assert.assertEquals("Step2- Guess was 'u'", "-AU---A--A", hangman.guess('u'));
            Assert.assertEquals("Step3- Guess was 'M'", "MAU---A--A", hangman.guess('M'));
            Assert.assertEquals("Step4- Guess was 'X'", "MAU---A--A", hangman.guess('X'));  // wrong 1
            Assert.assertEquals("Step5- Guess was 'i'", "MAU-I-A-IA", hangman.guess('i'));
            Assert.assertEquals("Step6- Guess was 'A'", "MAU-I-A-IA", hangman.guess('A'));
            Assert.assertEquals("Step7- Guess was 'R'", "MAURI-A-IA", hangman.guess('R'));
            Assert.assertEquals("Step8- Guess was 'T'", "MAURITA-IA", hangman.guess('T'));
            Assert.assertEquals("Step9- Guess was 'Y'", "MAURITA-IA", hangman.guess('Y'));  // wrong 2
            Assert.assertEquals("Step10- Guess was 'n'", "MAURITANIA", hangman.guess('n'));
            Assert.assertNull("Step11- Guess was 'Z'", hangman.guess('Z'));                 // wrong 3
            Assert.assertNull("Step12- Guess was 'O'", hangman.guess('O'));                 // wrong 4
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testFullGameLoss() {
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "MAURITANIA" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            hangman.setMaxWrongGuesses(3);
            Assert.assertEquals("Step1- Guess was 'a'", "-A----A--A", hangman.guess('a'));
            Assert.assertEquals("Step2- Guess was 'u'", "-AU---A--A", hangman.guess('u'));
            Assert.assertEquals("Step3- Guess was 'M'", "MAU---A--A", hangman.guess('M'));
            Assert.assertEquals("Step4- Guess was 'X'", "MAU---A--A", hangman.guess('X'));  // wrong 1
            Assert.assertEquals("Step5- Guess was 'i'", "MAU-I-A-IA", hangman.guess('i'));
            Assert.assertEquals("Step6- Guess was 'A'", "MAU-I-A-IA", hangman.guess('A'));
            Assert.assertEquals("Step7- Guess was 'R'", "MAURI-A-IA", hangman.guess('R'));
            Assert.assertEquals("Step8- Guess was 'T'", "MAURITA-IA", hangman.guess('T'));
            Assert.assertEquals("Step9- Guess was 'Y'", "MAURITA-IA", hangman.guess('Y'));  // wrong 2
            Assert.assertNull("Step10- Guess was 'Z'", hangman.guess('Z'));                 // wrong 3
            Assert.assertNull("Step11- Guess was 'n'", hangman.guess('n'));
            Assert.assertNull("Step12- Guess was 'O'", hangman.guess('O'));                 // wrong 4
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testInvalid() {
        // no select random word
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.setMaxWrongGuesses(3);
            try {
                hangman.guess('e');
                fail("Guessing without selecting a secret word, you should throw an Exception");
            } catch (Exception e) {}
            hangman.selectRandomSecretWord();
            String result = hangman.guess('E');
            Assert.assertEquals("Invalid Showing of Characters", "E----", result);
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
            
        // no max wrong guesses
        try {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            String dictionary[] = new String[] { "EGYPT" };
            hangman.setDictionary(dictionary);
            hangman.selectRandomSecretWord();
            try {
                hangman.guess('E');
                fail("Guessing without setting maximum number of wrong guesses, you should throw an Exception");
            } catch (Exception e) {}
            hangman.setMaxWrongGuesses(3);
            Assert.assertEquals("Invalid Showing of Characters", "E----", hangman.guess('E'));
        } catch (Exception e) {
            TestRunner.fail("Failed to complete the test", e);
        }
        
        // zero lengthed secret word
        {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            hangman.setDictionary(new String[] { "" });
            hangman.setMaxWrongGuesses(3);
            hangman.selectRandomSecretWord();
            try {
                hangman.guess('e');
                fail("Secret word can't be empty or spaces, you should throw an Exception");
            } catch (Exception e) {}
        }
        {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            hangman.setDictionary(new String[] { "     " });
            hangman.setMaxWrongGuesses(3);
            hangman.selectRandomSecretWord();
            try {
                hangman.guess('e');
                fail("Secret word can't be empty or spaces, you should throw an Exception");
            } catch (Exception e) {}
        }
        {
            IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
            hangman.setDictionary(new String[] { "           " });
            hangman.setMaxWrongGuesses(3);
            hangman.selectRandomSecretWord();
            try {
                hangman.guess('e');
                fail("Secret word can't be empty or spaces, you should throw an Exception");
            } catch (Exception e) {}
        }
    }
        
    @org.junit.Test(timeout = 1000)
    public void testRandomSecretWord() {
        IHangman hangman = (IHangman)TestRunner.getImplementationInstance();
        String dictionary[] = new String[] { "EGYPT", "Ahmed", "Mohamed", "Reham", "Omar", "Sara" };
        hangman.setDictionary(dictionary);
        String[] result = new String[5];
        try {
            result[0] = hangman.selectRandomSecretWord();
            result[1] = hangman.selectRandomSecretWord();
            result[2] = hangman.selectRandomSecretWord();
            result[3] = hangman.selectRandomSecretWord();
            result[4] = hangman.selectRandomSecretWord();
            boolean dup = true;
            for (String str : result) {
                if (!result[0].equals(str)) {
                    dup = false;
                    break;
                }
            }
            Assert.assertFalse("You should select a random word everytime", dup);
        } catch (Exception e) {
            TestRunner.fail("Unable to select a random word multiple times", e);
        }
    }
}
