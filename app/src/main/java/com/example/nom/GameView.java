package com.example.nom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {

    private static final String TAG = GameView.class.getSimpleName();

    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private static long previousNanos;
    public static float frameTime;
    public static GameView view;
    private ArrayList<Scene> sceneStack = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        GameView.view = this;
        scheduleUpdate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Scene scene = getTopScene();
        if (scene != null) {
            return scene.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void onBackPressed() {
        int last = sceneStack.size() - 1;
        if (last < 0) return; // finish activity here ?

        Scene scene = sceneStack.get(last);
        boolean handled = scene.onBackPressed();
        if (handled) return;

        popScene();
    }

    @Override
    public void doFrame(long nanos) {
        Log.d(TAG, "Nanos = " + nanos + " frameTime=" + frameTime);
        if (previousNanos != 0) {
            frameTime = (nanos - previousNanos) / 1_000_000_000f;
            update();
            invalidate();
        }
        previousNanos = nanos;
        if (isShown()) {
            scheduleUpdate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.onSize(w, h);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Metrics.concat(canvas);
        if (BuildConfig.DEBUG) {
            drawDebugBackground(canvas);
        }
        Scene scene = getTopScene();
        if (scene != null) {
            scene.draw(canvas);
        }
        canvas.restore();
        if (BuildConfig.DEBUG) {
            drawDebugInfo(canvas);
        }
    }

    private void drawDebugInfo(Canvas canvas) {
        if (fpsPaint == null) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.BLUE);
            fpsPaint.setTextSize(100f);
        }

        int fps = (int) (1.0f / frameTime);
        canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
    }


    private void update() {
        Scene scene = getTopScene();
        if (scene != null) {
            scene.update();
        }
    }

    private RectF borderRect;
    private Paint borderPaint, gridPaint, fpsPaint;

    private void drawDebugBackground(@NonNull Canvas canvas) {
        if (borderRect == null) {
            borderRect = new RectF(0, 0, Metrics.SCREEN_WIDTH, Metrics.SCREEN_HEIGHT);

            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(10f);
            borderPaint.setColor(Color.RED);

            gridPaint = new Paint();
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(1f);
            gridPaint.setColor(Color.GRAY);
        }

        canvas.drawRect(borderRect, borderPaint);
        for (float x = Metrics.GRID_UNIT; x < Metrics.SCREEN_WIDTH; x += Metrics.GRID_UNIT) {
            canvas.drawLine(x, 0, x, Metrics.SCREEN_HEIGHT, gridPaint);
        }
        for (float y = Metrics.GRID_UNIT; y < Metrics.SCREEN_HEIGHT; y += Metrics.GRID_UNIT) {
            canvas.drawLine(0, y, Metrics.SCREEN_WIDTH, y, gridPaint);
        }
    }

    public void pushScene(Scene scene) {
        int last = sceneStack.size() - 1;
        if (last >= 0) {
            sceneStack.get(last).onPause();
        }
        sceneStack.add(scene);
        scene.onEnter();
    }

    public Scene popScene() {
        int last = sceneStack.size() - 1;
        if (last < 0) {
            notifyEmptyStack();
            return null;
        }
        Scene top = sceneStack.remove(last);
        top.onExit();
        if (last >= 1) {
            sceneStack.get(last - 1).onResume();
        } else {
            notifyEmptyStack();
        }
        return top;
    }

    private void notifyEmptyStack() {
        if (emptyStackListener != null) {
            emptyStackListener.onEmptyStack();
        }
    }

    public void changeScene(Scene scene) {
        int last = sceneStack.size() - 1;
        if (last < 0) return;
        sceneStack.get(last).onExit();
        sceneStack.add(scene);
        scene.onEnter();
    }

    public Scene getTopScene() {
        //return sceneStack.getLast();
        // Call requires API level 35 (current min is 24): java. util. ArrayList#getLast
        int last = sceneStack.size() - 1;
        if (last < 0) return null;
        return sceneStack.get(last);
    }

    public interface OnEmptyStackListener {
        public void onEmptyStack();
    }

    private OnEmptyStackListener emptyStackListener;

    public void setEmptyStackListener(OnEmptyStackListener emptyStackListener) {
        this.emptyStackListener = emptyStackListener;
    }
}
