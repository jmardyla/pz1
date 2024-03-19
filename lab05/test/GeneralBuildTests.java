import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralBuildTests {

    @Test
    public void buildAndPrint(){
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7);
        assertEquals("2.1*x^3 + x^2 + (-2)*x + 7", exp.toString());

    }

    double f(double x) {
        return Math.pow(x, 3) - 2*Math.pow(x, 2) - x + 2;
    }

    @Test
    public void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        //System.out.println(exp.toString());
        for (double v = -5; v < 5; v += 0.1) {
            x.setValue(v);
            // System.out.println(f(x.evaluate()) + " " + exp.evaluate());
            assertEquals(f(x.evaluate()), exp.evaluate(), 1e-7);
        }
    }

    double evaluateCircleEquation(double x, double y) {
        return Math.pow(x, 2)+Math.pow(y, 2)+8*x+4*y+16;
    }
    @Test
    public void defineCircle(){
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        // System.out.println(circle.toString());

        for (int i = 0; i < 1000; i++) {
            double xv = 100*(Math.random()-.5);
            double yv = 100*(Math.random()-.5);
            x.setValue(xv);
            y.setValue(yv);
            assertEquals(evaluateCircleEquation(x.evaluate(), y.evaluate()), circle.evaluate(), 1e-7);
        }
        //System.out.print(String.format("Punkt (%f,%f) leży %s koła %s",xv,yv,(fv<=0?"wewnątrz":"na zewnątrz"),circle.toString()));
    }
}