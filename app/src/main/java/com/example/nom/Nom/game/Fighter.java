package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.IBoxCollidable;
import com.example.nom.framework.ILayerProvider;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.Sprite;

public class Fighter extends Sprite implements IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private static final float RADIUS = 125f;
    private float angle;

    public Fighter() {
        super(R.mipmap.plane_240);

        float r = 50f;
        float cx = r * 2;
        float y = Metrics.height-4*r;
        this.dx = 10;


        dstRect.set(cx - r, y, cx + r, y + 2 * r);
    }

    public RectF getCollisionRect() {
        return dstRect;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas); // 직접 그려도 되고 super 를 불러도 된다.
    }

    public void update() {
        super.update();

    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }
}
