package xmass;

import java.awt.*;

public class Snowflake implements XmasShape {
    int x;
    int y;
    double scale;
    int opacity;

    Snowflake(int x, int y, double scale, int opacity){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.opacity = opacity;
    }

    @Override
    public void render(Graphics2D g2d) {
        // Set the color with opacity
        g2d.setColor(new Color(255, 255, 255, opacity));
        g2d.fillOval(0, 0, 10, 10);
    }

    @Override
    public void transform(Graphics2D g2d) {
        double centerOffset = scale * -5;
        g2d.translate(x - centerOffset,y - centerOffset);
        g2d.scale(scale,scale);
    }
}