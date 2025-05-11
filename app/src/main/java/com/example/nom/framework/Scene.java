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

    public <E extends Enum<E>> void add(E layer, IGameObject gameObject) {
        int layerIndex = layer.ordinal();
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.add(gameObject);
    }

    public void add(ILayerProvider<?> gameObject) {
        Enum<?> e = gameObject.getLayer();
        int layerIndex = e.ordinal();
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.add(gameObject);
    }

    public <E extends Enum<E>> void remove(E layer, IGameObject gobj) {
        int layerIndex = layer.ordinal();
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.remove(gobj);
    }

    public void remove(ILayerProvider<?> gameObject) {
        Enum<?> e = gameObject.getLayer();
        int layerIndex = e.ordinal();
        remove(layerIndex, gameObject);
    }

    private void remove(int layerIndex, IGameObject gobj) {
        ArrayList<IGameObject> gameObjects = layers.get(layerIndex);
        gameObjects.remove(gobj);

    }


    public <E extends Enum<E>> ArrayList<IGameObject> objectsAt(E layer) {
        int layerIndex = layer.ordinal();
        return layers.get(layerIndex);
    }

    public int count() {
        int total = 0;
        for (ArrayList<IGameObject> layer : layers) {
            total += layer.size();
        }
        return total;
    }

    public <E extends Enum<E>> int countAt(E layer) {
        int layerIndex = layer.ordinal();
        return layers.get(layerIndex).size();
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


    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void push() {
        GameView.view.pushScene(this);
    }

    public static Scene pop() {
        return GameView.view.popScene();
    }
    public static Scene top() {
        return GameView.view.getTopScene();
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
