import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {
    private Position position;
    public Wall(int x1, int y1) {
        super(x1, y1);
        position = new Position(x1, y1);
    }
    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#000000"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(this.position.getX(), position.getY()), "#");
    }
}
