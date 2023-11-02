package test;

import game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GameTest {
    Game game;

    @BeforeEach
    void setUp(){
        game = new Game(10);
    }

    @Test
    @DisplayName("Testing spare in first 2 rounds")
    void testSpareInFirstTwoRounds() {
        game.roll(8);
        game.roll(2);
        game.roll(7);
        assertEquals(24, game.getScore());

    }
    @Test
    @DisplayName("Testing double strike after spare")
    void testingDoubleStrikeAfterSpare() {

        game.roll(8); game.roll(2);

        game.roll(10);
        game.roll(10);

        game.roll(3);
        game.roll(2);
        assertEquals(65, game.getScore());

    }

    @Test
    @DisplayName("Testing ten roung game with extra roll")
    void testingTenRoundsGameWithExtraRoll() {

        game.roll(1); game.roll(4);
        game.roll(4); game.roll(5);
        game.roll(6); game.roll(4);
        game.roll(5); game.roll(5);
        game.roll(10);
        game.roll(0); game.roll(1);
        game.roll(7); game.roll(3);
        game.roll(6); game.roll(4);
        game.roll(10);
        game.roll(2); game.roll(8); game.roll(6);

        assertEquals(133, game.getScore());

    }
    @Test
    @DisplayName("Testing ten roung game without extra roll")
    void testingTenRoundsGameWithoutExtraRoll() {

        game.roll(1); game.roll(4);
        game.roll(4); game.roll(5);
        game.roll(6); game.roll(4);
        game.roll(5); game.roll(5);
        game.roll(10);
        game.roll(0); game.roll(1);
        game.roll(7); game.roll(3);
        game.roll(6); game.roll(4);
        game.roll(10);
        game.roll(2); game.roll(7);

        assertEquals(125, game.getScore());

    }

}