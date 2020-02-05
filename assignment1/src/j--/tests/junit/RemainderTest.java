package junit;

import junit.framework.TestCase;
import pass.Remainder;

public class RemainderTest extends TestCase
{
	private Remainder remainder;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		remainder = new Remainder();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testDivide()
	{
		this.assertEquals(remainder.takeRemainder(0, 42), 0);
		this.assertEquals(remainder.takeRemainder(3, 42), 3);
		this.assertEquals(remainder.takeRemainder(127, 3), 1);
		this.assertEquals(remainder.takeRemainder(6, 2), 0);
		this.assertEquals(remainder.takeRemainder(5, 3), 2);
	}
}