package com.example.nom.Nom.game;

import android.view.MotionEvent;

import com.example.nom.BuildConfig;
import com.example.nom.R;
import com.example.nom.framework.AnimeSprite;
import com.example.nom.framework.GameView;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Scene;
import com.example.nom.framework.Score;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    private Fighter fighter;
    private final Score score;

    public enum Layer {
        enemy, fighter, ui, controller;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        Metrics.setGameSize(900, 1600);
        initLayers(Layer.COUNT);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;

        this.fighter = new Fighter();
        add(fighter);

        this.score = new Score(R.mipmap.number_24x32, 850f, 50f, 60f);
        score.setScore(0);


        for (int i = 0; i < 10; i++) {
            add(Ball.random());
        }

        AnimeSprite animSprite = new AnimeSprite(R.mipmap.enemy_01, 10);
        animSprite.setPosition(450f, 450f, 90f);

        add(Layer.ui, score);
        add(Layer.enemy, animSprite);
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.enemy, new TurnPoint(0, Metrics.height - 100));
        add(Layer.enemy, new TurnPoint(Metrics.width-100, 0));
        add(Layer.enemy, new TurnPoint(Metrics.width-100, Metrics.height - 100));
        add(Layer.enemy, new TurnPoint(0, 0));
    }

    public void addScore(int amount) {
        score.add(amount);
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
