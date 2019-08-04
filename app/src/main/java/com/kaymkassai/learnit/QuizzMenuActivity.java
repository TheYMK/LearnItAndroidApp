package com.kaymkassai.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizzMenuActivity extends AppCompatActivity {

    Button btnHsk1, btnHsk2, btnHsk3, btnHsk4, btnHsk5, btnHsk6;




    public void quizzActivityOpener(View view){

        if(view.getId() == R.id.btnHsk1){
            Intent intent = new Intent(getApplicationContext(), QuizzActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.btnHsk2){
            Toast.makeText(getApplicationContext(), "HSK2 QUIZZ", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.btnHsk3){
            Toast.makeText(getApplicationContext(), "HSK3 QUIZZ", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.btnHsk4){
            Toast.makeText(getApplicationContext(), "HSK4 QUIZZ", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.btnHsk5){
            Toast.makeText(getApplicationContext(), "HSK5 QUIZZ", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.btnHsk6){
            Toast.makeText(getApplicationContext(), "HSK6 QUIZZ", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_menu);

        btnHsk1 = findViewById(R.id.btnHsk1);
        btnHsk2 = findViewById(R.id.btnHsk2);
        btnHsk3 = findViewById(R.id.btnHsk3);
        btnHsk4 = findViewById(R.id.btnHsk4);
        btnHsk5 = findViewById(R.id.btnHsk5);
        btnHsk6 = findViewById(R.id.btnHsk6);
        
    }
}
