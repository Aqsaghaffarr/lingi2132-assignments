package junit;

import junit.framework.TestCase;
import pass.UnaryAddition;

public class UnaryAdditionTest extends TestCase
{
	private UnaryAddition unaryAddition;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		unaryAddition = new UnaryAddition();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testUnaryAddition()
	{
		this.assertEquals(unaryAddition.unaryAdd(4), 4);
		this.assertEquals(unaryAddition.unaryAdd(4 - 5), -1);
	}
}