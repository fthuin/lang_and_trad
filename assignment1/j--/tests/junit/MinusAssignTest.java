package junit;

import junit.framework.TestCase;
import pass.MinusAssign;

public class MinusAssignTest extends TestCase {
    private MinusAssign minusAssign;

    protected void setUp() throws Exception {
        super.setUp();
        minusAssign = new MinusAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMinusAssign() {
        this.assertEquals(minusAssign.minusAssign(0, 42), -42);
        this.assertEquals(minusAssign.minusAssign(42, 0), 42);
        this.assertEquals(minusAssign.minusAssign(42, 3), 39);
        this.assertEquals(minusAssign.minusAssign(3,42), -39);
    }
}
