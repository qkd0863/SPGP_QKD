package com.example.nom.Nom.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.BitmapPool;
import com.example.nom.framework.GameView;
import com.example.nom.framework.IGameObject;
import com.example.nom.framework.Metrics;

public class Fighter implements IGameObject {
    private final Bitmap bitmap;
    private final RectF dstRect = new RectF();

    public Fighter() {
        Resources res = GameView.view.getResources();
        bitmap = BitmapPool.get(R.mipmap.plane_240);

        float r = 125f;
        float cx = Metrics.width / 2;
        float y = 2 * Metrics.height / 3;


        dstRect.set(cx - r, y, cx + r, y + 2 * r);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void update() {

    }
}
