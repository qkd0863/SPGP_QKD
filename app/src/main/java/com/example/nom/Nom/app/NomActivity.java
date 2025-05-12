package com.example.nom.Nom.app;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.nom.Nom.game.MainScene;
import com.example.nom.R;
import com.example.nom.framework.GameActivity;
import com.example.nom.framework.GameView;
import com.example.nom.framework.Scene;

public class NomActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom);

        FrameLayout rootLayout = findViewById(R.id.nom);
        GameView gameView = new GameView(this);
        rootLayout.addView(gameView, 0);


        gameView.post(() -> {
            gameView.pushScene(new MainScene());
        });

    }

    public void JumpButton(View view) {
        Scene scene = GameView.view.getTopScene();
        if (scene instanceof MainScene) {
            ((MainScene) scene).checkTurnPointCollision();
        }
    }
}
