import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        int level = 0;
        int total_coins = 0;
        Game game = new Game(level);
        game.run();
        total_coins += game.getTotal_coins();
        while (!(game.end())) {
            level += 1;
            game = new Game(level);
            game.run();
            total_coins += game.getTotal_coins();
        }
        System.out.println("Total coins collected: " + total_coins);
    }
}