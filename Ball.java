import java.awt.Rectangle;

public class Ball {
    private int xKoord, yKoord, dx, dy, manyUps;
    private final int size;
    private final Rectangle rect;


    public Ball() {
        xKoord = 300;
        yKoord = 400;
        dx = 0;
        dy = 0;
        size = 20;
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

    public int getDy() {     // to check if the ball moves or not
        return dy;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void aendereXRichtung() {
        dx = -dx;
    }

    public void action(int i) {   // to stop the ball moving after the round ends or lot it reset
        dx = i + manyUps;
        dy = i + manyUps;
    }

    public void actionAngle(int i) {
        dy = i;
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
    }
}
