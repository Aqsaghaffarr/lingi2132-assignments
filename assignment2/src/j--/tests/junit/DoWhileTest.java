package junit;

import junit.framework.TestCase;
import pass.DoWhile;

public class DoWhileTest extends TestCase {
	
	private DoWhile dw;

    protected void setUp() throws Exception {
        super.setUp();
        dw = new DoWhile();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDoWhile() {
        this.assertEquals(dw.dowhile(5), 0);
        this.assertEquals(dw.dowhile(25), 0);
        this.assertEquals(dw.dowhile(1), 0);
        this.assertEquals(dw.dowhile(-1), -2);
        this.assertEquals(dw.dowhile(-12), -13);
        this.assertEquals(dw.dowhile(0), -1);
    }

}
