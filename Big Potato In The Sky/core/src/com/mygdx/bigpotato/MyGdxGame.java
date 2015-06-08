package com.mygdx.bigpotato;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Spencer Hirasawa on 5/6/2015.
 */
public class MyGdxGame extends Game{
    GameScreen gameScreen;
    MainMenu mainMenuScreen;
    private static Preferences highScore;

    @Override
    public void create(){
        gameScreen = new GameScreen(this, true);
        mainMenuScreen = new MainMenu(this, false);
        highScore = Gdx.app.getPreferences("BigPotato");
        if(!highScore.contains("highScore")) {
            highScore.putInteger("highScore", 0);
        }
        this.setScreen(mainMenuScreen);
    }

    public int getHighScore(){
        return highScore.getInteger("highScore");
    }

    public void setHighScore(int score){
        highScore.putInteger("highScore", score);
        highScore.flush();
    }
}
