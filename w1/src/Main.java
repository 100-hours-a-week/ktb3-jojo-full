import Game.Game;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Game game = new Game();
        game.startGame();
    }
}