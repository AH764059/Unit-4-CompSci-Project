/* =========================================================
   APPLE CLASS (INHERITS GAMEOBJECT)
   ========================================================= */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

class Apple extends GameObject {

    private int speed;

    public Apple(int x, int y, int speed) {
        super(x, y, 30, 30, Color.RED);
        this.speed = speed;
    }

    @Override
    public void update() {
        y += speed;
    }

    @Override
    public void draw(Graphics g) {

        // Apple body
        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);

        // Stem
        g.setColor(new Color(90, 50, 20));
        g.fillRect(x + 13, y - 5, 4, 10);

        // Leaf
        g.setColor(Color.GREEN);
        g.fillOval(x + 16, y, 10, 5);
    }

    public boolean offScreen(int panelHeight) {
        return y > panelHeight;
    }
}