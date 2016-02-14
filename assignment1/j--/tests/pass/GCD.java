package pass;

public class GCD {
    public int gcd(int x, int y) {
        int newx = x;
        int newy = y;

        if (x == 0) return 0;
        if (y == 0) return 0;

        while (! (newx == newy)) {
            if (newx > newy) {
                newx -= newy;
            }
            else {
                newy -= newx;
            }
        }

        return newx;
    }
}
