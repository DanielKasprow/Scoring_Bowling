package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        Game game = new Game();

        for(int i=1; i<11; i++){
            game.round();
        }
    }
}