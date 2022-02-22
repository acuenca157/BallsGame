package org.izv.di.acl.eljuegodelaspelotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class answer extends AppCompatActivity {

    private MediaPlayer questiontheme, fail, win;

    private GameData gd;
    private String answer, question;

    private TextView tvQuestion;
    private EditText etUserAnswer;

    Button btExit, btPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        gd = getIntent().getParcelableExtra("gd");

        questiontheme = MediaPlayer.create(this, R.raw.questiontheme);
        fail = MediaPlayer.create(this, R.raw.fail);
        win = MediaPlayer.create(this, R.raw.win);
        questiontheme.setLooping(true);
        questiontheme.start();

        tvQuestion = findViewById(R.id.questionTitle);
        etUserAnswer = findViewById(R.id.questionInbox);

        btExit = findViewById(R.id.btBack);
        btPhoto = findViewById(R.id.btTakePhoto);

        btExit.setVisibility(View.GONE);
        btPhoto.setVisibility(View.GONE);

        btExit.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btPhoto.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, camara.class);
            startActivity(intent);
            finish();
        });

        Button btResolve = findViewById(R.id.btSubmit);
        btResolve.setOnClickListener(v -> {
            if(etUserAnswer.getText().toString().isEmpty()){
                Toast alert = Toast.makeText(v.getContext(), "La respuesta no puede estar vacía", Toast.LENGTH_SHORT);
                alert.show();
            } else {
                btResolve.setEnabled(false);
                resolveQuestion(etUserAnswer.getText().toString());
            }
        });
        
        generateQuestion();
    }

    private void resolveQuestion(String userAnswer){
        questiontheme.stop();
        Toast alert;

        if (answer.equals(userAnswer)){
            win.start();
            alert = Toast.makeText(this, "Has ganado!", Toast.LENGTH_SHORT);
            btExit.setVisibility(View.VISIBLE);
            btPhoto.setVisibility(View.VISIBLE);
        } else {
            fail.start();
            alert = Toast.makeText(this, "Has perdido...", Toast.LENGTH_SHORT);
            btExit.setVisibility(View.VISIBLE);
            btPhoto.setVisibility(View.GONE);
        }
        alert.show();
    }

    private void generateQuestion() {
        switch (gd.difficulty){
            case 0:
                question = "¿Cuántos colores había?";
                this.answer = gd.nColors + "";
                break;
            case 1:
                question = "¿Cuántas pelotas había?";
                this.answer = gd.totalNBalls + "";
                break;
            case 2:
                getColorCountAnswer();
                break;
        }

        tvQuestion.setText(question);
    }

    private void getColorCountAnswer() {
        int posColor = ThreadLocalRandom.current().nextInt(0, gd.nColors);
        question = "¿Cuantás pelotas de color " + getColorName(gd.ballsColors[posColor]) + " había?";
        answer = gd.ballsCount[posColor] + "";

    }

    private String getColorName(int color){
        String nombre = "";
        //Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA
        switch (color){
            case Color.BLUE:
                nombre = "azul";
                break;
            case Color.RED:
                nombre = "rojo";
                break;
            case Color.GREEN:
                nombre = "verde";
                break;
            case Color.YELLOW:
                nombre = "amarillo";
                break;
            case Color.MAGENTA:
                nombre = "magenta";
                break;
        }
        return nombre;
    }
}