package game;

import java.util.*;

public class Game {
    List<Integer> scoring = new ArrayList<>();

    private int round;
    private int score;

    public Game() {
        this.round = 1;
        this.score = 0;
    }

    private boolean checkingRoll(int maxValue, int value) {
        return value >= 0 && value <= maxValue;
    }

    private void scoreUpdate() {
        int tempScore = 0, strikeScore = 0, spareScoreMultiply = 0;

        for (int i = 0; i < scoring.size(); i += 2) {

            //Add current roll to score
            tempScore += scoring.get(i) + scoring.get(i + 1);

            //if roll was strike
            if (scoring.get(i) == 10 && !(i + 2 == scoring.size())) {
                spareScoreMultiply++;
                strikeScore += (scoring.get(i + 2) + scoring.get(i + 3)) * spareScoreMultiply;
            } else if ((scoring.get(i) + scoring.get(i + 1) == 10)) {//if roll was spare
                tempScore += strikeScore;

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

    public void roll() {
        Scanner scan = new Scanner(System.in);

        int firstRoll, secondRoll;

        do {
            System.out.printf("Round %s, type roll 0-10: ", round);

            firstRoll = scan.nextInt();

            if (!checkingRoll(10, firstRoll)) {
                System.out.println("Error");
            } else if (firstRoll == 10) {// if strike
                afterRoll(firstRoll);
                endRound(0);

            } else { //when first roll was 0-9
                do {
                    afterRoll(firstRoll);
                    scoreUpdate();
                    printscore();
                    System.out.printf("Round %s, type roll 0-%s: ", round, 10 - firstRoll);
                    secondRoll = scan.nextInt();

                } while (!checkingRoll(10 - firstRoll, secondRoll));
                endRound(secondRoll);
            }
        } while (!checkingRoll(10, firstRoll)); //type beetween 0 and 10
        scan.close();
    }

    private void endRound(int scoreRoll) {

        scoring.set(scoring.size() - 1, scoreRoll);
        scoreUpdate();
        printscore();
        if (round == 10) {
            lastRoll();
        }
        round++;
    }

    public void lastRoll() {
        Scanner scan = new Scanner(System.in);

        //if strike or spare then extra roll
        if (scoring.get(scoring.size() - 2) + scoring.get(scoring.size() - 1) == 10) {

            System.out.printf("Round %s, type roll 0-10: ", round);
            int lastRoll;
            do {
                lastRoll = scan.nextInt();
            } while (!checkingRoll(10, lastRoll)); //type between 0 and 10

            setScore(getScore() + lastRoll);
            printscore();
        }
        scan.close();
        gameOver();
    }

    void gameOver() {
        System.out.printf("game.Game Over your score is: %s", score);
    }


    void afterRoll(int scoreRoll) {
        scoring.add(scoreRoll);
        scoring.add(0);
    }

    void printscore() {
        System.out.println("Score is " + getScore());
    }

    public int getScore() {
        return score;
    }

    private void setScore(final int score) {
        this.score = score;
    }
}
