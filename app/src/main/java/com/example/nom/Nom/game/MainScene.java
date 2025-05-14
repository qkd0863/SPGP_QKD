package com.example.nom.Nom.game;

import android.util.Log;
import android.view.MotionEvent;

import com.example.nom.BuildConfig;
import com.example.nom.R;
import com.example.nom.framework.CollisionHelper;
import com.example.nom.framework.GameView;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Scene;
import com.example.nom.framework.Score;
import com.example.nom.framework.IGameObject;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Player player;
    private final Score score;
    private float speed = 200;

    public enum Layer {
        enemy, fighter, ui, controller;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        initLayers(Layer.COUNT);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;

        this.player = new Player();
        add(player);
        player.setX(800);
        player.setY(Metrics.height);

        this.score = new Score(R.mipmap.number_24x32, 850f, 50f, 60f);
        score.setScore(0);


        for (int i = 0; i < 10; i++) {
            add(Enemy.random());
        }

        add(Layer.ui, score);
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.controller, new TurnPoint(0, Metrics.height - 100, speed, 0));
        add(Layer.controller, new TurnPoint(Metrics.width - 100, 0, -speed, 0));
        add(Layer.controller, new TurnPoint(Metrics.width - 100, Metrics.height - 100, 0, -speed));
        add(Layer.controller, new TurnPoint(0, 0, 0, speed));
    }

    public void addScore(int amount) {
        score.add(amount);
    }


    public void checkTurnPointCollision() {
        ArrayList<IGameObject> controllers = objectsAt(Layer.controller);
        Player player = this.player;

        for (IGameObject obj : controllers) {
            if (!(obj instanceof TurnPoint)) continue;
            TurnPoint point = (TurnPoint) obj;
            if (CollisionHelper.collides(point, player)) {
                Log.d(TAG, "TurnPoint 충돌 - 방향 전환");
                player.setDx(point.getPointx());
                player.setDy(point.getPointy());
                break;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }

}
