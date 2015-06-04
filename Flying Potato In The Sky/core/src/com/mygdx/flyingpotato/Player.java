package com.mygdx.flyingpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;


/**
 * Created by Spencer Hirasawa on 5/5/2015.
 */
public class Player {
    int width;
    int height;
    int mass;
    float gravityScale;
    float velocity;
    TextureAtlas playerAtlas;
    Animation playerAnimation;
    boolean inAnimation;
    float animationTime;
    Texture playerImg;
    Sprite playerSprite;
    boolean isFalling;
    int ascendTime;
    int ascendTimeMax;
    Sound ascendSound;
    Sound gameOverSound;

    Player(Texture playerImg, int width, int height){
        this.playerImg = playerImg;
        playerSprite = new Sprite(playerImg);
        playerAtlas = new TextureAtlas("images/potatoAtlas.atlas");
        playerAnimation = new Animation(1/16f, playerAtlas.getRegions());
        this.width = width;
        this.height = height;
        playerSprite.setSize(width, height);
        mass = 20;
        isFalling = true;
        velocity = 0;
        ascendTime = 0;
        gravityScale = (float).8;
        ascendTimeMax = 10;
        playerAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        animationTime = 0;
        inAnimation = false;
        ascendSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ascend.ogg"));
        gameOverSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/gameOver.wav"));
    }

    public void ascend(){
        if(this.isFalling) {
            velocity = 0;
        }
        ascendSound.play();
        ascendTime = ascendTimeMax;
        isFalling = false;
    }

    public void dispose(){
        playerImg.dispose();
        ascendSound.dispose();
        gameOverSound.dispose();
    }
}

