package xmass;

import java.awt.*;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;

public class Light implements XmasShape {
    int xStart;
    int yStart;
    int xEnd;
    int yEnd;

    Light(int xStart, int yStart, int xEnd, int yEnd){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3, CAP_ROUND,JOIN_MITER));
        g2d.drawLine(xStart, yStart, xEnd, yEnd);
    }

    @Override
    public void transform(Graphics2D g2d) {}
}