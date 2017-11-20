package com.mygdx.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.example.screens.SplashScreen;

public class MainGame extends Game {
	public SpriteBatch batch;
	AdHandler handler;
	boolean toggle;

	public MainGame(AdHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
//		this.setScreen(new MainMenuScreen(this));
		this.setScreen(new SplashScreen(this, handler));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
