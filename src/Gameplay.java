
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SE62917
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon titleImage;

    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon leftmouth;
    private ImageIcon rightmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;

    private int lengthOfSnake = 3;

    private Timer timer;
    private int delay = 100;
    private int moves = 0;
    private int score = 0;

    private ImageIcon snakeimage;

    private int[] enemyXPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275,
        300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650,
        675, 700, 725, 750, 775, 800, 825, 850};
    private int[] enemyYPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325,
        350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private ImageIcon enemyimage;

    private Random random = new Random();

    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

        if (moves == 0) {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;

            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }

        // draw title image border
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);

        // draw the title image
        titleImage = new ImageIcon("img/snaketitle.jpg");
        titleImage.paintIcon(this, g, 25, 11);

        // draw border for gameplay
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 577);

        // draw background for the gameplay
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Scores: " + score, 780, 32);

        // draw length
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + lengthOfSnake, 780, 52);

        rightmouth = new ImageIcon("img/rightmouth.png");
        rightmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);

        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0 && right) {
                rightmouth = new ImageIcon("img/rightmouth.png");
                rightmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }

            if (a == 0 && left) {
                leftmouth = new ImageIcon("img/leftmouth.png");
                leftmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }

            if (a == 0 && down) {
                downmouth = new ImageIcon("img/downmouth.png");
                downmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }

            if (a == 0 && up) {
                upmouth = new ImageIcon("img/upmouth.png");
                upmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }

            if (a != 0) {
                snakeimage = new ImageIcon("img/snakeimage.png");
                snakeimage.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
        }

        enemyimage = new ImageIcon("img/enemy.png");
        if ((enemyXPos[xpos] == snakeXLength[0]) && (enemyYPos[ypos] == snakeYLength[0])) {
            score++;
            lengthOfSnake++;
            xpos = random.nextInt(34);
            ypos = random.nextInt(23);
        }
        enemyimage.paintIcon(this, g, enemyXPos[xpos], enemyYPos[ypos]);

        for (int b = 1; b < lengthOfSnake; b++) {
            if (snakeXLength[b] == snakeXLength[0] && snakeYLength[b] == snakeYLength[0]) {
                right = false;
                left = false;
                up = false;
                down = false;

                timer.stop();

                g.setColor(Color.RED);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Space to RESTART", 350, 340);
            }
        }

        g.dispose();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            moves = 0;
            score = 0;
            lengthOfSnake = 3;
            repaint();
            timer.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                left = true;
            } else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                up = true;
            } else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                down = true;
            } else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (right) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeYLength[r + 1] = snakeYLength[r];
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeXLength[r] = snakeXLength[r] + 25;
                } else {
                    snakeXLength[r] = snakeXLength[r - 1];
                }
                if (snakeXLength[r] > 850) {
                    snakeXLength[r] = 25;
                }
            }
            repaint();
        }

        if (left) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeYLength[r + 1] = snakeYLength[r];
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeXLength[r] = snakeXLength[r] - 25;
                } else {
                    snakeXLength[r] = snakeXLength[r - 1];
                }
                if (snakeXLength[r] < 25) {
                    snakeXLength[r] = 850;
                }
            }
            repaint();
        }

        if (up) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeXLength[r + 1] = snakeXLength[r];
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeYLength[r] = snakeYLength[r] - 25;
                } else {
                    snakeYLength[r] = snakeYLength[r - 1];
                }
                if (snakeYLength[r] < 75) {
                    snakeYLength[r] = 625;
                }
            }
            repaint();
        }

        if (down) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeXLength[r + 1] = snakeXLength[r];
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeYLength[r] = snakeYLength[r] + 25;
                } else {
                    snakeYLength[r] = snakeYLength[r - 1];
                }
                if (snakeYLength[r] > 625) {
                    snakeYLength[r] = 75;
                }
            }
            repaint();
        }
    }
}
