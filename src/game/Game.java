package game;

import java.util.*;

public class Game {

    Scanner scan = new Scanner(System.in);

    List<Integer> scoring = new ArrayList<>();

    private final int maxRound;
    private int round;
    private int score;

    public Game(int maxRound) {
        this.round = 0;
        this.score = 0;
        this.maxRound = maxRound;
    }

    //checking value is between 0 and maxValue
    private boolean checkingRoll(int maxValue, int value) {
        return value >= 0 && value <= maxValue;
    }

    private int checkingNumberOfRoll(int maxRoll) {

        int scoreRoll;

        do {
            System.out.printf("Round %s, type roll 0-%s: ", round + 1, maxRoll);
            scoreRoll = scan.nextInt();
            if (!checkingRoll(maxRoll, scoreRoll)) {
                System.out.println("Error");
            }
        } while (!checkingRoll(maxRoll, scoreRoll));
        return scoreRoll;

    }

    public void roundGame() {
        int firstRoll, secondRoll;

        //first roll
        firstRoll = checkingNumberOfRoll(10);
        roll(firstRoll);

        //when first roll was not strike or is extra roll in last round
        if (firstRoll != 10 && round != 11) {
            secondRoll = checkingNumberOfRoll(10 - firstRoll);
            roll(secondRoll);
        }
    }

    public void roll(int scoreRoll) {

        //game over or extra roll in last round
        if (round == maxRound) {
            gameOver(scoreRoll);
            return;
        }

        scoring.add(scoreRoll);

        //first roll in round
        if (scoring.size() % 2 == 1) {
            scoring.add(0);

            //if not strike
            if (scoreRoll != 10) {
                scoreUpdate();
                printScore();
                scoring.remove(scoring.size() - 1);
            } else {// strike
                endRound();
            }

            //second roll in round
        } else {
            endRound();
        }
    }

    private void scoreUpdate() {
        int tempScore = 0, strikeScore = 0, spareScoreMultiply = 0;

        for (int i = 0; i < scoring.size(); i += 2) {

            //Add current rolls to score
            tempScore += scoring.get(i) + scoring.get(i + 1);

            //if roll was strike and not last round roll
            if (scoring.get(i) == 10 && !(i + 2 == scoring.size())) {
                spareScoreMultiply++;
                strikeScore += (scoring.get(i + 2) + scoring.get(i + 3)) * spareScoreMultiply;
            } else if ((scoring.get(i) + scoring.get(i + 1) == 10)) {//if roll was spare or strike in last round
                tempScore += strikeScore;
                //if not last round
                if (i + 2 != scoring.size()) {
                    tempScore += scoring.get(i + 2);
                    strikeScore = spareScoreMultiply = 0;
                }
            } else if (scoring.get(i) + scoring.get(i + 1) != 10) { //if roll was not strike or spare
                tempScore += strikeScore;
                strikeScore = spareScoreMultiply = 0;
            }

        }
        setScore(tempScore);
    }

    private void gameOver(int roll) {

        //if strike or spare then extra roll
        if (scoring.get(scoring.size() - 2) + scoring.get(scoring.size() - 1) == 10) {
            setScore(getScore() + roll);
        }

        System.out.printf("Game Over your score is: %s", score);
    }

    private void endRound() {
        scoreUpdate();
        printScore();
        round++;
    }

    private void printScore() {
        System.out.println("Score is " + getScore());
    }

    public int getScore() {
        return score;
    }

    private void setScore(final int score) {
        this.score = score;
    }
}
