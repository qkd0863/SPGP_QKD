package com.example.nom.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimeSprite extends Sprite {
    protected float fps;
    protected int frameCount;
    protected int frameWidth;
    protected int frameHeight;
    protected final long createdOn;

    public AnimeSprite(int mipmapId, float fps) {
        this(mipmapId, fps, 0);
    }

    public AnimeSprite(int mipmapId, float fps, int frameCount) {
        super(mipmapId);
        this.fps = fps;
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            this.frameWidth = imageHeight;
            this.frameHeight = imageHeight;
            this.frameCount = imageWidth / imageHeight;
        } else {
            this.frameWidth = imageWidth / frameCount;
            this.frameHeight = imageHeight;
            this.frameCount = frameCount;
        }
        srcRect = new Rect();
        createdOn = System.currentTimeMillis();
    }

    private void setFrameInfo(float fps, int frameCount) {
        this.fps = fps;
        int imageWidth = bitmap.getWidth();
        int imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            this.frameWidth = imageHeight;
            this.frameHeight = imageHeight;
            this.frameCount = imageWidth / imageHeight;
        } else {
            this.frameWidth = imageWidth / frameCount;
            this.frameHeight = imageHeight;
            this.frameCount = frameCount;
        }
    }

    public void setImageResourceId(int mipmapId, float fps) {
        setImageResourceId(mipmapId, fps, 0);
    }
    public void setImageResourceId(int mipmapId, float fps, int frameCount) {
        super.setImageResourceId(mipmapId);
        setFrameInfo(fps, frameCount);
    }
    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
