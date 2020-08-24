import java.awt.Rectangle;

public class Ball {
    private int xKoord, yKoord, dx, dy, size, counterLeft, counterRight, sp, manyUps;
    private Rectangle rect;


    public Ball() {
        xKoord = 300;
        yKoord = 400;
        dx = 0;
        dy = 0;
        size = 20;
        counterLeft = 0;    // goal counter left
        counterRight = 0;   // goal counter right
        sp = 0;             // to declare if the sound should be played or not
        manyUps = 0;    // to count how much power-ups are picked up
        rect = new Rectangle(xKoord, yKoord, size, size);
    }

    public int getXKoord() {
        return xKoord;
    }

    public int getYKoord() {
        return yKoord;
    }

    public int getSize() {
        return size;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void aendereXRichtung() {
        dx = -dx;
    }

    public int getCounterLeft() {
        return counterLeft;
    }

    public int getCounterRight() {
        return counterRight;
    }

    public void setCounterRight() {
        counterRight += 1;
    }   // just for a key-command

    public void setCounterLeft() {
        counterLeft += 1;
    }   // just for a key-command

    public void resetCounter() {
        counterLeft = 0;
        counterRight = 0;
    }   // for the restart

    public void action(int i) {   // to stop the ball moving after the round ends or lot it reset
        dx = i + manyUps;
        dy = i + manyUps;

    }

    public void actionAngle(int i) {
        dy = i;
    }

    public int getDy() {     // to check if the ball moves or not
        return dy;
    }

    public void powerUp() {     // to let the power-ups increase the movement speed on the x-axis
        manyUps += 1;
        if (dx > 0) {
            dx += manyUps;
        }
        if (dx < 0) {
            dx -= manyUps;
        }
    }

    public void resetPowerUp() {
        manyUps = 0;
    }

    public void move() {

        if (xKoord > 780) {     // let it respawn after a goal
            respawn();
            action(3);
            counterLeft += 1;
        }

        if (xKoord < 0) {       // let it respawn after a goal

            respawn();
            action(3);
            counterRight += 1;
        }

        if (yKoord < 0 || yKoord > 580) {
            dy = -dy;
        }

        xKoord += dx;
        yKoord += dy;
        rect.setLocation(xKoord, yKoord);
    }

    public void respawn() {
        xKoord = 400;
        yKoord = (int) ((Math.random() * 400) + 100);
        dx = -dx;
        dy = -dy;
        rect.setLocation(xKoord, yKoord);
        System.out.println(+counterLeft + " - " + counterRight);
        System.out.println("");
        int spp = 1;
        playSound(spp);

    }

    public void playSound(int spp) {    // play a sound if a goal is achieved
        this.sp = spp;
    }

    public int setSound() {     // to check if the sound is already played
        return sp;
    }

}
