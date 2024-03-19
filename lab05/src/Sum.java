import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<>();

    Sum() {
    }

    Sum(Node n1, Node n2) {
        args.add(n1);
        args.add(n2);
    }


    Sum add(Node n) {
        args.add(n);
        return this;
    }

    Sum add(double c) {
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c, n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result = 0;
        //oblicz sumę wartości zwróconych przez wywołanie evaluate skłądników sumy
        for (Node arg : args) {
            result += arg.evaluate();
        }
        return sign * result;
    }

    int getArgumentsCount() {
        return args.size();
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-(");
        // to implement
        for (int i = 0; i < args.size(); i++) {
            b.append(args.get(i).toString());
            if (i != args.size() - 1) {
                b.append(" + ");
            }
        }
        if (sign < 0) b.append(")");
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        for (Node n : args) {
            Node calculatedDiff = n.diff(var);
            if (!calculatedDiff.isZero()) {
                r.add(calculatedDiff);
            }
        }
        return r;
    }

    @Override
    boolean isZero() {
        for (Node arg : args) {
            if (!arg.isZero()) {
                return false;
            }
        }
        return true;
    }

    @Override
    Node simplify() {
//        for (Node arg :
//                args) {
//            System.out.println(arg.toString() + " " + arg.getClass());
////            arg.simplify();
//        }
        double sum = 0;
        args.replaceAll(Node::simplify);
        List<Node> finalArgs = new ArrayList<>();
        for(Node arg : args) {
            if (arg.getClass()==Constant.class) {
                sum += arg.evaluate();
            }
            else {
                finalArgs.add(arg);
            }
        }
        if (Math.abs(sum)>1e-7) {
            finalArgs.add(new Constant(sum));
        }
        finalArgs.replaceAll(Node::simplify);
        args=finalArgs;
        return this;
    }
}