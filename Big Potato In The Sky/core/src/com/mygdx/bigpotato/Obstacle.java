package com.mygdx.bigpotato;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Spencer Hirasawa on 5/5/2015.
 */
public class Obstacle {
    Rectangle rect;
    int velocity;

    public Obstacle(){
        velocity = 200;
    }

    public Obstacle(Rectangle rect, int velocity){
        this.rect = rect;
        this.velocity = velocity;
    }
}
