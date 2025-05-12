package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.IBoxCollidable;
import com.example.nom.framework.ILayerProvider;
import com.example.nom.framework.Sprite;

public class TurnPoint extends Sprite implements  IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private float x, y, r;


    public TurnPoint(float x, float y) {
        super(R.mipmap.turn_point);
        this.x = x;
        this.y = y;
        this.r = 50;
        dstRect.set(x ,y, x + 2*r, y + 2*r);
    }

    public RectF getCollisionRect() {
        return dstRect; // 충돌 범위
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void update() {

    }

    public float getR()
    {
        return r;
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }
}