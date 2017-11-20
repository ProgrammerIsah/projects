package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.example.AdHandler;
import com.mygdx.example.MainGame;

/**
 * Created by kingf on 5/6/17.
 */

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Texture background;
    private MainGame mainGame;
    AdHandler handler;

    private static int SPLASH_TIME = 200;

    public SplashScreen(MainGame mainGame, AdHandler handler)
    {
        this.batch = mainGame.batch;
        this.mainGame = mainGame;
        background = new Texture("splash.jpg");

        this.handler = handler;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SPLASH_TIME -= Gdx.graphics.getDeltaTime();
        if(SPLASH_TIME <= 0 || Gdx.input.isTouched())
        {
            this.dispose();
            mainGame.setScreen(new MainMenuScreen(mainGame, handler));
        }

        batch.begin();
        batch.draw(background, 0, 0, MainMenuScreen.getScreenSizeWidth(), MainMenuScreen.getScreenSizeHeight());
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
        background.dispose();
    }
}
