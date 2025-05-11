package com.example.nom.framework;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in " + this);
    }

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

    public int count() {
        return gameObjects.size();
    }
    public void remove(IGameObject gobj) {
        gameObjects.remove(gobj);
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

    public void onEnter() {
        Log.v(TAG, "onEnter: " + getClass().getSimpleName());
    }
    public void onPause() {
        Log.v(TAG, "onPause: " + getClass().getSimpleName());
    }
    public void onResume() {
        Log.v(TAG, "onResume: " + getClass().getSimpleName());
    }
    public void onExit() {
        Log.v(TAG, "onExit: " + getClass().getSimpleName());
    }

    public boolean onBackPressed() {
        return false;
    }
}
