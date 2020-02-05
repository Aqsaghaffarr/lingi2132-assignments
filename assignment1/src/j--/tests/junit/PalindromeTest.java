package junit;

import junit.framework.TestCase;
import pass.Palindrome;

public class PalindromeTest extends TestCase
{	
	private Palindrome palindrome;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		palindrome = new Palindrome();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testPalindrome()
	{
		this.assertEquals(palindrome.palindrome("kayak"), "kayak");
		this.assertEquals(palindrome.palindrome("Racecar"), "Racecar");
		this.assertEquals(palindrome.palindrome("java"), "");
	}
	
}