package com.example.nom.Nom.game;

import android.view.MotionEvent;

import com.example.nom.framework.Scene;

public class SubScene extends Scene {
    public enum Layer {
        enemy, fighter;
        public static final int COUNT = values().length;
    }
    public SubScene() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pop();
        return false;
    }
}
