package Game;

import java.util.concurrent.Callable;

public class TurnTimer implements Callable<Boolean> {
    private final Game game;
    private final int currentTurnId;

    public TurnTimer(Game game, int currentTurnId) {
        this.game = game;
        this.currentTurnId = currentTurnId;
    }

    @Override
    public Boolean call() {
        try {
            for (int i = 10; i >= 1; i--) {
                // 이미 턴이 끝났으면 중단
                if (game.isTimeout() || currentTurnId != game.getTurnId()) {
                    return true; //값 받았음!
                }
                System.out.println("\n남은 시간: " + i + "초");
                Thread.sleep(1000);
            }
            game.setTimeout(true);
            return false ;// 타이머가 지남.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
