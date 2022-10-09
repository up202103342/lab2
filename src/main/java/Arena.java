import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    private int width;
    private int height;
    private int level;
    public Arena(int width, int height, int level) {
        hero = new Hero(10, 10);
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.level = level;
        this.monsters = createMonsters();
        this.coins_taken = 0;
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < this.level + 3; i++) {
            monsters.add(new Monster(random.nextInt(width - 3) + 1, random.nextInt(height - 2) + 1));
        }
        for (Monster monster : monsters)  {
            if ((monster.getPosition().getX() + monster.getPosition().getY()) % 2 == 1) {
                monster.setPosition(monster.getPosition().getX() + 1, monster.getPosition().getY());
            }
        }
        return monsters;
    }
    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                coins_taken += 1;
                break;
            }
        }
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        Position p = null;
        for (int i = 0; i < 10; i++) {
            p = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            boolean position_exists = false;
            for (Coin coin : coins) {
                if (p.equals(coin.getPosition())) { position_exists = true; }
            }
            if (p.equals(hero.getPosition())) { position_exists = true; }
            while (position_exists) {

                System.out.print(".");
                p = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                position_exists = false;
                for (Coin coin : coins) {
                    if (p.equals(coin.getPosition())) { position_exists = true; }
                }
                if (p.equals(hero.getPosition())) { position_exists = true; }
            }
            coins.add(new Coin(p.getX(), p.getY()));
        }
        return coins;
    }
    private boolean canHeroMove(Position pos){
        if (pos.getX() < 0) { return false; }
        if (pos.getX() > width) { return false; }
        if (pos.getY() < 0) { return false; }
        if (pos.getY() >= height) { return false; }
        for (Wall wall : walls) {
            if (wall.getPosition().equals(pos)) { return false; }
        }
        return true;
    }
    private void moveHero(Position pos) {
        if (canHeroMove(pos)) {
            hero.setPosition(pos.getX(), pos.getY());
        }
    }

    public int getCoinsCollected() {
        return coins_taken;
    }
    private int coins_taken;
    private void moveMonster(Monster m, Position pos) {
        m.setPosition(pos.getX(), pos.getY());
    }

    public boolean endDueToDeath() {
        if (hero.getHealth() == 0) { return true; }
        return false;
    }
    public boolean endDueToFinish() {
        if (coins.size() == 0) { return true; }
        return false;
    }

    private boolean verifyMonsterCollisions(Monster m) {
        if (m.getPosition().equals(hero.getPosition())) { return true; }
        return false;
    }
    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(hero.moveUp());
        }
        else if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(hero.moveDown());
        }
        else if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(hero.moveLeft());
        }
        else if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(hero.moveRight());
        }
        retrieveCoins();
        for (Monster monster : monsters)  {
            moveMonster(monster, monster.move(hero.getPosition()));
        }
        for (Monster monster : monsters)  {
            if (verifyMonsterCollisions(monster)) {
                hero.setHealth(hero.getHealth() - 1);
                monsters.remove(monster);
                break;
            }
        }
    }
    public void draw(TextGraphics screen) {
        screen.setBackgroundColor(TextColor.Factory.fromString("#A08000"));
        screen.fillRectangle(new TerminalPosition(0, 0), new
                TerminalSize(width, height), ' ');

        hero.draw(screen);
        for (Wall wall : walls)  {
            wall.draw(screen);
        }
        for (Monster monster : monsters)  {
            monster.draw(screen);
        }
        for (Coin coin : coins)  {
            coin.draw(screen);
        }
    }
}
