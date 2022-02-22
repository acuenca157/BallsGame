package org.izv.di.acl.eljuegodelaspelotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen extends AppCompatActivity {

    AnimationDrawable countAnimation;
    MediaPlayer countPlayer, gameTheme, finish;
    GameData gd;
    BouncingBallInside BBI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gd = getIntent().getParcelableExtra("gd");
        BBI = new BouncingBallInside(this);
        cuentaAtras();
    }

    void cuentaAtras(){
        countPlayer = MediaPlayer.create(this, R.raw.countdown);
        gameTheme = MediaPlayer.create(this, R.raw.gametheme);
        finish = MediaPlayer.create(this, R.raw.finish);

        ImageView countImage = findViewById(R.id.countdown);
        countImage.setImageResource(R.drawable.count_animation);
        countAnimation = (AnimationDrawable) countImage.getDrawable();

        countPlayer.start();
        countAnimation.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            countImage.setActivated(false);
            gameTheme.start();
            generateBalls();
        }, 6000);
    }

    void generateBalls(){
        for (int i = 0; i < gd.ballsCount.length; i++) {
            int color = gd.ballsColors[i];
            int nBalls = gd.ballsCount[i];
            for (int k = 0; k < nBalls; k++){
                int speed =  ThreadLocalRandom.current().nextInt(10, 25);
                int x = ThreadLocalRandom.current().nextInt(50, 500);
                int y = ThreadLocalRandom.current().nextInt(50, 500);
                BBI.balls.add(new Ball(x,y,100, color, speed));
            }
        }

        setContentView(BBI);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            gameTheme.stop();
            finish.start();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(this, answer.class);
                intent.putExtra("gd", gd);
                startActivity(intent);
                finish();
            },3000);
        }, 25000);
    }
}