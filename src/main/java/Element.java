import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    private Position position;
    public Position getPosition() { return this.position; }
    public void setPosition(int x, int y) { this.position.setX(x); this.position.setY(y); }
    public Element(int x1, int y1) {
        position = new Position(x1, y1);
    }
    public abstract void draw(TextGraphics graphics);
}
