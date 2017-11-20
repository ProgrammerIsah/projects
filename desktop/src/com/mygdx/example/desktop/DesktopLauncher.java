package com.mygdx.example.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.example.AdHandler;
import com.mygdx.example.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(new AdHandler() {
			@Override
			public void showAds(boolean show) {
				System.out.println("OK");
			}
		}), config);
	}
}
