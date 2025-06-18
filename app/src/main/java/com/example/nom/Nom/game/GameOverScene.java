package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.nom.framework.Metrics;
import com.example.nom.framework.Scene;
import com.example.nom.framework.GameView;

public class GameOverScene extends Scene {
    private final Paint textPaint = new Paint();

    public GameOverScene() {
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(120f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void update() {  }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.argb(200, 0, 0, 0));
        canvas.drawText("GAME OVER", Metrics.width / 2f, Metrics.height / 2f, textPaint);
        canvas.drawText("Tap to restart", Metrics.width / 2f, Metrics.height / 2f + 180, textPaint);
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent e) {
        if (e.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            GameView.view.popAllScenes();
            GameView.view.pushScene(new MainScene());
        }
        return true;
    }
}
