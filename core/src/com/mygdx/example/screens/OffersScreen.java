package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.example.AdHandler;
import com.mygdx.example.AdSizeHandler;
import com.mygdx.example.MainGame;

import static com.mygdx.example.screens.MainMenuScreen.music;

/**
 * Created by kingf on 5/19/17.
 */

public class OffersScreen implements Screen{

    public MainGame mainGame;
    private AdHandler handler;
    private Texture background;
    private Texture menu, menu2;
    private int POSITION_X = 20;

    public OffersScreen(MainGame mainGame, AdHandler handler)
    {
        this.mainGame = mainGame;
        this.handler = handler;
        handler.showAds(true);

        background = new Texture("mainMenuBackground.jpg");
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

        mainGame.batch.begin();
        mainGame.batch.draw(background,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        menu2.dispose();
    }
}
