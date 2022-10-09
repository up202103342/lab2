import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin {
    private Position position;
    public Coin(int x1, int y1) {
        super();
        position = new Position(x1, y1);
    }
    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(this.position.getX(), position.getY()), "o");
    }

    public Position getPosition() {
        return this.position;
    }
}
