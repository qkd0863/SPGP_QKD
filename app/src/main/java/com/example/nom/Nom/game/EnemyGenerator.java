package com.example.nom.Nom.game;
import com.example.nom.framework.IGameObject;
import com.example.nom.framework.Metrics;
import com.example.nom.framework.GameView;

import java.util.Random;

public class EnemyGenerator implements IGameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 4.0f;
    private static final int INITIAL_ENEMIES_PER_WAVE = 3;
    private static final float DIFFICULTY_UP_INTERVAL = 100.0f;

    private float elapsed = 0f;
    private float timeSinceStart = 0f;

    private float spawnInterval = INITIAL_SPAWN_INTERVAL;
    private int enemiesPerWave = INITIAL_ENEMIES_PER_WAVE;

    private final MainScene scene;
    private final Random random = new Random();

    public EnemyGenerator(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update() {
        float frameTime = GameView.view.getFrameTime();
        timeSinceStart += frameTime;
        elapsed += frameTime;


        if (timeSinceStart > DIFFICULTY_UP_INTERVAL * 2) {
            spawnInterval = 1.0f;
            enemiesPerWave = 5;
        } else if (timeSinceStart > DIFFICULTY_UP_INTERVAL) {
            spawnInterval = 1.5f;
            enemiesPerWave = 4;
        }

        if (elapsed >= spawnInterval) {
            elapsed -= spawnInterval;
            spawnWave();
        }
    }

    private void spawnWave() {
        for (int i = 0; i < enemiesPerWave; i++) {
            scene.add(generatePatternEnemy(i));
        }
    }

    private Enemy generatePatternEnemy(int index) {
        float x, y, angle;

        switch (index % 3) {
            default:
                x = Metrics.width / 2;
                y = Metrics.height / 2;
                angle = random.nextFloat() * 360f;
                break;
        }

        return new Enemy(x, y, angle);
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {

    }
}
