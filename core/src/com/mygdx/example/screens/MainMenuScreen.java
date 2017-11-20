package com.mygdx.example.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.example.AdHandler;
import com.mygdx.example.Audio;
import com.mygdx.example.MainGame;

import static com.mygdx.example.Audio.soundPlay;

/**
 * Created by programmer on 4/18/17.
 */

public class MainMenuScreen implements Screen {

    private int BTN_WIDTH;
    private int BTN_HEIGHT;
    private int POSITION_X; // pozicija x buttona
    private int POSITION_Y; // pozicija y buttona
    private int POSITIONX_SOUND; // pozicija sound butona
    private int POSITIONY_SOUND;
    MainGame mainGame;
    Texture startBtn, startBtn2, exitBtn, exitBtn2, creditsBtn, creditsBtn2, background, soundON, soundOFF, musicON, musicOFF; // offersBtn, offersBtn2
    int y;

    public static Music music;
    private float musicTurnOFFTime = 0;
    private float soundTurnOFFTime = 0;
    AdHandler handler;

    public MainMenuScreen(MainGame mainGame, AdHandler handler)
    {
        this.mainGame = mainGame;
        startBtn = new Texture("btnStart2.png");
        startBtn2 = new Texture("btnStart.png");

//        offersBtn = new Texture("btnOffers.png");
//        offersBtn2 = new Texture("btnOffers2.png");

        exitBtn = new Texture("exit.png");

        creditsBtn = new Texture("btnCredits2.png");
        creditsBtn2 = new Texture("btnCredits.png");

        background = new Texture("mainMenuBackground.jpg");
//        offers = new Texture("offers.png");

        this.handler = handler;

        handler.showAds(true);

        soundON = new Texture("soundON.png");
        soundOFF = new Texture("soundOFF.png");
        musicON = new Texture("musicON.png");
        musicOFF = new Texture("musicOFF.png");

        BTN_WIDTH = getScreenSizeWidth()/2;
        BTN_HEIGHT = getScreenSizeHeight()/8;

        POSITION_X = getScreenSizeWidth()/2 - BTN_WIDTH/2;
        POSITION_Y = getScreenSizeHeight()/2;

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));

        if(Audio.musicPlay) {
            music.play();
            music.setVolume(1f);
            music.setLooping(true);
        }

        POSITIONX_SOUND = getScreenSizeWidth() - soundON.getWidth();
        POSITIONY_SOUND = 10;
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

        // START
        mainGame.batch.draw(startBtn2, POSITION_X, getScreenSizeHeight() - BTN_HEIGHT*4, BTN_WIDTH, BTN_HEIGHT);
//        mainGame.batch.draw(startBtn2, POSITION_X, POSITION_Y + startBtn.getHeight(), BTN_WIDTH, BTN_HEIGHT);
        if(Gdx.input.getX() > POSITION_X && Gdx.input.getX() < POSITION_X + BTN_WIDTH
                && Gdx.input.getY() < BTN_HEIGHT*4 && Gdx.input.getY() > BTN_HEIGHT*3)
        {
            if(Gdx.input.isTouched()) {
                this.dispose();
                mainGame.setScreen(new GameScreen(mainGame, handler)); // poziva GameScreen
                music.stop();
            }
        }

        // OFFERS
//        mainGame.batch.draw(offersBtn, POSITION_X, getScreenSizeHeight() - BTN_HEIGHT*4, BTN_WIDTH, BTN_HEIGHT);
//        if(Gdx.input.getX() > POSITION_X && Gdx.input.getX() < POSITION_X + BTN_WIDTH
//                && Gdx.input.getY() < BTN_HEIGHT*4 && Gdx.input.getY() > BTN_HEIGHT*3) {
//            if(Gdx.input.isTouched()){
//                this.dispose();
//                mainGame.setScreen(new OffersScreen(mainGame, handler)); // poziva GameScreen
//                music.stop();
//            }
//        }

        //CREDITS
        mainGame.batch.draw(creditsBtn2, POSITION_X, getScreenSizeHeight() - BTN_HEIGHT*6, BTN_WIDTH, BTN_HEIGHT);
        if(Gdx.input.getX() > POSITION_X && Gdx.input.getX() < POSITION_X + BTN_WIDTH
                && Gdx.input.getY() < BTN_HEIGHT*6 && Gdx.input.getY() > BTN_HEIGHT*5) {
            if(Gdx.input.isTouched()) {
                this.dispose();
                mainGame.setScreen(new CreditsScreen(mainGame, handler));
                music.stop();
            }
        }

        //EXIT
        mainGame.batch.draw(exitBtn, MainMenuScreen.getScreenSizeWidth()/2 - exitBtn.getWidth()/2, 10, exitBtn.getWidth(), exitBtn.getHeight());
        if(Gdx.input.getX() > MainMenuScreen.getScreenSizeWidth()/2 - exitBtn.getWidth()/2 && Gdx.input.getX() < MainMenuScreen.getScreenSizeWidth()/2 + exitBtn.getWidth()/2
                && Gdx.input.getY() > MainMenuScreen.getScreenSizeHeight() - exitBtn.getHeight() && Gdx.input.getY() < MainMenuScreen.getScreenSizeHeight())
        {
            if(Gdx.input.isTouched())
                Gdx.app.exit();
        }

        //SOUND
        soundTurnOFFTime += Gdx.graphics.getDeltaTime();
        if(Gdx.input.getX() > MainMenuScreen.getScreenSizeWidth() - soundON.getWidth() && Gdx.input.getX() < MainMenuScreen.getScreenSizeWidth()
                && Gdx.input.getY() > MainMenuScreen.getScreenSizeHeight() - soundON.getHeight() && Gdx.input.getY() < MainMenuScreen.getScreenSizeHeight() && soundTurnOFFTime > 0.3f)
            if(Gdx.input.isTouched()) {
                Audio.soundPlay = !Audio.soundPlay;  //                soundPlay = !soundPlay;
                soundTurnOFFTime = 0;
            }
        if(Audio.soundPlay)
            mainGame.batch.draw(soundON, POSITIONX_SOUND, POSITIONY_SOUND, soundON.getWidth(), soundON.getHeight());
        else
            mainGame.batch.draw(soundOFF, POSITIONX_SOUND, POSITIONY_SOUND, soundON.getWidth(), soundON.getHeight());


        //MUSIC
        musicTurnOFFTime += Gdx.graphics.getDeltaTime();
        if(Gdx.input.getX() > 0 && Gdx.input.getX() < musicON.getWidth() && Gdx.input.getY() > MainMenuScreen.getScreenSizeHeight() - musicON.getHeight() && Gdx.input.getY() < MainMenuScreen.getScreenSizeHeight() && musicTurnOFFTime > 0.3f)
            if(Gdx.input.isTouched()) {
                Audio.musicPlay = !Audio.musicPlay;
                musicTurnOFFTime = 0;
            }
        if(Audio.musicPlay) {
            mainGame.batch.draw(musicON, 0, POSITIONY_SOUND, soundON.getWidth(), soundON.getHeight());
            music.play();
        }
        else {
            mainGame.batch.draw(musicOFF, 0, POSITIONY_SOUND, soundON.getWidth(), soundON.getHeight());
            music.stop();
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
        startBtn.dispose();
        startBtn2.dispose();
        music.dispose();
//        offersBtn.dispose();
//        offersBtn2.dispose();
        exitBtn.dispose();
    }

    public static int getScreenSizeWidth()
    {
        return Gdx.graphics.getWidth();
    }

    public static int getScreenSizeHeight()
    {
        return Gdx.graphics.getHeight();
    }
}
