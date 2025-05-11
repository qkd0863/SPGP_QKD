package com.example.nom.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Scene {
    private static final String TAG = Scene.class.getSimpleName();
    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();

    protected void initLayers(int layerCount) {
        layers.clear();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void add(int layerIndex, IGameObject gameObject) {
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.add(gameObject);
        //Log.d(TAG, gameObjects.size() + " objects in " + this);
    }

    public void update() {
        for (ArrayList<IGameObject> gameObjects : layers) {
            int count = gameObjects.size();
            for (int i = count - 1; i >= 0; i--) {
                IGameObject gobj = gameObjects.get(i);
                gobj.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<IGameObject> gameObjects : layers) {
            for (IGameObject gobj : gameObjects) {
                gobj.draw(canvas);
            }
        }

        if (GameView.drawsDebugStuffs) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (ArrayList<IGameObject> gameObjects : layers) {
                for (IGameObject gobj : gameObjects) {
                    if (gobj instanceof IBoxCollidable) {
                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                        canvas.drawRect(rect, bboxPaint);
                    }
                }
            }
        }
    }

    protected static Paint bboxPaint;

    public int count() {
        int total = 0;
        for (ArrayList<IGameObject> layer : layers) {
            total += layer.size();
        }
        return total;
    }

    public void remove(int layerIndex, IGameObject gobj) {
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.remove(gobj);
    }

    public ArrayList<IGameObject> getLayer(int layerIndex) {
        return layers.get(layerIndex);
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
