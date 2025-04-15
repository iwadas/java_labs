package xmass;
import java.awt.*;

public class Bubble implements XmasShape {
    int x;
    int y;
    double scale;
    Color fillColor1;
    Color fillColor2;

    Bubble(int x, int y, double scale, Color fileColor1, Color fillColor2){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor1 = fileColor1;
        this.fillColor2 = fillColor2;
    }

    @Override
    public void render(Graphics2D g2d) {


        GradientPaint gradient = new GradientPaint(0,0,fillColor1,100,100,fillColor2);
        // ustaw kolor wypełnienia
        g2d.setPaint(gradient);

//        I use gradient instead
        // ustaw kolor wypełnienia
//        g2d.setColor(fillColor);
        // ustaw kolor obramowania
//        g2d.setColor(lineColor);

//        narysuj okrag
        g2d.fillOval(0,0,100,100);
//        narysuj obramowanie
        g2d.drawOval(0,0,100,100);
    }

    @Override
    public void transform(Graphics2D g2d) {
        double centerOffset = scale * -50;
        g2d.translate(x - centerOffset,y - centerOffset);
        g2d.scale(scale,scale);
    }
}
