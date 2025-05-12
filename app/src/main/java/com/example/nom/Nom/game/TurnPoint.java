package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.IBoxCollidable;
import com.example.nom.framework.ILayerProvider;
import com.example.nom.framework.Sprite;

public class TurnPoint extends Sprite implements IBoxCollidable, ILayerProvider<MainScene.Layer> {

    private float pointx, pointy;
    private boolean Active = false;


    public TurnPoint(float x, float y, float pointx, float pointy) {
        super(R.mipmap.turn_point);
        this.x = x;
        this.y = y;
        this.pointx = pointx;
        this.pointy = pointy;

        this.radius = 50;
        dstRect.set(x, y, x + 2 * radius, y + 2 * radius);
    }

    public RectF getCollisionRect() {
        return dstRect; // 충돌 범위
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void update() {

    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.controller;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public float getPointx() {
        return pointx;
    }

    public float getPointy() {
        return pointy;
    }
}