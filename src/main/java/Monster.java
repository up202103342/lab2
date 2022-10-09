import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

public class Monster {
    private Position position;
    public void setPosition(int x, int y) { this.position.setX(x); this.position.setY(y); }
    public Monster(int x1, int y1) {
        super();
        position = new Position(x1, y1);
    }
    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#000090"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(this.position.getX(), position.getY()), "Y");
    }

    private double distanceToPosition(Position a, Position b) {
        return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()));
    }

    public Position move(Position p) {
        List<Integer> where = new ArrayList<>();
        where.add(0);
        where.add(1);
        where.add(2);
        where.add(3);
        if (this.position.getX() == 1) { where.remove(3); }
        if (this.position.getY() == 38) { where.remove(2); }
        if (this.position.getX() == 78) { where.remove(1); }
        if (this.position.getY() == 1) { where.remove(0); }
        if (distanceToPosition(this.position, p) < 20 && Math.random() > 0.99) {
            if (p.getX() > this.position.getX() && p.getY() == this.position.getY()) { return new Position(this.position.getX() + 1, this.position.getY()); }
            if (p.getX() < this.position.getX() && p.getY() == this.position.getY()) { return new Position(this.position.getX() - 1, this.position.getY()); }
            if (p.getX() == this.position.getX() && p.getY() > this.position.getY()) { return new Position(this.position.getX(), this.position.getY() + 1); }
            if (p.getX() == this.position.getX() && p.getY() < this.position.getY()) { return new Position(this.position.getX(), this.position.getY() - 1); }
            if (p.getX() > this.position.getX() && p.getY() > this.position.getY()) {
                if (Math.random() > 0.5) { return new Position(this.position.getX() + 1, this.position.getY()); }
                else { return new Position(this.position.getX(), this.position.getY() + 1); }
            }
            if (p.getX() > this.position.getX() && p.getY() < this.position.getY()) {
                if (Math.random() > 0.5) { return new Position(this.position.getX() + 1, this.position.getY()); }
                else { return new Position(this.position.getX(), this.position.getY() - 1); }
            }
            if (p.getX() < this.position.getX() && p.getY() > this.position.getY()) {
                if (Math.random() > 0.5) { return new Position(this.position.getX() - 1, this.position.getY()); }
                else { return new Position(this.position.getX(), this.position.getY() + 1); }
            }
            if (p.getX() < this.position.getX() && p.getY() < this.position.getY()) {
                if (Math.random() > 0.5) { return new Position(this.position.getX() - 1, this.position.getY()); }
                else { return new Position(this.position.getX(), this.position.getY() - 1); }
            }
        }
        if (where.size() == 4) {
            if (Math.random() > 0.75) { return new Position(this.position.getX() - 1, this.position.getY()); }
            if (Math.random() > 0.66) { return new Position(this.position.getX() + 1, this.position.getY()); }
            if (Math.random() > 0.5) { return new Position(this.position.getX(), this.position.getY() - 1); }
            return new Position(this.position.getX(), this.position.getY() + 1);
        }
        int n = 0;
        if (where.size() == 3) {
            if (Math.random() > 0.66) { n = where.get(0); }
            else if (Math.random() > 0.5) { n = where.get(1); }
            else { n = where.get(2); }
        }
        else if (where.size() == 2) {
            if (Math.random() > 0.5) { n = where.get(0); }
            else { n = where.get(1); }
        }
        if (n == 0) { return new Position(this.position.getX(), this.position.getY() - 1); }
        if (n == 1) { return new Position(this.position.getX() + 1, this.position.getY()); }
        if (n == 2) { return new Position(this.position.getX(), this.position.getY() + 1); }
        return new Position(this.position.getX() - 1, this.position.getY());
    }

    public Position getPosition() {
        return this.position;
    }
}
