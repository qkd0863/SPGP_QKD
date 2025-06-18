package com.example.nom.Nom.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.nom.R;
import com.example.nom.framework.AnimeSprite;
import com.example.nom.framework.GameView;
import com.example.nom.framework.IBoxCollidable;
import com.example.nom.framework.ILayerProvider;
import com.example.nom.framework.Metrics;

import android.view.MotionEvent;

public class Player extends AnimeSprite implements IBoxCollidable, ILayerProvider<MainScene.Layer> {
    public enum State {
        // atk == doubleJump
        running, rotating, jump, hurt, atk
    }

    protected State state = State.running;
    protected static Rect[][] srcRectsArray = {
            makeRects(100, 101, 102, 103), // running
            makeRects(100, 101, 102, 103), // rotating
            makeRects(7, 8),               // jump
            makeRects(503, 504),           // hurt
            makeRects(1, 2, 3, 4),         // doubleJump == atk
    };

    protected static Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 72 + (idx % 100) * 272;
            int t = 132 + (idx / 100) * 272;
            rects[i] = new Rect(l, t, l + 140, t + 140);
        }
        return rects;
    }
    private int maxHp = 3;
    private int currentHp = 3;
    private float atkTimer = 0f;
    private static final float ATK_DURATION = 0.5f;
    private static final float RADIUS = 125f;

    private float angle;
    private float prevDx, prevDy;
    private float bounceTimer = 0f;
    private static final float BOUNCE_DELAY = 0.7f;
    private static final float BOUNCE_POWER = 0.7f;


    private float rotation = 0f;
    private float rotationSpeed = 0f;
    private float remainingRotation = 0f;

    private static final float DEFAULT_ROTATION_SPEED = -720f;
    private static final float ROTATION_ANGLE = 90f;

    public Player() {
        super(R.mipmap.cookie_player_sheet, 8);

        float r = 50f;
        float cx = r * 2;
        float y = Metrics.height - 4 * r;
        this.dx = 200;

        dstRect.set(cx - r, y, cx + r, y + 2 * r);
    }

    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRectsArray[state.ordinal()];
        int frameIndex = (int) (time * fps) % rects.length;

        canvas.save();
        float cx = dstRect.centerX();
        float cy = dstRect.centerY();
        canvas.rotate(rotation, cx, cy);
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
        canvas.restore();
    }

    public void update() {
        float frameTime = GameView.view.getFrameTime() > 0 ? GameView.view.getFrameTime() : 0.016f;

        if (state == State.atk) {
            atkTimer -= frameTime;
            if (atkTimer <= 0f) {
                state = State.running;
            }
        }

        if (state == State.rotating) {
            float deltaRotation = Math.abs(rotationSpeed) * frameTime;

            if (deltaRotation >= remainingRotation) {
                deltaRotation = remainingRotation;
                state = State.running;
            }

            rotation += deltaRotation * Math.signum(rotationSpeed);
            remainingRotation -= deltaRotation;
        }

        if (state == State.running || state == State.jump) {
            move(frameTime);
        }

        if (bounceTimer > 0f) {
            bounceTimer -= frameTime;
            move(frameTime);
            if (bounceTimer <= 0f) {
                dx = prevDx;
                dy = prevDy;
                state = State.running;
            }
        } else {
            if (checkCollision()) {
                bounceTimer = BOUNCE_DELAY;
            }
        }
    }


    public void startRotation() {
        if (state != State.rotating) {
            state = State.rotating;
            rotationSpeed = DEFAULT_ROTATION_SPEED;
            remainingRotation = ROTATION_ANGLE;
        }
    }

    public void jump() {

    }
    public void attack() {
        if (state == State.running || state == State.jump) {
            state = State.atk;
            atkTimer = ATK_DURATION;
        }
    }
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
        return false;
    }

    private void move(float frameTime) {
        float timedDx = dx * frameTime;
        float timedDy = dy * frameTime;

        x += timedDx;
        y += timedDy;
        dstRect.offset(timedDx, timedDy);
    }

    private boolean checkCollision() {
        boolean collided = false;

        float minX = 0;
        float maxX = Metrics.width;
        float minY = 0;
        float maxY = Metrics.height;

        if (dstRect.left < minX) {
            float diff = minX - dstRect.left;
            dstRect.offset(diff, 0);
            dx = -dx * BOUNCE_POWER;
            collided = true;
        }
        if (dstRect.right > maxX) {
            float diff = maxX - dstRect.right;
            dstRect.offset(diff, 0);
            dx = -dx * BOUNCE_POWER;
            collided = true;
        }
        if (dstRect.top < minY) {
            float diff = minY - dstRect.top;
            dstRect.offset(0, diff);
            dy = -dy * BOUNCE_POWER;
            collided = true;
        }
        if (dstRect.bottom > maxY) {
            float diff = maxY - dstRect.bottom;
            dstRect.offset(0, diff);
            dy = -dy * BOUNCE_POWER;
            collided = true;
        }

        if (collided) {
            state = State.hurt;
        }

        return collided;
    }

    public void snapToWall(TurnPoint turnPoint) {
        float halfWidth = dstRect.width() / 2;
        float halfHeight = dstRect.height() / 2;

        float tolerance = 20f; // TurnPoint를 구분하는 허용 오차

        if (Math.abs(turnPoint.getX() - 0) < tolerance) {
            // 왼쪽 벽
            x = halfWidth;
        } else if (Math.abs(turnPoint.getX() - (Metrics.width - 100)) < tolerance) {
            // 오른쪽 벽
            x = Metrics.width - halfWidth;
        }

        if (Math.abs(turnPoint.getY() - 0) < tolerance) {
            // 상단 벽
            y = halfHeight;
        } else if (Math.abs(turnPoint.getY() - (Metrics.height - 100)) < tolerance) {
            // 하단 벽
            y = Metrics.height - halfHeight;
        }

        // dstRect 갱신
        dstRect.set(x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight);
    }

    public void setDx(float _dx) {
        this.dx = _dx;
        prevDx = dx;
    }

    public void setDy(float _dy) {
        this.dy = _dy;
        prevDy = dy;
    }

    public void setX(float _x) {
        this.x = _x;
    }

    public void setY(float _y) {
        this.y = _y;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void takeDamage(int amount) {
        currentHp -= amount;
        if (currentHp < 0) currentHp = 0;
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }
}
