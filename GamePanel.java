/* =========================================================
   GAME PANEL
   ========================================================= */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener,
        KeyListener, MouseListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Timer timer;

    private Player player;

    private ArrayList<Apple> apples;

    private Random random;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private int score = 0;
    private int highScore = 0;
    private String hsUsername = NewtonAppleDodgeGame.username;
    
    public String name;
    
    public int lives = 3;

    public boolean gameOver = true;

    private JButton restartButton;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(180, 230, 255));
        setLayout(null);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        player = new Player((WIDTH/2) - 50, HEIGHT - 120);

        apples = new ArrayList<>();
        random = new Random();
        
        name = NewtonAppleDodgeGame.username;

        loadHighScore();

        // Restart Button
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(320, 260, 160, 40);
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());
        
        add(restartButton);

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    /* =========================================================
       GAME LOOP
       ========================================================= */

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {
            // Movement
            if (leftPressed) {
                if(!downPressed)
                {
                    player.moveLeft();
                    player.speed = (int)(player.speed*1.1);
                }
                else
                {
                    player.crouchMoveLeft();
                    player.crouchSpeed = (int)(player.crouchSpeed*1.1);
                }
            }
            

            if (rightPressed) {
                if(!downPressed)
                {
                    player.moveRight(WIDTH);
                    player.speed = (int)(player.speed*1.1);
                }
                else
                {
                    player.crouchMoveRight(WIDTH);
                    player.crouchSpeed = (int)(player.crouchSpeed*1.1);
                }
            }
            
            
            
            if(player.y >= HEIGHT-120)
            {
                player.y = HEIGHT-120;
                player.upSpeed = 0;
                player.onGround = true;
            }
            else
            {
                player.onGround = false;
                player.upSpeed -= player.gravity;
            }
            
            
            if(upPressed && player.onGround)
            {
                player.upSpeed = 17;
            }
            
            player.y -= player.upSpeed;
            
            if(downPressed)
            {
                player.height = 40;
                player.width = 30;
                player.y = HEIGHT - 80;
            }

            // Spawn apples
            if (random.nextInt(20) == 0) {

                int appleX = random.nextInt(WIDTH - 30);
                int speed = 3 + random.nextInt(5);

                apples.add(new Apple(appleX, -30, speed));
            }

            // Update apples
            for (int i = 0; i < apples.size(); i++) {

                Apple apple = apples.get(i);

                apple.update();

                // Collision Detection
                if (apple.getBounds().intersects(player.getBounds())) {
    
                    apples.remove(i);
                    lives--;

                    
                }
                
                if(lives == 0)
                {
                    gameOver = true;
                    restartButton.setVisible(true);
                    
                    if (score > highScore) {
                        highScore = score;
                        hsUsername = NewtonAppleDodgeGame.username;
                        saveHighScore();
                    }
                }

                // Remove offscreen apples
                if (apple.offScreen(HEIGHT)) {
                    apples.remove(i);
                    i--;
                    score++;
                }
            }
        }

        repaint();
    }

    /* =========================================================
       DRAWING
       ========================================================= */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        
        // Sky background
        g.setColor(new Color(180, 230, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Ground
        g.setColor(new Color(50, 180, 50));
        g.fillRect(0, HEIGHT - 40, WIDTH, 40);


        // Tree
        drawTree(g);

        // Draw player
        player.draw(g);

        // Draw apples
        for (Apple apple : apples) {
            apple.draw(g);
        }

        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 22));

        g.drawString("Score: " + score, 20, 30);
        g.drawString("High Score: " + highScore + ", by " + hsUsername, 20, 60);
        g.drawString("Username: " + NewtonAppleDodgeGame.username, 20, 90);
        
        //lives
        
        g.drawString("Lives: ", WIDTH*2/3, 30);
        if(lives == 3)
        {
            g.setColor(new Color(36, 255, 51));
            g.fillOval(WIDTH*19/24, 10, 25, 25);
            g.fillOval(WIDTH*19/24 + 30, 10, 25, 25);
            g.fillOval(WIDTH*19/24 + 60, 10, 25, 25);
        }
        else if(lives == 2)
        {
            g.setColor(new Color(36, 255, 51));
            g.fillOval(WIDTH*19/24, 10, 25, 25);
            g.fillOval(WIDTH*19/24 + 30, 10, 25, 25);
        }
        
        else if(lives == 1)
        {
            g.setColor(new Color(36, 255, 51));
            g.fillOval(WIDTH*19/24, 10, 25, 25);
        }
        else if(lives == 0)
        {
            g.setColor(new Color(180, 230, 255));
            g.fillOval(WIDTH*3/4, 100, 20, 20);
        }
        // Game Over
        if (gameOver) {

            g.setColor(new Color(0, 0, 0, 170));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 42));

            g.drawString("GAME OVER", 260, 220);

            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.drawString("Final Score: " + score, 300, 320);
        }
    }

    private void drawTree(Graphics g) {

        // Trunk
        g.setColor(new Color(120, 70, 20));
        g.fillRect(100, 360, 50, 200);

        // Leaves
        g.setColor(new Color(20, 150, 40));
        g.fillOval(40, 280, 180, 140);
    }

    /* =========================================================
       FILE I/O
       ========================================================= */

    private void saveHighScore() {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter("highscore.txt"));
            PrintWriter writerU = new PrintWriter(new FileWriter("hsUsername.txt"));
            
            writer.println(highScore);
            writerU.println(NewtonAppleDodgeGame.username);

            writer.close();
            writerU.close();

        } catch (IOException e) {
            System.out.println("Could not save high score.");
        }
    }

    private void loadHighScore() {

        try {

            BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
            BufferedReader readerU = new BufferedReader(new FileReader("hsUsername.txt"));
            
            highScore = Integer.parseInt(reader.readLine());
            hsUsername = readerU.readLine();

            reader.close();
            readerU.close();

        } catch (Exception e) {
            highScore = 0;
        }
    }

    /* =========================================================
       RESTART GAME
       ========================================================= */

    private void restartGame() {

        apples.clear();

        player = new Player(WIDTH / 2 - 30, HEIGHT - 100);

        score = 0;
        
        lives = 3;

        gameOver = false;

        restartButton.setVisible(false);

        requestFocusInWindow();
    }

    /* =========================================================
       KEY EVENTS
       ========================================================= */

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
            
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
            //player.gravity = 4;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
            player.speed = 5;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
            player.speed = 5;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
            player.upSpeed = 5;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
            player.height = 80;
            player.y = HEIGHT - 120;
            player.width = 50;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /* =========================================================
       MOUSE EVENTS
       ========================================================= */

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}