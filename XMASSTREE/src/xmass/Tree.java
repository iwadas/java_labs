package xmass;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class Tree implements XmasShape {

    List<XmasShape> shapes = new ArrayList<>();
    int x;
    int y;

    Tree(int x, int y){
        this.x = x;
        this.y = y;
        shapes.add(new Trunk(0, y-200, 1, new Color(69, 26, 3), new Color(161, 98, 7) ));
        for(int i = 0, j = 0; i < 5; i++, j-=50){
            double scale = 6 - i;
            shapes.add(new Branch(0, j, scale, Color.GREEN, Color.BLACK));
//            branch basic width is 40 and height is 40
            int xStart, yStart, xEnd, yEnd;
            if(i % 2 == 0){
                xStart = (int) (-20 * scale); // left bottom
                yStart = (int) (20 * scale); // left bottom
                xEnd = (int) (16 * scale); // center of right angle
                yEnd = (int) (12 * scale); // center of right angle
            } else {
                xStart = (int) (20 * scale); // left bottom
                yStart = (int) (20 * scale); // left bottom
                xEnd = (int) (-16 * scale); // center of right angle
                yEnd = (int) (12 * scale); // center of right angle
            }
            shapes.add(new Light(xStart, j + yStart, xEnd, yEnd + j));
        }
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
    }

    @Override
    public void render(Graphics2D g2d) {
        for(XmasShape s:shapes) s.draw(g2d);
    }

}
