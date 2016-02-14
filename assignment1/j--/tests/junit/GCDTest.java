package junit;

import junit.framework.TestCase;
import pass.GCD;

public class GCDTest extends TestCase {
    private GCD gcd;

    protected void setUp() throws Exception {
        super.setUp();
        gcd = new GCD();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGCD() {
        this.assertEquals(gcd.gcd(12, 8), 4);
        this.assertEquals(gcd.gcd(8,12), 4);
        this.assertEquals(gcd.gcd(42,1), 1);
        this.assertEquals(gcd.gcd(6,9), 3);
        this.assertEquals(gcd.gcd(6,12), 6);
    }
}
