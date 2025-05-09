package com.example.nom.Nom.app;

import android.os.Bundle;

import com.example.nom.Nom.game.MainScene;
import com.example.nom.framework.GameActivity;

public class NomActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}
