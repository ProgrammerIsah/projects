package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.example.AdHandler;
import com.mygdx.example.MainGame;

import static com.mygdx.example.screens.MainMenuScreen.music;

/**
 * Created by programmer on 4/20/17.
 */

public class CreditsScreen implements Screen {
    private MainGame mainGame;
    private Texture background;
    private Texture menu, menu2;
    private BitmapFont font;
    int y;
    private int POSITION_X = 20;
    AdHandler handler;

    public CreditsScreen(MainGame mainGame, AdHandler handler)
    {
        this.mainGame = mainGame;

        this.handler = handler;
        handler.showAds(true);

        background = new Texture("creditsBackground.jpg");
        menu = new Texture("menu.png");
        menu2 = new Texture("menu2.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(-200 < y)
            y -= 60 * Gdx.graphics.getDeltaTime();
        else
            y = 0;

        mainGame.batch.begin();

        mainGame.batch.draw(background, 0, 0, MainMenuScreen.getScreenSizeWidth(), MainMenuScreen.getScreenSizeHeight());

        //MENU
        mainGame.batch.draw(menu2, Gdx.graphics.getWidth() - menu2.getWidth()/2, 20, menu.getWidth() / 3, menu.getHeight() / 3);
        if(Gdx.input.getX() > MainMenuScreen.getScreenSizeWidth() - menu2.getWidth() && Gdx.input.getY() > MainMenuScreen.getScreenSizeHeight() - menu2.getHeight()) {
            if(Gdx.input.isTouched()) {
                music.stop();
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

    @Override
    public void dispose() {
        background.dispose();
        menu.dispose();
    }
}
