package com.example.nom.Nom.game;

import android.view.MotionEvent;

import com.example.nom.BuildConfig;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Scene;
import com.example.nom.framework.GameView;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
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
