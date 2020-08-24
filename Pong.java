import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Pong extends JPanel implements Runnable, KeyListener {

    JFrame myFrame;
    Ball spielball;
    Bumper leftBumper, rightBumper;
    File goal, hitItem, goalBumper, win, draw;     // to add a goal
    Font middel, small, big;    // for a different font size
    TimerTimer newTimer, shadow;    // for the timer and the shadow of the timer
    Item item;
    int randX, randY, counter, soundChecker, start, firstStart;


    public Pong(int w, int h) {

        initGame();

        this.setPreferredSize(new Dimension(w, h));
        this.setBackground(Color.BLACK);
        myFrame = new JFrame("Pong");
        myFrame.setLocation(100, 100);
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(this);
        myFrame.addKeyListener(this);
        myFrame.pack();
        myFrame.setVisible(true);

        Thread th = new Thread(this);
        th.start();
    }

    private void initGame() {
        randX = (int) ((Math.random() * 400) + 200);    // x-axis coordinate for the power-up
        randY = (int) ((Math.random() * 300) + 100);   // y-axis coordinate for the power-up
        counter = 0;    // prevent to the console gets spammed with text
        spielball = new Ball();
        leftBumper = new Bumper(65);
        rightBumper = new Bumper(720);
        goal = new File("sounds/umbrella.wav");
        hitItem = new File("sounds/dink.wav");
        goalBumper = new File("sounds/rock.wav");
        win = new File("sounds/win.wav");
        draw = new File("sounds/draw.wav");
        middel = new Font("", 1, 30);
        small = new Font("", 1, 20);
        big = new Font("", 1, 37);
        item = new Item(randX, randY, false);    // initialise the power-up --> invisible
        soundChecker = 0;
        start = 0;  // to check if the program starts
        firstStart = 0;  // to check if it starts the first time or is restarting the program
    }

    public void startGame() {
        newTimer = new TimerTimer();
        shadow = new TimerTimer();
        repaint();
        newTimer.start();
        shadow.start();
        start = 1;
        spielball.respawn();
        spielball.action(3);
        leftBumper.action(1);
        rightBumper.action(1);
        spielball.resetPowerUp();
        leftBumper.resetPowerUp();
        rightBumper.resetPowerUp();
        spielball.resetCounter();
        soundChecker = 0;
    }

    @Override
    public void run() {
        while (myFrame.isVisible()) {
            repaint();
            if (start == 1) {

                while (start == 1) {

                    moveObjects();
                    repaint();

                    if (spielball.setSound() == 1) {    // to look if the goal should be played
                        spielball.playSound(0);
                        Playgoal(goal);
                    }

                    if (spielball.getCounterRight() >= 1 || spielball.getCounterLeft() >= 1) {   // to give the power-up a chance to spawn in after getting at least 1 goal
                        if (!item.getVisible() && newTimer.getSeconds() > 0) {
                            int rand = (int) ((Math.random() * 2000) + 1);    // to randomize the chance a power-up spawns
                            if (rand == 20) {
                                randX = (int) ((Math.random() * 400) + 200);    // randomize the new spawn coordinates from the power-up
                                randY = (int) ((Math.random() * 300) + 100);
                                item.setXKoord(randX);
                                item.setYKoord(randY);
                                item.reArrange();   // rearrange the rectangle of the power-up
                                item.setVisible(true);  // let the power-up show up
                            }
                        }
                    }
                    if (item.getVisible()) {    // check if the power-up should be on the field
                        if (counter == 0) {
                            System.out.println("Spawned");
                            System.out.println("");
                            counter = 1;
                        }
                        if (spielball.getRect().intersects(item.getRect())) {   // check if the power-up is hit by the ball
                            Playgoal(hitItem);
                            System.out.println("Power Up");
                            System.out.println("");
                            item.setVisible(false);
                            counter = 0;    // to give a chance for a new power-up to spawn
                            spielball.powerUp();    // to give the ball and the bumpers a power-up boost
                            leftBumper.powerUp();
                            rightBumper.powerUp();
                        }
                    }

                    if (spielball.getRect().intersects(leftBumper.getRect()) || spielball.getRect().intersects(rightBumper.getRect())) {    // to check if the ball is hitting a Bumper
                        spielball.aendereXRichtung();
                        Playgoal(goalBumper);
                    }


                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            }

        }

    }

    public void moveObjects() {
        spielball.move();
        if (newTimer.getSeconds() > 0) {
            leftBumper.move();
            rightBumper.move();
        }
    }

    static void Playgoal(File sounds) { // to let the goal play
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(sounds));
            sound.start();


        } catch (Exception e) {
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start == 0 && firstStart == 0) { // to draw the start screen
            g.setColor(Color.WHITE);
            g.setFont(big);
            g.drawString("press r to start", 300, 300);
        }

        if (start == 1) {

            g.setColor(Color.BLACK);
            g.drawString("press r to start", 300, 300);

            // to draw the main elements of teh game


            g.setColor(Color.WHITE);
            g.fillOval(spielball.getXKoord(), spielball.getYKoord(), spielball.getSize(), spielball.getSize());
            g.fillRect(leftBumper.getXKoord(), leftBumper.getYKoord(), leftBumper.getWidth(), leftBumper.getHeight());
            g.fillRect(rightBumper.getXKoord(), rightBumper.getYKoord(), rightBumper.getWidth(), rightBumper.getHeight());

            // to draw the score

            g.setFont(small);
            g.drawString(Integer.toString(spielball.getCounterRight()), 600, 20);
            g.drawString(Integer.toString(spielball.getCounterLeft()), 200, 20);

            g.setColor(Color.WHITE);

            for (int i = 0; i < 800; i++) { // to draw the middle line
                g.drawString("|| ", 400, i);
            }
            if (item.getVisible()) {  // to draw the power-up
                g.setColor(Color.RED);
                g.fillRect(item.getXKoord(), item.getYKoord(), item.getWidth(), item.getHeight());
            }
            if (newTimer.getSeconds() > 0) {    // to display the timer and its timer-shadow
                g.setFont(big);
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(shadow.getSeconds()), 385, 32);
                g.setFont(middel);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(newTimer.getSeconds()), 390, 30);
            } else if (spielball.getCounterLeft() < spielball.getCounterRight()) {  // to display that the right player won
                spielball.action(0);
                leftBumper.action(0);
                rightBumper.action(0);
                item.setVisible(false);
                g.setColor(Color.BLACK);
                g.fillOval(spielball.getXKoord(), spielball.getYKoord(), spielball.getSize(), spielball.getSize());
                g.fillRect(leftBumper.getXKoord(), leftBumper.getYKoord(), leftBumper.getWidth(), leftBumper.getHeight());
                g.fillRect(rightBumper.getXKoord(), rightBumper.getYKoord(), rightBumper.getWidth(), rightBumper.getHeight());
                g.drawString(Integer.toString(spielball.getCounterRight()), 600, 20);
                g.drawString(Integer.toString(spielball.getCounterLeft()), 200, 20);
                for (int i = 0; i < 800; i++) {
                    g.drawString("|| ", 400, i);
                }
                g.setFont(middel);
                g.setColor(Color.WHITE);
                g.drawString("right Player Won", 300, 300);
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(spielball.getCounterLeft() + "           -           " + spielball.getCounterRight(), 300, 200);

                g.setColor(Color.WHITE);
                g.setFont(small);
                g.drawString("press r to start", 340, 500);

                if (soundChecker == 0) {
                    Playgoal(win);
                    soundChecker += 1;
                }
            } else if (spielball.getCounterLeft() > spielball.getCounterRight()) {  // to display that the left player won
                spielball.action(0);
                leftBumper.action(0);
                rightBumper.action(0);
                item.setVisible(false);
                g.setColor(Color.BLACK);
                g.fillOval(spielball.getXKoord(), spielball.getYKoord(), spielball.getSize(), spielball.getSize());
                g.fillRect(leftBumper.getXKoord(), leftBumper.getYKoord(), leftBumper.getWidth(), leftBumper.getHeight());
                g.fillRect(rightBumper.getXKoord(), rightBumper.getYKoord(), rightBumper.getWidth(), rightBumper.getHeight());
                g.drawString(Integer.toString(spielball.getCounterRight()), 600, 20);
                g.drawString(Integer.toString(spielball.getCounterLeft()), 200, 20);
                for (int i = 0; i < 800; i++) {
                    g.drawString("|| ", 400, i);
                }
                g.setFont(middel);
                g.setColor(Color.WHITE);
                g.drawString("left Player Won", 300, 300);
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(spielball.getCounterLeft() + "           -           " + spielball.getCounterRight(), 300, 200);

                g.setColor(Color.WHITE);
                g.setFont(small);
                g.drawString("press r to start", 340, 500);

                if (soundChecker == 0) {
                    Playgoal(win);
                    soundChecker += 1;
                }
            } else if (spielball.getCounterLeft() == spielball.getCounterRight()) { // to display that the game is a draw
                spielball.action(0);
                leftBumper.action(0);
                rightBumper.action(0);
                item.setVisible(false);
                g.setColor(Color.BLACK);
                g.fillOval(spielball.getXKoord(), spielball.getYKoord(), spielball.getSize(), spielball.getSize());
                g.fillRect(leftBumper.getXKoord(), leftBumper.getYKoord(), leftBumper.getWidth(), leftBumper.getHeight());
                g.fillRect(rightBumper.getXKoord(), rightBumper.getYKoord(), rightBumper.getWidth(), rightBumper.getHeight());
                g.drawString(Integer.toString(spielball.getCounterRight()), 600, 20);
                g.drawString(Integer.toString(spielball.getCounterLeft()), 200, 20);
                for (int i = 0; i < 800; i++) {
                    g.drawString("|| ", 400, i);
                }
                g.setFont(middel);
                g.setColor(Color.WHITE);
                g.drawString("DRAW", 360, 300);
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(spielball.getCounterLeft() + "           -           " + spielball.getCounterRight(), 300, 200);

                g.setColor(Color.WHITE);
                g.setFont(small);
                g.drawString("press r to start", 340, 500);

                if (soundChecker == 0) {
                    Playgoal(draw);
                    soundChecker += 1;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP)) {       // to set the direction of the movement up
            rightBumper.setDirection(1);
        } else if ((e.getKeyCode() == KeyEvent.VK_DOWN)) {      // to set the direction of the movement down
            rightBumper.setDirection(2);
        }

        if ((e.getKeyChar() == 'w')) {      // to set the direction of the movement up
            leftBumper.setDirection(1);
        } else if ((e.getKeyChar() == 's')) {       // to set the direction of the movement down
            leftBumper.setDirection(2);
        }

        if ((e.getKeyCode() == KeyEvent.VK_LEFT)) {     // to spawn a power-up at an instant
            if (newTimer.getSeconds() > 0) {
                spielball.setCounterRight();
                spielball.setCounterLeft();
                randX = (int) ((Math.random() * 400) + 200);
                randY = (int) ((Math.random() * 300) + 100);
                item.setXKoord(randX);
                item.setYKoord(randY);
                item.reArrange();
                item.setVisible(true);
            }
        } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT)) {  // to let the game end at an instant
            newTimer.setSeconds();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP)) {
            rightBumper.setDirection(0);
        } else if ((e.getKeyCode() == KeyEvent.VK_DOWN)) {
            rightBumper.setDirection(0);
        }

        if ((e.getKeyChar() == 'w')) {
            leftBumper.setDirection(0);
        } else if ((e.getKeyChar() == 's')) {
            leftBumper.setDirection(0);
        }

        if ((e.getKeyCode() == KeyEvent.VK_LEFT)) {

        } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT)) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 27) {
            System.exit(0);
        }
        if (e.getKeyChar() == 'r') {
            if (spielball.getDy() == 0) {
                System.out.println("start");
                System.out.println(" ");
                start = 0;
                firstStart = 1;
                startGame();
            }
        }
    }

    public static void main(String[] args) {
        new Pong(800, 600);
    }
}
