package com.example.nom.Nom.game;

import android.view.MotionEvent;

import com.example.nom.framework.Scene;

public class SubScene extends Scene {
    public SubScene() {
        for (int i = 0; i < 5; i++) {
            gameObjects.add(Ball.random());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pop();
        return false;
    }
}
