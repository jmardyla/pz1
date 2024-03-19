import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod() {
    }

    Prod(Node n1) {
        args.add(n1);
    }

    Prod(double c) {
        //wywołaj konstruktor jednoargumentowy przekazując new Constant(c)
        this(new Constant(c));
    }

    Prod(Node n1, Node n2) {
        args.add(n1);
        args.add(n2);
    }

    Prod(double c, Node n) {
        //wywołaj konstruktor dwuargumentowy
        this(new Constant(c), n);
    }


    Prod mul(Node n) {
        args.add(n);
        return this;
    }

    Prod mul(double c) {
        //???
        return this.mul(new Constant(c));
    }

    List<Node> getArgs() {
        return args;
    }


    @Override
    double evaluate() {
        double result = 1;
        // oblicz iloczyn czynników wołąjąc ich metodę evaluate
        for (Node argument : args) {
            result *= argument.evaluate();
        }
        return sign * result;
    }

    int getArgumentsCount() {
        return args.size();
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-");
        // ...
        // zaimplementuj
        for (int i = 0; i < args.size(); i++) {
            b.append(args.get(i).toString());
            if (i != args.size()-1) {
                b.append("*");
            }
        }
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        for (int i = 0; i < args.size(); i++) {
            Prod m = new Prod();
            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);
                if (j == i) m.mul(f.diff(var));
                else m.mul(f);
            }
            if (!m.isZero()) {
                r.add(m);
            }
        }
        return r;
    }

    @Override
    boolean isZero() {
        for(Node argument : args) {
            if (argument.isZero()) {
                return true;
            }
        }
        return false;
    }


    @Override
    Node simplify() {
//        for(Node arg : args) {
//            System.out.println("    " + arg.toString() + " " + arg.getClass());
//        }
        double overall = 1;
        List<Node> newArgs = new ArrayList<>();
        for(Node arg : args) {
            if (arg instanceof Prod) {
                for(Node prodArgument : ((Prod) arg).getArgs()) {
                    if (!(prodArgument instanceof Constant) || (Math.abs(prodArgument.evaluate()-1)>1e-7)) {
                        newArgs.add(prodArgument);
                    }
                }
            }
            else {
                newArgs.add(arg);
            }
        }
        List<Node> finalArgs = new ArrayList<>();
        for(Node arg : newArgs) {
            if (arg.getClass() == Constant.class) {
                overall *= arg.evaluate();
            } else {
                finalArgs.add(arg.simplify());
            }
        }
        if (Math.abs(overall-1)>1e-7) {
            finalArgs.add(0, new Constant(overall));
        }
        if (finalArgs.size()==1) {
            return finalArgs.get(0);
        }

        args=finalArgs;
        return this;
    }
}