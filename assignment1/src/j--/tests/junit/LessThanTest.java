package junit;

import junit.framework.TestCase;
import pass.LessThan;

public class LessThanTest extends TestCase
{
	private LessThan lessThan;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		lessThan = new LessThan();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testLessThan()
	{
		this.assertEquals(lessThan.lessThan(3, 0), false);
		this.assertEquals(lessThan.lessThan(0, 3), true);
		this.assertEquals(lessThan.lessThan(0, 0), false);
	}
}