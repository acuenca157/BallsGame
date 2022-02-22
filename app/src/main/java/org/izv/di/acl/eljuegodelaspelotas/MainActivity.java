package org.izv.di.acl.eljuegodelaspelotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer btnClick, menuTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = MediaPlayer.create(this, R.raw.btnclick);
        menuTheme = MediaPlayer.create(this, R.raw.menutheme);

        menuTheme.setLooping(true);
        menuTheme.start();

        Button btEasy = findViewById(R.id.btEasy);
        btEasy.setOnClickListener(v -> {
            play(0);
        });

        Button btMedio = findViewById(R.id.btMedium);
        btMedio.setOnClickListener(v -> {
            play(1);
        });
        Button btHard = findViewById(R.id.btHard);
        btHard.setOnClickListener(v -> {
            play(2);
        });

        Button btCam = findViewById(R.id.btCam);
        btCam.setOnClickListener(v -> {
            menuTheme.stop();
            Intent intent = new Intent(this, camara.class);
            startActivity(intent);
        });

    }

    private void play(int d){
        menuTheme.stop();
        btnClick.start();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            GameData gd = new GameData(d);
            Intent intent = new Intent(this, GameScreen.class);
            intent.putExtra("gd", gd);
            startActivity(intent);
            finish();
        }, 1000);
    }
}