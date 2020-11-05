/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minessweeper2D;

import service.Settings;
import engine.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import service.Box;
import service.Coord;
import service.Ranges;

/**
 *
 * @author SK-2018
 */
public class MinesSweeper extends JFrame {

    /**
     * @param args the command line arguments
     */
    private JPanel panel;
    private Game game;
    private JLabel label;
    
    public static void main(String[] args) {
        new MinesSweeper();
    }
    
    public MinesSweeper() {
        game = new Game(Settings.COLS, Settings.ROWS, Settings.BOMBS);
        game.startGame();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBoxImage(coord).image, 
                                 coord.x * Settings.IMAGE_SIZE,
                                 coord.y * Settings.IMAGE_SIZE, rootPane);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { 
                int x = e.getX() / Settings.IMAGE_SIZE;
                int y = e.getY() / Settings.IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.leftMouseButtonPressed(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.rightMouseButtonPressed(coord);
                label.setText(getMessage());
                repaint();             
            }

            private String getMessage() {
                switch(game.getState()) {
                    case PLAYING: return "Think twice!";
                    case BOMBED: return "You lose... Very bad!!!";
                    case WINNED: return "Congratulations! You win!!!";
                    default: return null;
                }
            }
        });
        //panel.add(label);
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * Settings.IMAGE_SIZE,
                                             Ranges.getSize().y * Settings.IMAGE_SIZE));
        add(panel);
    }
    
    private void initLabel() {
        label = new JLabel("Wellcome");
        label.setFont(new Font("Arial", Font.BOLD, 22) {
        });
        label.setLayout(new BorderLayout());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.SOUTH);
    }
    
    private void initFrame() {
        pack();
        setTitle("Mines Sweeper");
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(getImage("icon"));
        setVisible(true);
    }
    
    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name());
    }
    
    private Image getImage(String name) {
        String path = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
        return icon.getImage();
    }
    
}
