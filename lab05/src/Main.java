public class Main {
    public static void main(String[] args) {
//        Variable x = new Variable("x");
//        Node exp = new Sum()
//                .add(2.1,new Power(x,3))
//                .add(new Power(x,2))
//                .add(-2,x)
//                .add(7);
//        System.out.println(exp.toString());

        Variable z = new Variable("z");
        Node exp2 = new Sum()
                .add(2,new Power(z,3))
                .add(new Power(z,2))
                .add(-2,z)
                .add(7);
        System.out.print("exp2=");
        System.out.println(exp2.toString());

        Node d = exp2.diff(z);
        System.out.print("d(exp)/dz=");
        System.out.println(d.toString());
        System.out.println(d.simplify().toString());

        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.print("f(x,y)=");
        System.out.println(circle.toString());

        Node dx = circle.diff(x);
        System.out.print("d f(x,y)/dx=");
        System.out.println(dx.toString());
        System.out.print("d f(x,y)/dy=");
        Node dy = circle.diff(y);
        System.out.println(dy.toString());
        System.out.println(dx.simplify().toString());
        System.out.println(dy.simplify().toString());


        Node last = new Sum()
                .add(new Sin(new Prod(8, x)))
                .add(new Cosine(new Sum(x, new Constant(2))));
        System.out.println(last.toString());
    }
}
