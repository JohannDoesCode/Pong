import java.awt.Rectangle;

public class Bumper {
    private int xKoord, yKoord, width, height, v, manyUps, gravity, difficulty, speed;
    private Rectangle rect;


    public Bumper(int xKoord) {
        this.xKoord = xKoord;
        this.yKoord = 250;
        this.width = 15;
        this.height = 80;
        this.gravity = 1;       // velocity of the Bumpers
        this.v = 0;             // direction in which the Bumpers are commanded to go
        this.manyUps = 0;
        this.speed = 0;
        rect = new Rectangle(xKoord, yKoord, width, height);
    }

    public void setDifficulty(int diffi){
        difficulty = diffi;
    }

    public void setSpeed(int speed){
        this.speed = speed;

    }

    public int getYKoord() {
        return yKoord;
    }

    public int getXKoord() {
        return xKoord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void action(int i) {       // for the end to let the Bumpers not fall down
        gravity = i;
    } // to say if the Bumper should stop or start

    public Rectangle getRect() {
        return rect;
    }


    public void move(boolean movement) {
        if(movement) {
            if (yKoord <= 598 - height) {
                yKoord += gravity;
            }
            rect.setLocation(xKoord, yKoord);


            if (v == 0) {
                rect.setLocation(xKoord, yKoord);
            }

            if (yKoord > 600 - height) {
                yKoord -= (5 + manyUps);
            }

            if (yKoord < 0) {
                yKoord += (5 + manyUps);
            }

            if (v == 1) {   // let the ball moves up
                yKoord -= (5 + manyUps);
            }

            if (v == 2) {   // let the ball moves down
                yKoord += (5 + manyUps);
            }
        }

        if(!movement) {

            if (yKoord <= 598 - height) {
                yKoord += gravity;
            }
                rect.setLocation(xKoord, yKoord);

            if(difficulty == 1){
                if(speed > 3){
                    speed = 3 + manyUps;
                    yKoord += speed ;
                }
                if(speed < -3){
                    speed = -(3  + manyUps);
                    yKoord += speed ;
                }
                if (yKoord > 600 - height) {
                    yKoord -= speed ;
                }
                if (yKoord < 0){
                    yKoord += speed ;
                }

            }

            if(difficulty == 2){
                if(speed > 3){
                    speed = 3 + manyUps;
                    yKoord += speed ;
                }
                if(speed < -3){
                    speed = -(4  + manyUps);
                    yKoord += speed ;
                }
                if (yKoord > 600 - height) {
                    yKoord -= speed ;
                }
                if(yKoord < 0) {
                    yKoord += speed ;
                }
            }

            if(difficulty == 3){
                if(speed > 3){
                    speed = 4 + manyUps;
                    yKoord += speed ;
                }
                if(speed < -3){
                    speed = -(5  + manyUps);
                    yKoord += speed ;
                }
                if (yKoord > 600 - height) {
                    yKoord -= speed ;
                }
                if(yKoord < 0) {
                    yKoord += speed ;
                }
            }
        }
    }

    public void powerUp() {
        manyUps += 1;
    } // to let the power-ups increase the movement speed

    public void resetPowerUp() {
        manyUps = 0;
    }

    public void setDirection(int pV) {
        v = pV;
    }

}
