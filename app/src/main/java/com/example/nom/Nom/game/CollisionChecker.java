package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.nom.framework.CollisionHelper;
import com.example.nom.framework.IGameObject;

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

        Player player = null;
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i) instanceof Player) {
                player = (Player) enemies.get(i);
                break;
            }
        }

        if (player == null) return;

        for (IGameObject obj : enemies) {
            if (!(obj instanceof Enemy)) continue;
            Enemy enemy = (Enemy) obj;


            if (RectF.intersects(player.getDstRect(), enemy.getDstRect())) {
                player.takeDamage(1);
                break;
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
    }

}