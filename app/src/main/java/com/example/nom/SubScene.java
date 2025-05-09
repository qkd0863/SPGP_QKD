package com.example.nom;

import android.view.MotionEvent;

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
