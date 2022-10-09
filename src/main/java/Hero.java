import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element {
    private Position position;
    public Position getPosition() { return this.position; }
    public void setPosition(int x, int y) { this.position.setX(x); this.position.setY(y); }
    public Hero(int x1, int y1) {
        super(x1, y1);
        position = new Position(x1, y1);
        this.health = 3;
    }
    private int health;
    public void draw(TextGraphics screen) {
        if (this.health == 3) { screen.setForegroundColor(TextColor.Factory.fromString("#00FF00")); }
        else if (this.health == 2) { screen.setForegroundColor(TextColor.Factory.fromString("#FFFF00")); }
        else { screen.setForegroundColor(TextColor.Factory.fromString("#FF0000")); }
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(position.getX(), position.getY()), "X");
    }
    public Position moveUp() {
        return new Position(this.position.getX(), this.position.getY() - 1);
    }
    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
    }
    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }
    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
