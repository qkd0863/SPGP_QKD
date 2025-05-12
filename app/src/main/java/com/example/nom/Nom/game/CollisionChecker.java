package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.nom.framework.CollisionHelper;
import com.example.nom.framework.IGameObject;
import com.example.nom.framework.Scene;

import java.util.ArrayList;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;

    public CollisionChecker(MainScene mainScene) {
        this.scene = mainScene;
    }

    @Override
    public void update() {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> points = scene.objectsAt(MainScene.Layer.controller);

        Fighter player = null;
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i) instanceof Fighter) {
                player = (Fighter) enemies.get(i);
                break;
            }
        }

        if (player == null) return;


        for (int i1 = enemies.size() - 1; i1 >= 0; i1--) {
            if (!(enemies.get(i1) instanceof Ball)) continue;
            Ball ball = (Ball) enemies.get(i1);

            if (CollisionHelper.collides(ball, player)) {
                Log.d(TAG, "Collision !!");
                scene.remove(ball);
                scene.addScore(200);
                break;
            }

        }

        for (int i1 = points.size() - 1; i1 >= 0; i1--) {
            if (!(points.get(i1) instanceof TurnPoint)) continue;
            TurnPoint point = (TurnPoint) points.get(i1);

            if (CollisionHelper.collides(point, player)) {
                Log.d(TAG, "Collision !!");
                player.setDx(point.getPointx());
                player.setDy(point.getPointy());
                break;
            }

        }


    }

    @Override
    public void draw(Canvas canvas) {
    }

}