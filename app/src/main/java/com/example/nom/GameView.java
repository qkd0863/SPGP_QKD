package com.example.nom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameView extends View {
    private static final float SCREEN_WIDTH = 900f;
    private static final float SCREEN_HEIGHT = 1600f;
    private final Matrix transformMatrix = new Matrix();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint = new Paint();

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10.0f);
        borderPaint.setColor(Color.RED);
    }

    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(borderRect, borderPaint);
    }
}
