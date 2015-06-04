package com.mygdx.bigpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Spencer Hirasawa on 5/24/2015.
 */
public class LossMenu extends TextureObj{
    BitmapFont score;
    BitmapFont lossFont;
    Texture retryButton;
    Sprite retrySprite;
    Texture quitButton;
    Sprite quitSprite;
    float waitTime;
    public LossMenu(){
        width = Gdx.graphics.getWidth()/2;
        height = Gdx.graphics.getHeight()/2;
        x = (Gdx.graphics.getWidth()/2) - width/2;
        y = (Gdx.graphics.getHeight()/2 - height/2);
        texture = new Texture("images/lossMenuBackground.png");
        sprite = new Sprite(texture);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setSize(width, height);
        boundingRect = sprite.getBoundingRectangle();

        retryButton = new Texture("images/retryButton.png");
        retrySprite = new Sprite(retryButton);
        retrySprite.setSize(width/3.0f, height/5.0f);
        retrySprite.setX((2*sprite.getX() + width)/2.0f - retrySprite.getWidth()/2.0f);
        retrySprite.setY((2*sprite.getY() + height)/2.0f - retrySprite.getHeight()/2.0f);

        quitButton = new Texture("images/quitButton.png");
        quitSprite = new Sprite(quitButton);
        quitSprite.setSize(width/3.0f, height/5.0f);
        quitSprite.setX((2*sprite.getX() + width)/2.0f - quitSprite.getWidth()/2.0f);
        quitSprite.setY((2*sprite.getY() + height)/2.0f - (quitSprite.getHeight()/2.0f + 1.05f*retrySprite.getHeight()));

        score = new BitmapFont(Gdx.files.internal("fonts/fonts.fnt"), false);
        lossFont = new BitmapFont(Gdx.files.internal("fonts/fonts.fnt"), false);
        waitTime = 1;
    }

    public boolean clickedRetry(float x, float y){
        if(retrySprite.getBoundingRectangle().contains(x,y)) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean clickedQuit(float x, float y){
        if(quitSprite.getBoundingRectangle().contains(x,y)) {
            return true;
        }
        else{
            return false;
        }
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
        retrySprite.draw(batch);
        quitSprite.draw(batch);
    }

    @Override
    public void dispose(){
        texture.dispose();
        retryButton.dispose();
        quitButton.dispose();
        score.dispose();
        lossFont.dispose();
    }
}
