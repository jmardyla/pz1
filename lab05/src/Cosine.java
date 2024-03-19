public class Cosine extends Node {
    Node arg;

    Cosine(Node arg) {
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "cos(" + arg.toString() + ')';
    }

    @Override
    double evaluate() {
        return Math.cos(arg.evaluate());
    }

    @Override
    boolean isZero() {
        return Math.abs(Math.cos(arg.evaluate()))<1e-7;
    }

    @Override
    Node diff(Variable var) {
        Sin e = new Sin(arg);
        e.minus();
        return e;
    }

    @Override
    Node simplify() {
        arg = arg.simplify();
        return this;
    }
}
