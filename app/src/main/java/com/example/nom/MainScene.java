package com.example.nom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class MainScene extends Scene {
    private Fighter fighter;

    public MainScene(GameView gameView) {
        Resources res = gameView.getResources();
        Bitmap ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        Ball.setBitmap(ballBitmap);

        Bitmap fighterBitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
        fighter = new Fighter(fighterBitmap);


        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        gameObjects.add(fighter);
    }


}
