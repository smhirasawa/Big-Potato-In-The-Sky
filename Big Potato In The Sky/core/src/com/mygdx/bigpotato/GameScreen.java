package com.mygdx.bigpotato;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen extends ApplicationAdapter implements InputProcessor,Screen{

    private final static float SPAWN_TIME = 3.25f;
    private final static int DIFFICULTY_INCREASE_THRESHOLD = 100;
    private final static int MAX_DIFFICULTY = 24;

    Player player;

    long startTime;
    int playerScore;
    int scoreCheck;
    BitmapFont scoreDisplay;
    int difficulty;

    SpriteBatch batch;
    float lastObstacleTime;
    float timerCounter;
    int spawnThreshold;

    private Array<Obstacle> obstacles;

    final private int screenWidth = Gdx.graphics.getWidth();
    final private int screenHeight = Gdx.graphics.getHeight();

    private OrthographicCamera camera;

    boolean isTransitioning;
    boolean fadeIn;
    TransitionManager screenFade;
    boolean lostGame;

    LossMenu lossMenu;

    MainMenu mainMenu;

    Sound itemSelect;
    Music gameMusic;

    private Array<Background> backgroundArray;
    final static int BACKGROUND_WIDTH = Gdx.graphics.getWidth()*3;

    boolean startGame;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont highScoreDisplay;

    MyGdxGame game;

    public GameScreen(MyGdxGame game, boolean fadeIn){
        this.game = game;
        this.fadeIn = fadeIn;
    }

    @Override
    public void dispose() {
        batch.dispose();
        scoreDisplay.dispose();
        lossMenu.dispose();
        screenFade.transitionScreen.dispose();
        player.dispose();
        gameMusic.dispose();
        itemSelect.dispose();
        fontGenerator.dispose();
    }

    private void spawnObstacle(){
        Rectangle r = new Rectangle();
        r.height = MathUtils.random(screenHeight/10, screenHeight/3);
        r.width = MathUtils.random(screenWidth/20, screenWidth/15);
        r.x = screenWidth;
        r.y = MathUtils.random(0, screenHeight - r.height);
        Obstacle o = new Obstacle();
        obstacles.add(o);
        lastObstacleTime = Gdx.graphics.getDeltaTime();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Alters coordinates so that they are in sync with camera coordinates
        Vector3 coords = new Vector3(screenX, screenY, 0);
        Vector3 unprojectedCoords = camera.unproject(coords);

        //When game is active ascend player on tap
        if(applyPhysics()) {
            player.ascend();
            player.ascendSound.play();
            player.inAnimation = true;
        }

        //Wait for player to tap to begin game
        System.out.println("touched");
        if(!startGame && !fadeIn){
            startGame = true;
            gameMusic.play();
        }

        //When game is not active check for menu button clicks
        if(lostGame && !isTransitioning){
            if(lossMenu.clickedRetry(unprojectedCoords.x, unprojectedCoords.y)){
                itemSelect.play();
                GameScreen gameScreen = new GameScreen(game, false);
                game.setScreen(gameScreen);
            }
            else if(lossMenu.clickedQuit(unprojectedCoords.x, unprojectedCoords.y)){
                itemSelect.play();
                isTransitioning = true;
            }
        }

        return true;
    }

    @Override
    public void show() {
        mainMenu = new MainMenu(game, true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        batch = new SpriteBatch();

        player = new Player(new Texture("images/potato.png"), screenWidth/12, screenHeight/8);

        player.playerSprite.setX(screenWidth/10);
        player.playerSprite.setY(screenHeight / 2 - player.height / 2);

        obstacles = new Array<Obstacle>();

        timerCounter = 0;
        spawnThreshold = 50;

        playerScore = 0;
        scoreCheck = 0;
        difficulty = 1;

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Bitwise.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;
        scoreDisplay = fontGenerator.generateFont(fontParameter);
        highScoreDisplay = fontGenerator.generateFont(fontParameter);

        startTime = System.currentTimeMillis();

        screenFade = new TransitionManager();
        isTransitioning = false;
        lostGame = false;
        lossMenu = new LossMenu();

        itemSelect = Gdx.audio.newSound(Gdx.files.internal("sounds/itemSelect.wav"));
        gameMusic =  Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMusic.wav"));

        gameMusic.setLooping(true);

        backgroundArray = new Array<Background>();
        Background background1 = new Background(new Texture("images/backgroundImage.png"), 0, -5, BACKGROUND_WIDTH, screenHeight+5);
        backgroundArray.add(background1);
        Background background2 = new Background(new Texture("images/backgroundImage.png"), background1.width, -5, BACKGROUND_WIDTH, screenHeight+5);
        backgroundArray.add(background2);

        startGame = false;

        Gdx.input.setInputProcessor(this);
        spawnObstacle();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        //Update player score
        //Difficulty scales with player score
        if(applyPhysics()) {
            playerScore = (int)(System.currentTimeMillis() - startTime)/100;
            if (playerScore % DIFFICULTY_INCREASE_THRESHOLD == 0 && difficulty < MAX_DIFFICULTY && playerScore != scoreCheck) {
                difficulty++;
                scoreCheck = playerScore;
                if (spawnThreshold > MAX_DIFFICULTY)
                    spawnThreshold -= 3;
            }
        }
        /*-----------------------Draw Objects---------------------*/
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Background b: backgroundArray){
            b.sprite.draw(batch);
        }

        //Renders player animations
        if(player.inAnimation){
            player.animationTime += Gdx.graphics.getDeltaTime();
            batch.draw(player.playerAnimation.getKeyFrame(player.animationTime, false), player.playerSprite.getX(), player.playerSprite.getY(), player.width, player.height);
        }
        else {
            player.playerSprite.draw(batch);
        }
        if (player.playerAnimation.isAnimationFinished(player.animationTime)){
            player.animationTime = 0;
            player.inAnimation = false;
        }

        //Renders each obstacle
        for(Obstacle o: obstacles){
            o.draw(batch);
        }

        highScoreDisplay.draw(batch, "High Score: " + Integer.toString(game.getHighScore()), 0, screenHeight - screenHeight / 30);
        scoreDisplay.draw(batch, Integer.toString(playerScore), screenWidth / 2, screenHeight - screenHeight / 30);
        //Handles screen fade in from splash screen
        if(fadeIn){
            screenFade.fadeInScreen(batch);
            if(screenFade.fadeInComplete()) {
                startTime = System.currentTimeMillis();
                fadeIn = false;
            }
        }

        //Handles screen fade out from game screen
        if(isTransitioning){
            screenFade.fadeOutScreen(batch);
            if(screenFade.fadeOutComplete()) {
                game.setScreen(mainMenu);
            }
        }

        //Handles event for a lost game
        if(lostGame){
            gameMusic.stop();
            //1 is a magic number that represents the time delay before drawing lossMenu
            if(lossMenu.waitTime == 1) {
                player.gameOverSound.play();
                if(game.getHighScore() < playerScore){
                    game.setHighScore(playerScore);
                }
                System.out.println("High Score: " + game.getHighScore());
            }
            lossMenu.waitTime -= Gdx.graphics.getDeltaTime();
            if(lossMenu.waitTime <= 0) {
                lossMenu.draw(batch);
            }
        }

        batch.end();

        /*--------------------Apply Physics-----------------*/
        //only apply physics once done fading in screen or not transitioning
        if(applyPhysics()) {

            //Timer to spawn new obstacles
            timerCounter += Gdx.graphics.getDeltaTime();
            if (timerCounter > SPAWN_TIME) {
                spawnObstacle();
                timerCounter = 0;
            }

            //Makes background scroll and "wrap"
            for(int i = 0; i < backgroundArray.size; i++){
                backgroundArray.get(i).sprite.setX(backgroundArray.get(i).sprite.getX() - 200 * Gdx.graphics.getDeltaTime());
                if(backgroundArray.get(i).sprite.getX() + backgroundArray.get(i).width <= 0){
                    float difference = Math.abs(backgroundArray.get(i).sprite.getX() + backgroundArray.get(i).width);
                    backgroundArray.get(i).sprite.setX(backgroundArray.get(i).width - difference);
                }
            }

            //Translates each obstacle based on its velocity
            //and checks to see if there is a collision with the player
            Iterator<Obstacle> iter = obstacles.iterator();
            while (iter.hasNext()) {
                Obstacle obstacle = iter.next();
                obstacle.update();
                Rectangle actor = new Rectangle();
                actor.setX(player.playerSprite.getX());
                actor.setY(player.playerSprite.getY());
                actor.setWidth(player.width);
                actor.setHeight(player.height);
                if (obstacle.collisionCheck(actor)) {
                    lostGame = true;
                }
                if (obstacle.clearToRemove()) {
                    iter.remove();
                    obstacle.dispose();
                }
            }

            //apply gravity to player
            if (player.isFalling) {
                player.velocity += player.mass * Gdx.graphics.getDeltaTime() * player.gravityScale;
                player.playerSprite.setY(player.playerSprite.getY() - player.velocity);
            } else {
                player.velocity += player.mass * Gdx.graphics.getDeltaTime() * player.ascendTime / (player.ascendTimeMax / 2);
                player.ascendTime--;
                player.playerSprite.setY(player.playerSprite.getY() + player.velocity);
                if (player.ascendTime == 0) {
                    player.isFalling = true;
                    player.velocity = 0;
                }
            }

            checkForLoss();
        }
    }

    public void checkForLoss(){
        if(player.playerSprite.getY() + player.height > screenHeight){
            lostGame = true;
        }
        else if(player.playerSprite.getY() < 0){
            lostGame = true;
        }

        if(lostGame){
           //isTransitioning = true;
        }
    }

    public boolean applyPhysics(){
        if(isTransitioning)
            return false;
        else if(fadeIn)
            return false;
        else if(lostGame)
            return false;
        else if(!startGame)
            return false;
        else
            return true;
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /*--------------NOT USED FOR ANDROID-----------------------*/
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    /*--------------NOT USED FOR ANDROID END-----------------------*/

}
