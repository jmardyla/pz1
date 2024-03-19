import org.junit.Test;

import static org.junit.Assert.*;

public class SimplifyTest {

    @Test
    public void simplifyTest() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node exp = new Sum()
                .add(new Prod(4, (new Power(x,3))))
                .add(new Power(y,2))
                .add(new Power(x, 2))
                .add(8,x)
                .add(16);
//        System.out.println(exp.toString());

        Node dx = exp.diff(x);
//        System.out.println(dx.toString());
//        System.out.println(dx.simplify().toString());
        assertEquals("12*x^2 + 2*x + 8", dx.simplify().toString());
    }
}