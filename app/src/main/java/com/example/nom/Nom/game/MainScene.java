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

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        fighter = new Fighter();


        for (int i = 0; i < 10; i++) {
            gameObjects.add(Ball.random());
        }
        add(fighter);
        AnimeSprite animSprite = new AnimeSprite(R.mipmap.enemy_01, 10);
        animSprite.setPosition(450f, 450f, 90f);
        add(animSprite);
    }


    @Override
    public void update() {
        super.update();
        checkCollision();
    }

    private void checkCollision() {
        for (IGameObject o1 : gameObjects) {
            if (!(o1 instanceof Ball)) {
                continue;
            }
            Ball ball = (Ball) o1;
//            boolean removed = false;

            if (CollisionHelper.collides(ball, fighter)) {
                Log.d(TAG, "Collision !!");
                remove(ball);
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
