package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.example.AdHandler;
import com.mygdx.example.MainGame;
import com.mygdx.example.entities.Asteroids;

/**
 * Created by programmer on 4/23/17.
 */

public class ResultScoreScreen implements Screen {

    MainGame mainGame;
    private Texture background;
    private Texture menu;
    private BitmapFont resultFont;
    private BitmapFont highScoreFont;
    int score;
    int highscore;
    private int POSITION_X; // pozicija x buttona
    private int POSITION_Y; // pozicija y buttona
    private int BTN_WIDTH;
    private int BTN_HEIGHT;
    AdHandler handler;
    public ResultScoreScreen(MainGame mainGame, int score, AdHandler handler)
    {
        this.mainGame = mainGame;
        background = new Texture("resultBackground.jpg");
        resultFont = new BitmapFont(Gdx.files.internal("simpsonsf.fnt"), Gdx.files.internal("simpsonsf.png"), false);
        highScoreFont = new BitmapFont(Gdx.files.internal("simpsonsf.fnt"), Gdx.files.internal("simpsonsf.png"), false);
        this.score = score;

        this.handler = handler;
        handler.showAds(true);

        menu = new Texture("menu.png");

        POSITION_X = MainMenuScreen.getScreenSizeWidth()/2 - menu.getWidth()/3;
        POSITION_Y = MainMenuScreen.getScreenSizeHeight()/2;

        BTN_WIDTH = MainMenuScreen.getScreenSizeWidth()/4;
        BTN_HEIGHT = MainMenuScreen.getScreenSizeHeight()/8;

        //Get highscore from savefile
        Preferences preferences = Gdx.app.getPreferences("hscore");
        this.highscore = preferences.getInteger("highscore", 0);

        Asteroids.ASTEROID_SPEED = 100;
        MainMenuScreen.music.stop();
        GameScreen.music2.stop();

        if(score < 0)
            score = 0;

        if(score > highscore ) {
            preferences.putInteger("highscore", score);
            preferences.flush();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainGame.batch.begin();

        mainGame.batch.draw(background, 0, 0, MainMenuScreen.getScreenSizeWidth(), MainMenuScreen.getScreenSizeHeight());

        //HighScore
        GlyphLayout highscoreLayout = new GlyphLayout(highScoreFont, "" + highscore);
        highScoreFont.draw(mainGame.batch, highscoreLayout, Gdx.graphics.getWidth()/2 - highscoreLayout.width/2,
                Gdx.graphics.getHeight()/2 + highscoreLayout.height*5);
        highScoreFont.setColor(Color.RED);
        highScoreFont.getData().setScale(2f,2f);

        //Score
        GlyphLayout resultLayout = new GlyphLayout(resultFont, "" + score);
        resultFont.draw(mainGame.batch, resultLayout, Gdx.graphics.getWidth()/2 - resultLayout.width/2, Gdx.graphics.getHeight()/2 - resultLayout.height*2);
        resultFont.getData().setScale(2f,2f);


        //Menu
        mainGame.batch.draw(menu, 20, MainMenuScreen.getScreenSizeHeight() - menu.getHeight() / 2, menu.getWidth() / 3, menu.getHeight() / 3);
        if(Gdx.input.getX() < POSITION_X + menu.getWidth()/2 && Gdx.input.getY() < 80) {
            if(Gdx.input.isTouched()) {
                this.dispose();
                mainGame.setScreen(new MainMenuScreen(mainGame, handler)); // poziva MainMenuScreen
            }
        }

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

    public static int getHighscore()
    {
        Preferences preferences = Gdx.app.getPreferences("hscore");
        return preferences.getInteger("highscore", 0);
    }

    @Override
    public void dispose() {
        background.dispose();
        resultFont.dispose();
        highScoreFont.dispose();
        menu.dispose();
    }
}
