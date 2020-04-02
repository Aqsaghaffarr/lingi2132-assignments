package junit;

import junit.framework.TestCase;
import pass.Or;

public class OrTest extends TestCase {
	
	private Or or;

    protected void setUp() throws Exception {
        super.setUp();
        or = new Or();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testOr() {
        this.assertEquals(or.or(false, false), false);
        this.assertEquals(or.or(false, true), true);
        this.assertEquals(or.or(true, false), true);
        this.assertEquals(or.or(true, true), true);
    }

}
