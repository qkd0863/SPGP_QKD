package com.example.nom.Nom.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.BitmapPool;
import com.example.nom.framework.GameView;
import com.example.nom.framework.IGameObject;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Sprite;

import java.util.Random;

public class Ball extends Sprite {

    private static final float BALL_RADIUS = 100f;
    private static final float SPEED = 700f; // 초당 700 unit 을 움직이는 속도.

    private static final Random random = new Random();

    public static Ball random() {
        return new Ball(random.nextFloat() * Metrics.width,
                random.nextFloat() * Metrics.height,
                random.nextFloat() * 360);
    }

    public Ball(float centerX, float centerY, float angle_degree) {
        super(R.mipmap.soccer_ball_240);
        setPosition(centerX, centerY, BALL_RADIUS);
        double radian = Math.toRadians(angle_degree);
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);


    }




    public void update() {
        super.update();
        if (dx > 0) {
            if (dstRect.right > Metrics.width) { // Alt+Enter -> Make GameView.SCREEN_WIDTH public
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }


}