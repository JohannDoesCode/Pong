import java.awt.Rectangle;

public class Item {
    private int xKoord, yKoord;
    private final int width, height;
    private final Rectangle rect;
    private boolean visible;    // to declare if the object is supposed to be on the filed


    public Item(int xKoord, int yKoord, boolean visible) {
        this.xKoord = xKoord;
        this.yKoord = yKoord;
        this.width = 15;
        this.height = 15;
        this.visible = visible;
        rect = new Rectangle(xKoord, yKoord, width, height);
    }

    public int getYKoord() {
        return yKoord;
    }

    public int getXKoord() {
        return xKoord;
    }

    public void setXKoord(int xKoord) {
        this.xKoord = xKoord;
    }

    public void setYKoord(int yKoord) {
        this.yKoord = yKoord;
    }

    public void reArrange() {
        rect.setLocation(xKoord, yKoord);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setVisible(boolean visible) {   // to say if the object deserves to be on the filed
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }   // to ask if the object is already on the field

    public Rectangle getRect() {
        return rect;
    }

}
