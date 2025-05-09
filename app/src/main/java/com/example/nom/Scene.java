package com.example.nom;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Scene {
    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    public void update() {
        for (IGameObject gobj : gameObjects) {
            gobj.update();
        }
    }
    public void draw(Canvas canvas) {
        for (IGameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void push() {
        GameView.view.pushScene(this);
    }
    public static Scene pop() {
        return GameView.view.popScene();
    }
}
