package com.example.nom.Nom.game;

import android.util.Log;
import android.view.MotionEvent;

import com.example.nom.BuildConfig;
import com.example.nom.R;
import com.example.nom.framework.AnimeSprite;
import com.example.nom.framework.CollisionHelper;
import com.example.nom.framework.GameView;
import com.example.nom.framework.IGameObject;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Scene;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;

    public enum Layer {
        enemy, fighter;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        initLayers(Layer.COUNT);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        this.fighter = new Fighter();
        add(Layer.fighter.ordinal(), fighter);
        //add(Layer.controller.ordinal(), new EnemyGenerator());

        for (int i = 0; i < 10; i++) {
            add(Layer.enemy.ordinal(), Ball.random());
        }

        AnimeSprite animSprite = new AnimeSprite(R.mipmap.enemy_01, 10);
        animSprite.setPosition(450f, 450f, 90f);
        add(Layer.enemy.ordinal(), animSprite);
    }


    @Override
    public void update() {
        super.update();
        checkCollision();
    }

    private void checkCollision() {
        ArrayList<IGameObject> enemies = getLayer(Layer.enemy.ordinal());
        for (int i1 = enemies.size() - 1; i1 >= 0; i1--) {
            if (!(enemies.get(i1) instanceof Ball)) continue;
            Ball ball = (Ball) enemies.get(i1);

            if (CollisionHelper.collides(ball, fighter)) {
                Log.d(TAG, "Collision !!");
                remove(Layer.enemy.ordinal(), ball);
//                    removed = true;
                break;
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] xy = Metrics.fromScreen(event.getX(), event.getY());
                if (xy[0] < 100 && xy[1] < 100) {
                    new SubScene().push();
                    return false;
                }

                return true;
        }
        return false;
    }

}
