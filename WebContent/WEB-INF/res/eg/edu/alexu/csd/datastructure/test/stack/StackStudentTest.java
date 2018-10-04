package eg.edu.alexu.csd.datastructure.test.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.stack.IStack;

public class StackStudentTest {

	private IStack stack;

	public static Class<?> getSpecifications() {
		return IStack.class;
	}

	@Before
	public void init() {
		stack = (IStack) TestRunner.getImplementationInstance();
	}

	@org.junit.Test(timeout = 1000)
	public void cs52PopWhenStachEmpty() {

		try {
			stack.pop();
			fail("Stack is Empty");
		} catch (Exception e) {

		}
	}

	@org.junit.Test(timeout = 1000)
	public void cs52PeekWhenStachEmpty() {

		try {
			stack.peek();
			fail("Stack is Empty");
		} catch (Exception e) {

		}
	}

	@org.junit.Test(timeout = 1000)
	public void cs52isEmpty() {
		assertEquals(stack.isEmpty(), true);
		stack.push(0);
		stack.push(1);
		stack.push(2);
		assertEquals(stack.isEmpty(), false);
	}

	@org.junit.Test(timeout = 1000)
	public void cs52Push_Pop() {

		stack.push(0);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		for (int i = 4; i <= 0; i++) {
			assertEquals(stack.pop(), i);
		}
	}

	@org.junit.Test(timeout = 1000)
	public void cs52Push_Peek() {

		stack.push(0);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		for (int i = 4; i <= 0; i++) {
			assertEquals(stack.peek(), 4);
		}
	}

	@org.junit.Test(timeout = 1000)
	public void cs52Size() {

		stack.push(0);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		assertEquals(stack.size(), 5);
	}

	@org.junit.Test(timeout = 1000)
	public void cs52Size2() {

		stack.push(0);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		assertEquals(stack.size(), 5);
	}

	@org.junit.Test(timeout = 1000)
	public void cs52TestPushPop() {

		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		for (int i = 5; i >= 1; i--) {
			assertEquals(stack.pop(), i);
		}
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test2peekDontClear() {
		stack.push(Integer.valueOf(0));
		assertEquals(0, stack.peek());
		assertFalse(stack.isEmpty());
		return;
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test3popRemoves() {
		stack.push(Integer.valueOf(0));
		assertEquals(0, stack.pop());
		assertTrue(stack.isEmpty());
		return;
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test4logicOfStack() {
		for (int counter = 0; counter < 10; counter++) {
			stack.push(Integer.valueOf(counter));
		}
		for (int counter = 9; counter > -1; counter--) {
			assertEquals(Integer.valueOf(counter), stack.pop());
		}
		assertTrue(stack.isEmpty());
		return;
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test5pushPopEmpty() {
		for (int counter = 0; counter < 100; counter++) {
			stack.push(Integer.valueOf(counter));
			assertEquals(counter, stack.pop());
			assertTrue(stack.isEmpty());
		}
		return;
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test6Size() {
		for (int counter = 0; counter < 10; counter++) {
			assertEquals(counter, stack.size());
			stack.push(counter);
		}
		return;
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test7Palindrome() {
		String h = "oraaro";
		char[] word = new char[15];
		word = h.toCharArray();
		for (int counter = 0; counter < h.length(); counter++) {
			if (counter < h.length() / 2) {
				stack.push(Character.valueOf(word[counter]));
			} else if (h.length() % 2 != 0 && counter == h.length() / 2 + 1) {
			} else {
				if (Character.valueOf(word[counter]) != stack.pop()) {
					break;
				} else {
					stack.pop();
				}
			}
		}
		assertTrue(stack.isEmpty());// that's a palindrome
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test8notPalindrome() {
		String h = "friday";
		char[] word = h.toCharArray();
		for (int counter = 0; counter < h.length(); counter++) {
			if (counter < h.length() / 2) {
				stack.push(Character.valueOf(word[counter]));
			} else if (h.length() % 2 != 0 && counter == h.length() / 2 + 1) {
			} else {
				if (Character.valueOf(word[counter]) != stack.peek()) {
					break;
				} else {
					stack.pop();
				}
			}
		}
		assertFalse(stack.isEmpty());// and that's not :D
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test9GoodTags() {
		String[] Tags = { "<header>", "<middle>", "<left>", "</left>", "<right>", "</right>", "</middle>",
				"</header>" };
		for (int counter = 0; counter < Tags.length; counter++) {
			Tags[counter] = Tags[counter].substring(1, Tags[counter].length());
		}
		for (int counter = 0; counter < Tags.length; counter++) {
			if (Tags[counter].toCharArray()[0] != '/') {
				stack.push(Tags[counter]);
			} else {
				Tags[counter] = Tags[counter].substring(1, Tags[counter].length());
				if (!Tags[counter].matches((String) stack.peek())) {
					break;
				} else {
					stack.pop();
				}
			}
		}
		assertTrue(stack.isEmpty());// that tag array is well formed
	}

	@org.junit.Test(timeout = 1000)
	public void cs64Test10BadTags() {
		String[] Tags = { "<header>", "<middle>", "<left>", "</middle>", "<right>", "</right>", "</left>",
				"</header>" };
		for (int counter = 0; counter < Tags.length; counter++) {
			Tags[counter] = Tags[counter].substring(1, Tags[counter].length());
		}
		for (int counter = 0; counter < Tags.length; counter++) {
			if (Tags[counter].toCharArray()[0] != '/') {
				stack.push(Tags[counter]);
			} else {
				Tags[counter] = Tags[counter].substring(1, Tags[counter].length());
				if (!Tags[counter].matches((String) stack.peek())) {
					break;
				} else {
					stack.pop();
				}
			}
		}
		assertFalse(stack.isEmpty());// and that's not :D
	}

}
