package com.example.nom;

import android.graphics.Canvas;

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

}
