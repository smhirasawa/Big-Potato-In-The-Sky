package com.mygdx.bigpotato;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Spencer Hirasawa on 6/2/2015.
 */
public class Background extends TextureObj {
    boolean reachedEnd;
    public Background(Texture texture, int x, int y, int width, int height){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setSize(width, height);
        boundingRect = sprite.getBoundingRectangle();
        reachedEnd = false;
    }
}
