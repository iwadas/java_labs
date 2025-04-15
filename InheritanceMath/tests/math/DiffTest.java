package math;

import static org.junit.Assert.*;

public class DiffTest {

    @org.junit.Test
    public void diffPoly() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2, new Power(x, 3))
                .add(new Power(x, 2))
                .add(-2, x)
                .add(7);
        System.out.println(exp.toString());

//        przy implementacji metody mul oraz add od razu dokonalem optymalizacji przez co funkcja simplify w tym przypadku jest zbedna
        assertEquals("2*x^3.0 + x^2.0 + (-2)*x + 7", exp.toString());

        Node d = exp.diff(x);
        assertEquals("6*x^2.0 + 2*x + (-2)", d.toString());
    }

    @org.junit.Test
    public void diffCircle() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        assertEquals("x^2.0 + y^2.0 + 8*x + 4*y + 16",  circle.toString());
        Node dx = circle.diff(x);
        assertEquals("2*x + 8", dx.toString());
        Node dy = circle.diff(y);
        assertEquals("2*y + 4", dy.toString());

    }

}
