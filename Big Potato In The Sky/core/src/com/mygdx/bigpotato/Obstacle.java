package com.mygdx.bigpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Spencer Hirasawa on 5/5/2015.
 */
public class Obstacle {
    private static final float MAX_PIPE_BASE_HEIGHT = Gdx.graphics.getHeight()*.75f;
    private static final float MIN_PIPE_BASE_HEIGHT = 0;//Gdx.graphics.getHeight()*.1f;

    Rectangle rect;
    int velocity;
    Texture pipeBase;
    Sprite pipeBaseSprite;
    Texture pipeTop;
    Sprite pipeTopSprite;

    Sprite pipeBaseSpriteLower;
    Sprite pipeTopSpriteLower;

    public Obstacle(){
        //Top Pipe
        pipeBase = new Texture(Gdx.files.internal("images/pipeBase.png"));
        pipeBaseSprite = new Sprite(pipeBase);
        pipeBaseSprite.setSize(175, MathUtils.random(MIN_PIPE_BASE_HEIGHT, MAX_PIPE_BASE_HEIGHT));
        pipeBaseSprite.setPosition(Gdx.graphics.getWidth() + 25, Gdx.graphics.getHeight() - (pipeBaseSprite.getHeight()));

        pipeTop = new Texture(Gdx.files.internal("images/pipeTop.png"));
        pipeTopSprite = new Sprite(pipeTop);
        pipeTopSprite.setSize(225, 150);
        pipeTopSprite.setPosition(Gdx.graphics.getWidth(), pipeBaseSprite.getY() - pipeTopSprite.getHeight()/2);

        //Bottom Pipe
        pipeBaseSpriteLower = new Sprite(pipeBase);
        float heightCheck = pipeTopSprite.getY() - Gdx.graphics.getHeight()/3;
        /*if (heightCheck < 0)
            heightCheck = 0;*/
        pipeBaseSpriteLower.setSize(175, heightCheck);
        pipeBaseSpriteLower.setPosition(Gdx.graphics.getWidth() + 25, -5);

        pipeTopSpriteLower = new Sprite(pipeTop);
        pipeTopSpriteLower.setSize(225, 150);
        pipeTopSpriteLower.setPosition(Gdx.graphics.getWidth(), (pipeBaseSpriteLower.getHeight()+pipeBaseSpriteLower.getY()) - pipeTopSpriteLower.getHeight()/2);

        this.velocity = 200;
    }

    public void draw(SpriteBatch batch){
        pipeBaseSprite.draw(batch);
        pipeTopSprite.draw(batch);
        pipeBaseSpriteLower.draw(batch);
        pipeTopSpriteLower.draw(batch);
    }

    public void update(){
        pipeBaseSprite.setX(pipeBaseSprite.getX() - velocity*Gdx.graphics.getDeltaTime());
        pipeTopSprite.setX(pipeTopSprite.getX() - velocity*Gdx.graphics.getDeltaTime());
        pipeBaseSpriteLower.setX(pipeBaseSpriteLower.getX() - velocity*Gdx.graphics.getDeltaTime());
        pipeTopSpriteLower.setX(pipeTopSpriteLower.getX() - velocity*Gdx.graphics.getDeltaTime());
    }

    public boolean clearToRemove(){
        if(pipeTopSprite.getX() + pipeTopSprite.getWidth() < 0)
            return true;
        else
            return false;
    }

    public boolean collisionCheck(Rectangle player){
        if(pipeBaseSprite.getBoundingRectangle().overlaps(player))
            return true;
        else if(pipeTopSprite.getBoundingRectangle().overlaps(player))
            return true;
        else if(pipeBaseSpriteLower.getBoundingRectangle().overlaps(player))
            return true;
        else if(pipeTopSpriteLower.getBoundingRectangle().overlaps(player))
            return true;
        else
            return false;
    }

    public void dispose(){
        pipeBase.dispose();
        pipeTop.dispose();
    }
}
