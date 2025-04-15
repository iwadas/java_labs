package math;

import static org.junit.Assert.assertEquals;

public class AdditionalFunctionsTest {

    @org.junit.Test
    public void testPrintAndEvaluate() {

        Variable x = new Variable("x");
        Variable y = new Variable("y");

        Node exp = new Sum()
                .add(new Sin(new Prod(x, new Constant(2))))
                .add(new Cos(new Sum(y, new Constant(Math.PI))))
                .add(2);


        assertEquals("sin(x*2) + cos(y + 3.14159) + 2", exp.toString());

        x.setValue(Math.PI/12);
        y.setValue(Math.PI);

//        sin(x*2) + cos(y + 3.14159) + 2
//        sin(PI/6) + cos(PI + PI) + 2
//          1/2           1          2 = 3,5

        assertEquals(3.5, exp.evaluate(), 0.0001);
    }


    @org.junit.Test
    public void testDiff() {
        Variable x = new Variable("x");

        Node exp = new Sum()
                .add(new Sin(new Prod(x, new Constant(2))))
                .add(new Cos(new Sum(x, new Constant(Math.PI))))
                .add(2);

        Node dx = exp.diff(x);
        assertEquals("cos(x*2)*2 + (-1)*sin(x + 3.14159)", dx.toString());

    }



}
