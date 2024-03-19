import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node {
    double value;

    Constant(double value) {
        this.sign = value < 0 ? -1 : 1;
        this.value = value < 0 ? -value : value;
    }


    @Override
    double evaluate() {
        return sign * value;
    }

    @Override
    public String toString() {
        String sgn = sign < 0 ? "-" : "";
//        return sgn+Double.toString(value);
        StringBuilder b = new StringBuilder();
        if (sign<0) {
            b.append("(");
        }
        DecimalFormat format = new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));
        b.append(sgn);
        b.append(format.format(value));
        if (sign<0) {
            b.append(")");
        }
        return b.toString() ;
    }

    @Override
    boolean isZero() {
        return Math.abs(value)<1e-7;
    }

    @Override
    Node diff(Variable var) {
        return new Constant(0);
    }

    @Override
    Node simplify() {
        return this;
    }
}