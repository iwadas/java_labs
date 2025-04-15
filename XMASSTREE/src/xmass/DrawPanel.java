package xmass;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel(){
//        background
        setBackground(new Color(0,0,100));
//        snowflakes ( background )
        for(int i = 0; i < 100; i++){
            shapes.add(new Snowflake((int)(Math.random()*600), (int)(Math.random()*550), Math.random() * 0.2, 50 + (int)(Math.random()*50)));
        }
//        tree
        shapes.add(new Tree(300, 350));
//        bubbles
        shapes.add(new Bubble(310,200, 0.2, new Color(220,38,38), new Color(153,27,27)));
        shapes.add(new Bubble(250,300, 0.4, new Color(94,234,212), new Color(21, 94, 234)));
        shapes.add(new Bubble(300,250, 0.3,  new Color(191, 192, 48), new Color(192, 147, 48)));
        shapes.add(new Bubble(200,400, 0.45, new Color(220,38,38), new Color(153,27,27)));
        shapes.add(new Bubble(240,220, 0.2, new Color(94,234,212), new Color(21, 94, 234)));
        shapes.add(new Bubble(330,360, 0.4,  new Color(191, 192, 48), new Color(192, 147, 48)));
//        star
        shapes.add(new Star(300, 78, Color.ORANGE, 110));
        shapes.add(new Star(300, 82, Color.YELLOW, 90));
        shapes.add(new Star(300, 86, Color.ORANGE, 70));

        // snowflakes (foreground)
        for(int i = 0; i < 100; i++){
            shapes.add(new Snowflake((int)(Math.random()*600), (int)(Math.random()*550), Math.random(), 100 + (int)(Math.random()*100)));
        }
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(XmasShape s:shapes){
            s.draw((Graphics2D)g2d);
        }
    }
}