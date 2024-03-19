public class Sin extends Node {
    Node arg;

    Sin(Node arg) {
        this.arg = arg;
    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ')';
    }

    @Override
    double evaluate() {
        return Math.sin(arg.evaluate());
    }

    @Override
    boolean isZero() {
        return Math.abs(Math.sin(arg.evaluate()))<1e-7;
    }

    @Override
    Node diff(Variable var) {
        return new Cosine(arg);
    }

    @Override
    Node simplify() {
        arg = arg.simplify();
        return this;
    }
}
