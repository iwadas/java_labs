package math;

import java.util.HashMap;
import static org.junit.Assert.*;

public class GeneralTest {

    @org.junit.Test
    public void buildAndPrint(){
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7);

        assertEquals("2.1*x^3.0 + x^2.0 + (-2)*x + 7", exp.toString());
    }

    @org.junit.Test
    public void buildAndEvaluate(){

        HashMap<Double, Double> functionValues = new HashMap<>();
//        expected values of the function
        functionValues.put(-5.0, -168.0);
        functionValues.put(-4.0, -90.0);
        functionValues.put(-3.0, -40.0);
        functionValues.put(-2.0, -12.0);
        functionValues.put(-1.0, 0.0);
        functionValues.put(0.0, 2.0);
        functionValues.put(1.0, 0.0);
        functionValues.put(2.0, 0.0);
        functionValues.put(3.0, 8.0);
        functionValues.put(4.0, 30.0);
        functionValues.put(5.0, 72.0);

        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x,3))
                .add(-2,new Power(x,2))
                .add(-1,x)
                .add(2);
        for(double v=-5;v<5;v+=1){
            x.setValue(v);
            assertEquals(functionValues.get(v), exp.evaluate(), 0.0001);
        }
    }

    @org.junit.Test
    public void defineCircle(){

        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.println(circle.toString());

//        generating 100 random points in circle
//        x^2 + 8x + y^2 + 4y + 16 = 0
//        (x+4)^2 - 16 + (y+2)^2 - 4 + 16 = 0
//        (x+4)^2 + (y+2)^2 = 4
//        circle with center (-4,-2) and radius 2
        HashMap<Double, Double> pointsInCircle = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            double angle = 2 * Math.PI * Math.random();
            double distance = 2.0 * Math.sqrt(Math.random());
            double xv = -4.0 + distance * Math.cos(angle);
            double yv = -2.0 + distance * Math.sin(angle);
            pointsInCircle.put(xv, yv);
        }

        for(int i = 0; i < 100; i++){
            x.setValue((double) pointsInCircle.keySet().toArray()[i]);
            y.setValue((double) pointsInCircle.values().toArray()[i]);
            double fv = circle.evaluate();
            assertTrue(fv < 0);
        }

    }

}