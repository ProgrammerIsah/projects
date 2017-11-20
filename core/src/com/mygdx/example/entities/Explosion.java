package com.mygdx.example.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by programmer on 5/5/17.
 */

public class Explosion {

    public static final float FRAME_LENGTH = 0.01f;
    public static final int OFFSET = 8;
    public static final int SIZE = 128;

    private static Animation animation = null;
    float x, y;
    float stateTime;

    public boolean remove = false;

    public Explosion(float x, float y, String exp)
    {
        this.x = x - 60;
        this.y = y - 60;
        stateTime = 0;

        animation = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture(exp), SIZE, SIZE)[0]);
    }

    public void update(float deltaTime)
    {
        stateTime += deltaTime;
        if(animation.isAnimationFinished(stateTime))
            remove = true;
    }


    public void render(SpriteBatch batch)
    {
        // Ako se hoce duplo veca animacija eksplozije onda staviti SIZE*2 ako ne onda samo obrisati SIZE*2
        // Izmijeniti poziciju eksplozije nakon promjene velicine
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime), x, y, SIZE*2, SIZE*2);
    }



}
