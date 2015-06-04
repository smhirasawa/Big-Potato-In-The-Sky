package com.mygdx.flyingpotato;

import com.badlogic.gdx.Game;

/**
 * Created by Spencer Hirasawa on 5/6/2015.
 */
public class MyGdxGame extends Game{
    GameScreen gameScreen;
    MainMenu mainMenuScreen;
    @Override
    public void create(){
        gameScreen = new GameScreen(this, true);
        mainMenuScreen = new MainMenu(this, false);
        this.setScreen(mainMenuScreen);
    }
}
