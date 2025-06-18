package com.example.nom.Nom.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.nom.framework.IGameObject;
import com.example.nom.Nom.game.Player;
import com.example.nom.R;
import com.example.nom.framework.BitmapPool;

public class HpUi implements IGameObject{
    private final Player player;
    private final Bitmap fullHeart;
    private final Bitmap emptyHeart;

    private final float startX = 0f;
    private final float startY = -100f;
    private final float gap = 6f;

    public HpUi(Player player) {
        this.player = player;
        this.fullHeart = BitmapPool.get(R.mipmap.heart_full);
        this.emptyHeart = BitmapPool.get(R.mipmap.heart_empty);
    }

    @Override
    public void update() {
        // nothing needed
    }

    @Override
    public void draw(Canvas canvas) {
        int max = player.getMaxHp();
        int current = player.getCurrentHp();

        float scale = 0.1f; // 20% 크기로 축소
        int heartWidth = (int)(fullHeart.getWidth() * scale);
        int heartHeight = (int)(fullHeart.getHeight() * scale);

        for (int i = 0; i < max; i++) {
            Bitmap heart = (i < current) ? fullHeart : emptyHeart;
            float x = startX + i * (heartWidth + 10); // 10px 간격

            Rect src = new Rect(0, 0, heart.getWidth(), heart.getHeight());
            Rect dst = new Rect((int)x, (int)startY, (int)(x + heartWidth), (int)(startY + heartHeight));

            canvas.drawBitmap(heart, src, dst, null);
        }
    }

}
