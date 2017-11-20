package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.example.AdHandler;
import com.mygdx.example.Audio;
import com.mygdx.example.MainGame;
import com.mygdx.example.entities.Asteroids;
import com.mygdx.example.entities.Explosion;
import com.mygdx.example.levels.Level;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.example.screens.MainMenuScreen.music;

/**
 * Created by programmer on 4/18/17.
 */

public class GameScreen implements Screen {

    public static final int SPEED = 3200; // brzina pomjeranja igraca
    private static int TIMER = 200;
    private static float PROVJERA_TIMER = 1.2f; // vrijeme koje ce pauzirati prije provjere da li je broj bodova % 150 da bi povecao level
    public MainGame mainGame;
    public Texture img;

    float x,y;
    float asteroidTime;
    float stateTime;
    float levelTime;
    float ammoTime;

    public int SHIP_WIDTH;
    public int SHIP_HEIGHT;
    int roll;
    int shoot = 0;
    public static int score;
    public static int brojPropustenihBalona = 10;

    Animation[] rolls;
    ArrayList<Asteroids> alista;
    ArrayList<Explosion> explosionsLista;

    Random random;
    Texture player;
    BitmapFont scoreFont;
    BitmapFont levelFont;
    BitmapFont highScoreFont;
    Texture menu, menu2;
    Texture background;
    public static int level;

    Sound explosion;

    private float RESULTT = 0.6f; //vrijeme koje treba da prodje prije nego se pozove ResultScoreScreen

    int nScore; // varijabla koja se koristi za level
    private int POSITION_X;
    private int POSITION_Y;
    private boolean shooting = true;

    public static String[] baloni = {"red.png", "blue.png", "purple.png", "yellow.png"};
    public static String[] eksplozije = {"redxplosion.png", "bluexplosion.png", "purplexplosion.png", "yellowexplosion.png"};

    int clickCount = 200;
    public static  Music music2;

    AdHandler handler;

    public GameScreen(MainGame mainGame, AdHandler handler)
    {
        this.mainGame = mainGame;

        img = new Texture("icon.png");
        random = new Random();

        roll = 1;
        rolls = new Animation[8];
        asteroidTime = random.nextFloat()*0.4f + 0.8f;

        alista = new ArrayList<Asteroids>();
        explosionsLista = new ArrayList<Explosion>();

        this.handler = handler;

        background = new Texture("gamebackground.jpg");

        handler.showAds(false);

        score = 0;
        nScore = 0;
        level = 1;
        levelTime = PROVJERA_TIMER;
        menu = new Texture("menu.png");
        menu2 = new Texture("menu2.png");
        scoreFont = new BitmapFont(Gdx.files.internal("simpsonsf.fnt"), Gdx.files.internal("simpsonsf.png"), false);
        levelFont = new BitmapFont(Gdx.files.internal("simpsonsf.fnt"), Gdx.files.internal("simpsonsf.png"), false);
        highScoreFont = new BitmapFont(Gdx.files.internal("simpsonsf.fnt"), Gdx.files.internal("simpsonsf.png"), false);

        player = new Texture("circlePlayer.png");

        explosion = Gdx.audio.newSound(Gdx.files.internal("audio/explosion.mp3"));

        music2 = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));

        if(Audio.musicPlay) {
            music2.play();
            music2.setVolume(0.5f);
            music2.setLooping(true);
        }

        x = MainMenuScreen.getScreenSizeWidth()/2;
        y = 100;

        ammoTime = 0.6f;
        POSITION_X = 20;
        POSITION_Y = MainMenuScreen.getScreenSizeHeight()/2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shoot += Gdx.graphics.getDeltaTime();

        //Asteroid
        TIMER -= Gdx.graphics.getDeltaTime();
        if(TIMER <= 0) {
            asteroidTime -= Gdx.graphics.getDeltaTime();
            //Asteroids
            if (asteroidTime < 0) {
                alista.add(new Asteroids(new Random().nextInt(Gdx.graphics.getWidth() - 150) + 50, baloni[GameScreen.level % 4]));
                asteroidTime = random.nextFloat()*0.4f + 0.8f;
                nScore++;
            }
        }

        //Update asteroid
        ArrayList<Asteroids> ast = new ArrayList<Asteroids>();
        for(Asteroids as : alista)
        {
            as.update(Gdx.graphics.getDeltaTime());
            if(as.remove)
                ast.add(as);
        }

        //Update explosion
        ArrayList<Explosion> elist = new ArrayList<Explosion>();
        for(Explosion ex : explosionsLista)
        {
            ex.update(Gdx.graphics.getDeltaTime());
            if(ex.remove)
                elist.add(ex);
        }

        // Moving Player on x and y
        if((int)(x + SHIP_WIDTH/2) < Gdx.input.getX()){
            x += SPEED * Gdx.graphics.getDeltaTime();
            if((x + SHIP_WIDTH) > MainMenuScreen.getScreenSizeWidth())
                x = MainMenuScreen.getScreenSizeWidth() - SHIP_WIDTH;
        }
        if((int)(x + SHIP_WIDTH/2) > Gdx.input.getX()){
            x -= SPEED * Gdx.graphics.getDeltaTime();
            if( x < 0)
                x = 0;
        }
        if((int)(y + SHIP_WIDTH/2) < MainMenuScreen.getScreenSizeHeight() - Gdx.input.getY())
        {
            y += SPEED * Gdx.graphics.getDeltaTime();
            if((y + SHIP_WIDTH/2) > MainMenuScreen.getScreenSizeHeight() - SHIP_HEIGHT*3)
                y = MainMenuScreen.getScreenSizeHeight() - SHIP_HEIGHT*3;
        }
        if((int)(y + SHIP_HEIGHT/2) > MainMenuScreen.getScreenSizeHeight() - Gdx.input.getY())
        {
            y -= SPEED * Gdx.graphics.getDeltaTime();
            if( y < SHIP_HEIGHT)
                y = SHIP_HEIGHT;
        }

        //Updates collision detection with balloons
            for(Asteroids a : alista){
                if(Gdx.input.getX() >= a.getX() && Gdx.input.getX() <= a.getX() + SHIP_WIDTH
                        && MainMenuScreen.getScreenSizeHeight() - Gdx.input.getY() >= a.getY()
                        && MainMenuScreen.getScreenSizeHeight() - Gdx.input.getY() <= a.getY() + a.getAsteroidHeight() ) // ako je asteroid ista x i y sa touch kordinatom onda obrisi
                {
                    if(Gdx.input.isTouched()) {
                        ast.add(a);
                        //Ako se drzi orginalna velicina eksplozije onda
//                        explosionsLista.add(new Explosion(a.getX() + a.getAsteroidWidth()/2, a.getY() + a.getAsteroidHeight()/2, eksplozije[GameScreen.level % 4]));
                        //Ako hocemo duplo vecu animaciju eksplozije onda
                        explosionsLista.add(new Explosion(a.getX(), a.getY(), eksplozije[GameScreen.level % 4]));
                        if (Audio.soundPlay)
                            explosion.play();
                        score += 10;
                    }
                }
            }// petlja


        alista.removeAll(ast);
        explosionsLista.removeAll(elist);

        String s = "" + score;

        //Check if score is less than -20
        if(score < - 10) {
            shooting = false;
            s = "You LOSE!";
        }
        if(score < -40) {
            music2.stop();
            music.stop();
            mainGame.setScreen(new MainMenuScreen(mainGame, handler));
        }

        // Level increase
        levelTime -= Gdx.graphics.getDeltaTime();
        if(levelTime < 0)
            if(nScore > 0 && nScore % 30 == 0) { // za broj bodova povecava se level
                Level.up();
                level++;
                levelTime = PROVJERA_TIMER;
            }

        //Za highscore ispis
        RESULTT -= Gdx.graphics.getDeltaTime();
        if(brojPropustenihBalona <= 0) {
            shooting = false;
            if(RESULTT <= 0)
            {
                RESULTT = 0.6f;
                brojPropustenihBalona = 10;
//                asteroidTime = random.nextFloat()*0.4f + 0.8f;
                this.dispose();
                mainGame.setScreen(new ResultScoreScreen(mainGame, score, handler));
            }
        }

        SHIP_WIDTH = MainMenuScreen.getScreenSizeWidth()/6;  // bilo /10
        SHIP_HEIGHT = SHIP_WIDTH;

        stateTime += Gdx.graphics.getDeltaTime();

        mainGame.batch.begin();
        mainGame.batch.draw(background, 0, 0, MainMenuScreen.getScreenSizeWidth(), MainMenuScreen.getScreenSizeHeight());

        //Menu opcija u igri
        mainGame.batch.draw(menu2, Gdx.graphics.getWidth() - menu2.getWidth()/2, 20, menu.getWidth() / 3, menu.getHeight() / 3);
        if(Gdx.input.getX() > MainMenuScreen.getScreenSizeWidth() - SHIP_WIDTH && Gdx.input.getY() > MainMenuScreen.getScreenSizeHeight() - SHIP_HEIGHT) {
            if(Gdx.input.isTouched()) {
                asteroidTime = random.nextFloat()*0.4f + 0.8f;
                Asteroids.ASTEROID_SPEED = 140;
                music2.stop();
                this.dispose();
                mainGame.setScreen(new MainMenuScreen(mainGame,handler)); // poziva MainMenuScreen
            }
        }

        //--------------------------------------------------------------------------------------------
        for(Asteroids a : alista)
            a.render(mainGame.batch);
        for(Explosion e : explosionsLista)
            e.render(mainGame.batch);


        mainGame.batch.draw(player, x, y, SHIP_WIDTH, SHIP_HEIGHT);

        //Score
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, s);
        scoreFont.draw(mainGame.batch, scoreLayout, 50, MainMenuScreen.getScreenSizeHeight() - scoreLayout.height);

        //Level
        GlyphLayout levelFontLayout = new GlyphLayout(levelFont, "" + level);
        levelFont.draw(mainGame.batch, levelFontLayout, MainMenuScreen.getScreenSizeWidth()/2 - levelFontLayout.width/2,
                MainMenuScreen.getScreenSizeHeight() - (int)(levelFontLayout.height*0.5));
        levelFont.getData().setScale(1.5f,1.5f);
        levelFont.setColor(Color.RED);

        //HighScore
        GlyphLayout highScoreLayout = new GlyphLayout(highScoreFont, "" + ResultScoreScreen.getHighscore());
        highScoreFont.draw(mainGame.batch, highScoreLayout, MainMenuScreen.getScreenSizeWidth() - highScoreLayout.width*2, MainMenuScreen.getScreenSizeHeight() - highScoreLayout.height);
        mainGame.batch.end();
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
        img.dispose();
        explosion.dispose();

        music.dispose();
        music2.dispose();

        scoreFont.dispose();
        levelFont.dispose();
        highScoreFont.dispose();

        player.dispose();

        menu.dispose();
        menu2.dispose();
    }
}
