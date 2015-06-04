package com.mygdx.flyingpotato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Spencer Hirasawa on 5/6/2015.
 */
public class MainMenu implements InputProcessor, Screen{
    int screenWidth;
    int screenHeight;
    MyGdxGame game;
    TextureObj startButton;
    TextureObj title;
    SpriteBatch batch;
    int startButtonWidth;
    int startButtonHeight;

    boolean isTransitioning;
    boolean fadeIn;
    TransitionManager screenFade;

    private OrthographicCamera camera;

    Sound itemSelect;
    Music menuMusic;

    GameScreen gameScreen;

    public MainMenu(MyGdxGame game, boolean fadeIn){
        this.game = game;
        this.fadeIn = fadeIn;
    }

    /*-------------------Screen-------------------------*/
    @Override
    public void show() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        startButtonWidth =  screenWidth/4;
        startButtonHeight = screenHeight/6;

        isTransitioning = false;
        screenFade = new TransitionManager();

        batch = new SpriteBatch();
        title = new TextureObj(new Texture(("images/title.png")), screenWidth/2 - screenWidth/4, screenHeight-screenHeight/3, screenWidth/2, screenHeight/4);
        startButton = new TextureObj(new Texture("images/startButton.png"), screenWidth/2 - startButtonWidth/2, screenHeight/4, startButtonWidth, startButtonHeight);

        itemSelect = Gdx.audio.newSound(Gdx.files.internal("sounds/itemSelect.wav"));
        menuMusic =  Gdx.audio.newMusic(Gdx.files.internal("sounds/menuMusic.wav"));

        menuMusic.setLooping(true);
        menuMusic.play();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(title.sprite, title.x, title.y, title.width, title.height);
        batch.draw(startButton.sprite, startButton.x, startButton.y, startButton.width, startButton.height);
        if(fadeIn){
            screenFade.fadeInScreen(batch);
            if(screenFade.fadeInComplete())
                fadeIn = false;
        }
        if(isTransitioning){
            screenFade.fadeOutScreen(batch);
            if(screenFade.fadeOutComplete()) {
                gameScreen = new GameScreen(game, true);
                game.setScreen(gameScreen);
            }
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
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        startButton.texture.dispose();
        title.texture.dispose();
        screenFade.transitionScreen.dispose();
        itemSelect.dispose();
        menuMusic.dispose();
    }

    /*-------------------Input Processor-------------------------*/
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 coords = new Vector3(screenX, screenY, 0);
        Vector3 unprojectedCoords = camera.unproject(coords);
        if(startButton.x <= screenX && startButton.x + startButton.width >= screenX){
            if(startButton.y <= unprojectedCoords.y && startButton.y + startButton.height >= unprojectedCoords.y) {
                isTransitioning = true;
                menuMusic.stop();
                itemSelect.play();
            }
        }
        return true;
    }
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
