import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private int width = 80;
    private int height = 40;
    private Arena arena;
    public Game(int level) {
        try {
            TerminalSize terminalSize = new TerminalSize(width, height);
            DefaultTerminalFactory terminalFactory = new
                    DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            this.arena = new Arena(width, height, level);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            TextGraphics graphics = screen.newTextGraphics();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();

    }
    private boolean death = false;
    public boolean end() { return death; }
    private boolean running = true;
    private int total_coins = 0;
    public void run() throws IOException {
        while (running == true) {
            draw();
            if (arena.endDueToDeath()) {
                death = true;
                screen.close();
                running = false;
            }
            if (arena.endDueToFinish()) {
                screen.close();
                running = false;
            }
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    death = true;
                    screen.close();
                    running = false;
            }
            if (key.getKeyType() == KeyType.EOF) {
                screen.close();
                running = false;
            }
            processKey(key);
        }
        total_coins += arena.getCoinsCollected();
    }
    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    public int getTotal_coins() {
        return total_coins;
    }
}
