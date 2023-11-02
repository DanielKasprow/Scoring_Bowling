package game;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        int rounds = 10;
        Game game = new Game(rounds);
        for (int i = 0; i <= rounds; i++) {
            game.roundGame();
        }
    }
}