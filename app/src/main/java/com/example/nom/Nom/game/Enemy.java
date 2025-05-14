package com.example.nom.Nom.game;

import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.IBoxCollidable;
import com.example.nom.framework.ILayerProvider;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Sprite;

import java.util.Random;

public class Enemy extends Sprite implements IBoxCollidable , ILayerProvider<MainScene.Layer> {

    private static final float BALL_RADIUS = 100f;
    private static final float SPEED = 700f; // 초당 700 unit 을 움직이는 속도.

    private static final Random random = new Random();

    public static Enemy random() {
        return new Enemy(random.nextFloat() * Metrics.width,
                random.nextFloat() * Metrics.height,
                random.nextFloat() * 360);
    }

    public Enemy(float centerX, float centerY, float angle_degree) {
        super(R.mipmap.enemy);
        setPosition(centerX, centerY, BALL_RADIUS);
        double radian = Math.toRadians(angle_degree);
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);


    }

    public RectF getCollisionRect() {
        return dstRect;
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

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }

}