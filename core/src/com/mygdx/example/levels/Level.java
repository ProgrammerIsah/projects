package com.mygdx.example.levels;

import com.mygdx.example.entities.Asteroids;
import com.mygdx.example.screens.GameScreen;

/**
 * Created by programmer on 4/22/17.
 */

public class Level {

    //Sadrzavat ce varijable brzine asteroida, tipa asteroida

    static int broj = 0;
    static int i = 0;
    public Level()
    {

    }

    public static void up()
    {
        Asteroids.ASTEROID_SPEED += 120;

        if(GameScreen.level % 2 == 0)
        {
            if( i < 4) {
                broj++;
                i++;
            }
        }

    }
}
