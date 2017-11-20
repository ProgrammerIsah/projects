package com.mygdx.example.tools;

/**
 * Created by programmer on 4/23/17.
 */

public class CollisionRect {
    float x,y;
    int width, height;
    public CollisionRect(float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void move(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean colides(CollisionRect collisionRect)
    {
        return x < collisionRect.x + collisionRect.width - 10 && y < collisionRect.y
                + collisionRect.height - 10 && x + width > collisionRect.x + 10 && y + height > collisionRect.y + 10;
    }

    public boolean colides(float xP, float yP, int width, int height)
    {
        return x < xP + width - 10 && y <= 20 && x + width > xP + 10;
    }
}
