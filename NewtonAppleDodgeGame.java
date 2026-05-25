/*
 * NewtonAppleDodgeGame.java
 *
 * A complete Java Swing game:
 * - Dodge the falling apples
 * - Player is Isaac Newton
 * - Arrow key movement
 * - Collision detection
 * - Buttons + mouse events
 * - Abstract classes + inheritance
 * - GUI design
 * - Files I/O (high score saving)
 *
 * HOW TO RUN:
 * 1. Save as NewtonAppleDodgeGame.java
 * 2. Compile:
 *      javac NewtonAppleDodgeGame.java
 * 3. Run:
 *      java NewtonAppleDodgeGame
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class NewtonAppleDodgeGame extends JFrame {

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        JTextField EnterUN = new JTextField(20);
        public static String username;
    
    public NewtonAppleDodgeGame() {
        setTitle("Isaac Newton - Dodge the Apples!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        //screen1
        GamePanel panel = new GamePanel();
        add(panel);
        
        //screen2
        JPanel loginScreen = new JPanel();
        loginScreen.setLayout(null); 
        JLabel explanation = new JLabel("Entering username allows high score to be saved");
        JLabel prompt = new JLabel("Enter Username: ");
        JButton playBtn = new JButton("Proceed to game");
        
        JLabel controls = new JLabel("Controls: ");
        JLabel instructions1 = new JLabel("Left/Right Arrow Keys - Moves Left/Right");
        JLabel instructions2 = new JLabel("Up Arrow Key - Jump");
        JLabel instructions3 = new JLabel("Down Arrow Key - Shrink (speed decreases)");
        
        explanation.setBounds(50, 100, 500, 50);
        prompt.setBounds(50, 50, 150, 50);
        EnterUN.setBounds(180, 60, 200, 30);
        playBtn.setBounds(400, 60, 200, 30);
        controls.setBounds(50, 150, 300, 50);
        instructions1.setBounds(50, 200, 400, 50);
        instructions2.setBounds(50, 250, 400, 50);
        instructions3.setBounds(50, 300, 400, 50);
        
        loginScreen.add(explanation);
        loginScreen.add(EnterUN);
        loginScreen.add(prompt);
        loginScreen.add(playBtn);
        
        loginScreen.add(controls);
        loginScreen.add(instructions1);
        loginScreen.add(instructions2);
        loginScreen.add(instructions3);
        
        mainPanel.add(loginScreen, "1");
        mainPanel.add(panel, "2");
        
        playBtn.addActionListener(e -> 
        {
            cardLayout.show(mainPanel, "2");
            username = EnterUN.getText();
            panel.gameOver = false;
            panel.requestFocusInWindow();
            
        });
        
        add(mainPanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewtonAppleDodgeGame());
    }
}