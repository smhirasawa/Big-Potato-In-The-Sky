package com.mygdx.flyingpotato;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Spencer Hirasawa on 5/13/2015.
 */
public class TextureObj {
    int x;
    int y;
    int width;
    int height;
    Texture texture;
    public Sprite sprite;
    Rectangle boundingRect;

    public TextureObj(){

    }

    public TextureObj(Texture texture, int x, int y, int width, int height){
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
    }

    public void dispose(){
        texture.dispose();
    }
}
