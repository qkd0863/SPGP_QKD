package com.example.nom.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Sprite implements IGameObject {
    protected Bitmap bitmap;
    protected Rect srcRect = null;
    protected final RectF dstRect = new RectF();
    protected float x, y, dx, dy;
    protected float width, height, radius;

    public Sprite(int mipmapId) {
        if (mipmapId != 0) {
            bitmap = BitmapPool.get(mipmapId);
        }
    }
    public void setImageResourceId(int mipmapId) {
        bitmap = BitmapPool.get(mipmapId);
    }
    public void setPosition(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.width = this.height = 2 * radius;
        RectUtil.setRect(dstRect, x, y, radius);

    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        radius = Math.min(width, height) / 2;
        RectUtil.setRect(dstRect, x, y, width, height);
    }

    @Override
    public void update() {
        float timedDx = dx * (GameView.view.getFrameTime() > 0 ? GameView.view.getFrameTime() : 0.016f);
        float timedDy = dy * (GameView.view.getFrameTime() > 0 ? GameView.view.getFrameTime() : 0.016f);

        x += timedDx;
        y += timedDy;
        dstRect.offset(timedDx, timedDy);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setX(float x) {
        this.x = x;
        RectUtil.setRect(dstRect, x, y, width, height);
    }

    public void setY(float y) {
        this.y = y;
        RectUtil.setRect(dstRect, x, y, width, height);
    }
}


