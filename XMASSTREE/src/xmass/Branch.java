package xmass;

import java.awt.*;

public class Branch implements XmasShape {
    int x;
    int y;
    double scale;
    Color fillColor1;
    Color fillColor2;

    Branch(int x, int y, double scale, Color fillColor1, Color fillColor2){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor1 = fillColor1;
        this.fillColor2 = fillColor2;
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0,0,fillColor1,40,40,fillColor2);
        // ustaw kolor wypełnienia
        g2d.setPaint(gradient);
        g2d.fillPolygon(new int[]{0, 20, 40}, new int[]{40, 0, 40}, 3);
        transform(g2d);
    }

    @Override
    public void transform(Graphics2D g2d) {
        double centerOffset = scale * -20;
        System.out.println(x + centerOffset);
        g2d.translate(x + centerOffset, y + centerOffset);
        g2d.scale(scale, scale);

    }
}
