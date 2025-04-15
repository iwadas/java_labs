package xmass;

import java.awt.*;

public class Trunk implements XmasShape {
    int x;
    int y;
    double scale;
    Color fillColor1;
    Color fillColor2;

    Trunk(int x, int y, double scale, Color fillColor1, java.awt.Color fillColor2){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor1 = fillColor1;
        this.fillColor2 = fillColor2;
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0,0,fillColor1,60,60,fillColor2);
        // ustaw kolor wypełnienia
        g2d.setPaint(gradient);
        g2d.fillPolygon(new int[]{0, 0, 40, 40}, new int[]{0, 80, 80, 0}, 4);
        transform(g2d);
    }

    @Override
    public void transform(Graphics2D g2d) {
        double centerOffsetX = scale * -20;
        double centerOffsetY = scale * -40;
        g2d.translate(x + centerOffsetX, y + centerOffsetY);
        g2d.scale(scale, scale);

    }
}