package com.mygdx.example.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.example.screens.GameScreen;
import com.mygdx.example.screens.MainMenuScreen;
import com.mygdx.example.tools.CollisionRect;

import java.util.Random;

/**
 * Created by programmer on 4/20/17.
 */

public class Asteroids {

    public static int ASTEROID_SPEED = 140;
    public static Texture asteroid;
    float x,y;
    public boolean remove = false;
    int asteroidSpeed;
    CollisionRect rect;
    public static int broj = 0;

    public Asteroids(float x, String ast)
    {
        this.x = x;
        this.y = MainMenuScreen.getScreenSizeHeight() - 100;

        asteroidSpeed =(new Random().nextInt(200) + ASTEROID_SPEED);
        asteroid = new Texture(ast);

        this.rect = new CollisionRect(x,y,asteroid.getWidth(),asteroid.getWidth());
    }

    //Pomjera asteroid sve dok ne izadje
    public void update(float deltaTime)
    {
        y -=  asteroidSpeed * deltaTime;
        if(y < -asteroid.getHeight()) {
            remove = true;
            GameScreen.score -= 10;
            GameScreen.brojPropustenihBalona--;
        }
        rect.move(x,y);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(asteroid, x, y, asteroid.getWidth(), asteroid.getHeight());
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public CollisionRect getColision()
    {
        return rect;
    }

    public float getAsteroidWidth()
    {
        return asteroid.getWidth();
    }

    public float getAsteroidHeight()
    {
        return asteroid.getHeight();
    }

}
