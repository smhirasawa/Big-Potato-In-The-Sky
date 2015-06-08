package com.mygdx.bigpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Spencer Hirasawa on 6/5/2015.
 */
public class HighScoreScreen implements InputProcessor, Screen{
    MyGdxGame game;
    boolean fadeIn;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;

    final private int screenWidth = Gdx.graphics.getWidth();
    final private int screenHeight = Gdx.graphics.getHeight();

    int []scores;
    String []names;

    public HighScoreScreen(MyGdxGame game, boolean fadeIn){
        this.game = game;
        this.fadeIn = fadeIn;
    }

    /*----------------------Screen-------------------*/
    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        font = new BitmapFont(Gdx.files.internal("fonts/fonts.fnt"), false);

        /*Save.load();
        scores = Save.scoreManager.getScores();
        names = Save.scoreManager.getNames();*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "High Scores", screenWidth/2, screenHeight-100);

        for(int i = 0; i < scores.length; i++){
            String s = i+".\t\t" + scores[i];
            font.draw(batch, s, screenWidth/2, screenHeight - (150 + i*20));
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /*------------------Input Processor----------------------*/
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
