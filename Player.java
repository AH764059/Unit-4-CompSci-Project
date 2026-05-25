/* =========================================================
   PLAYER CLASS (INHERITS GAMEOBJECT)
   ========================================================= */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.net.URL;

class Player extends GameObject {

    public int speed = 5;
    public int upSpeed = 0;
    public int crouchSpeed = 3;
    public boolean onGround = true;
    public int gravity = 1;
    public Image img;

    public Player(int x, int y) {
        super(x, y, 50, 80, Color.BLUE);
        
        try {
            URL url = Player.class.getResource("Images/newton.png");
            
            if (url != null) {
                this.img = new ImageIcon(url).getImage();
            } else {
                System.out.println("Error: /resources/newton.png not found in file tree.");
            }
        } catch (Exception e) {
            System.out.println("Error loading player image: " + e.getMessage());
        }
    }

    @Override
    public void update() {
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }
    
    public void crouchMoveLeft() {
        x -= crouchSpeed;
        if (x < 0) x = 0;
    }

    public void moveRight(int panelWidth) {
        x += speed;
        if ((x + width) > panelWidth) {
            x = panelWidth - width;
        }
    }
    public void crouchMoveRight(int panelWidth) {
        x += crouchSpeed;
        if ((x + width) > panelWidth) {
            x = panelWidth - width;
        }
    }
    
    public void jump()
    {
        y -= upSpeed;
    }

    @Override
    public void draw(Graphics g) {

        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }
}