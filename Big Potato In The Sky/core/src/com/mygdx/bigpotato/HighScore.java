package com.mygdx.bigpotato;

import com.badlogic.gdx.Preferences;


/**
 * Created by Spencer Hirasawa on 6/5/2015.
 */
public class HighScore {
    public int id;
    public int score;
    Preferences highScore;

    public HighScore(int id, int score){
        this.score = score;
        this.id = id;
    }

    public int getHighScore(){
        return highScore.getInteger("highScore");
    }

    public void setHighScore(int score){
        highScore.putInteger("highScore", score);
    }
}
