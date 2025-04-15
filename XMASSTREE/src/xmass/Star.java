package xmass;
import java.awt.*;

public class Star implements XmasShape {
    int x;
    int y;
    int fontSize;
    Color fillColor;

    Star(int x, int y, Color fillColor, int fontSize){
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.setFont(new Font("Serif", Font.PLAIN, fontSize));
        g2d.drawString("\u2605", x - g2d.getFontMetrics().stringWidth("★") / 2, y + g2d.getFontMetrics().getAscent() / 2);
    }

    @Override
    public void transform(Graphics2D g2d) {}

}
