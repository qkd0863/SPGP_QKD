package com.example.nom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        fighter = new Fighter();


        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        add(fighter);

    }


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] xy = Metrics.fromScreen(event.getX(), event.getY());
                if (xy[0] < 100 && xy[1] < 100) {
                    new SubScene().push();
                    return false;
                }

                return true;
        }
        return false;
    }

}
