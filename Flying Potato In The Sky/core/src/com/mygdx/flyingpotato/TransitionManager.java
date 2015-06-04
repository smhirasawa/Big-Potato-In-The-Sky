package com.mygdx.flyingpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionManager {
    float fadeOutTime;
    float fadeInTime;
    TextureObj transitionScreen;

    public TransitionManager(){
        fadeInTime = 1.0f;
        fadeOutTime = 0.0f;
        transitionScreen = new TextureObj(new Texture("images/transitionScreen.png"),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void fadeInScreen(SpriteBatch batch){
        transitionScreen.sprite.setColor(0.0f, 0.0f, 0.0f, fadeInTime);
        fadeInTime -= Gdx.graphics.getDeltaTime();
        transitionScreen.sprite.draw(batch);
    }

    public boolean fadeInComplete(){
        if(fadeInTime <= 0)
            return true;
        else
            return false;
    }

    public void fadeOutScreen(SpriteBatch batch){
        transitionScreen.sprite.setColor(0.0f, 0.0f, 0.0f, fadeOutTime);
        fadeOutTime += Gdx.graphics.getDeltaTime();
        transitionScreen.sprite.draw(batch);
    }

    public boolean fadeOutComplete(){
        if(fadeOutTime >= 1)
            return true;
        else
            return false;
    }
}
