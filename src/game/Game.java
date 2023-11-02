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
        if (firstRoll != 10 && round != 10) {
            secondRoll = checkingNumberOfRoll(10 - firstRoll);
            roll(secondRoll);
        }

    }

    public void roll(int scoreRoll) {

        //game over with extra roll
        if (round == maxRound) {
            if (scoring.get(scoring.size() - 2) + scoring.get(scoring.size() - 1) == 10) {
                gameOver(scoreRoll);
            }
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

        } else { //second roll in round
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
            printScore();
        }
        System.out.println("Score for each round:");
        printGameOverScore();
        System.out.print(getScore());
        System.out.printf("\nGame Over your final score is: %s", getScore());

    }

    private void printGameOverScore() {
        int scorePrintGameOver = 0;
        for (int i = 0; i < scoring.size(); i += 2) {

            //if strike
            if (scoring.get(i) == 10) {
                scorePrintGameOver += scoreCheckingStrike(scoring.subList(i + 2, scoring.size()));
                System.out.print(scorePrintGameOver + " | ");
            } else if (scoring.get(i) + scoring.get(i + 1) == 10 && !(i + 2 == scoring.size())) {//if spare
                scorePrintGameOver += scoring.get(i) + scoring.get(i + 1) + scoring.get(i + 2);
                System.out.print(scorePrintGameOver + " | ");
            } else {
                scorePrintGameOver += scoring.get(i) + scoring.get(i + 1);
                System.out.print(scorePrintGameOver + " | ");
            }
        }
    }

    private int scoreCheckingStrike(List<Integer> tempScoring) {

        //if strike
        if (tempScoring.get(0) == 10) {
            //recurrence
            return 10 + scoreCheckingStrike(tempScoring.subList(2, tempScoring.size()));
        } else if (tempScoring.get(0) + tempScoring.get(1) == 10 && (2 == scoring.size())) {// if spare
            return 10 + tempScoring.get(0) + tempScoring.get(1) + tempScoring.get(2);

        } else {
            return 10 + tempScoring.get(0) + tempScoring.get(1);
        }
    }

    private void endRound() {
        scoreUpdate();
        printScore();
        round++;

        //game over without extra roll
        if (round == maxRound && scoring.get(scoring.size() - 2) + scoring.get(scoring.size() - 1) != 10) {
            gameOver(0);
        }
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
