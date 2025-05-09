package com.example.nom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Fighter implements IGameObject {
    private final Bitmap bitmap;
    private final RectF dstRect = new RectF();

    public Fighter() {
        Resources res = GameView.view.getResources();
        bitmap = BitmapPool.get(R.mipmap.plane_240);
        float cx = 500f, y = 1200f;
        float r = 125f;
        dstRect.set(cx-r, y, cx+r, y+2*r);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void update() {

    }
}
