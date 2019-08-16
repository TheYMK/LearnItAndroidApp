package com.kaymkassai.learnit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizzActivity extends AppCompatActivity {

    final static int MAX_CHAR = 153;
    final int min = 1;
    final int max = 109;
    int randomChar = 0;
    int locationCorrectAnswer;
    int incorrectAnswerLocation;
    int total = 0;
    int correctAnswers = 0;

    TextView quizzTitle_text, quizzQuestion_text, result_txt, score_txt;
    Button answer1_btn, answer2_btn, answer3_btn, answer4_btn, replay_btn;

    ArrayList<String> characters;
    ArrayList<String> pinyins;
    String[] answers;
    ArrayList<ParseObject> trash;

    public void generateQuizz() {
        final Random rand = new Random();
        //randomChar = rand.nextInt(characters.size());
        randomChar = rand.nextInt((max - min) + 1) + min;

        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("HSK1");

        answers = new String[4];
        answers[0] = "a";
        answers[1] = "b";
        answers[2] = "c";
        answers[3] = "d";

        System.out.println(randomChar);

        q.whereEqualTo("identifiant", randomChar);
        q.setLimit(1);

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            if (trash.contains(object)) {
                                //MANUAL DEBUGGING
                                Toast.makeText(getApplicationContext(), "" + object.getString("character") + " already displayed", Toast.LENGTH_SHORT).show();
                                generateQuizz();
                            } else {
                                trash.add(object);
                                quizzQuestion_text.setText(object.getString("character"));

                                locationCorrectAnswer = rand.nextInt(4);
                                Log.i("LOCATIONCORRECT", String.valueOf(locationCorrectAnswer));

                                for (int i = 0; i < 4; i++) {
                                    if (i == locationCorrectAnswer) {
                                        answers[i] = object.getString("pinyin");
                                    } else {
                                        incorrectAnswerLocation = rand.nextInt((max - min) + 1) + min;

                                        Log.i("INCORRECTANSWER", String.valueOf(incorrectAnswerLocation));

                                        //MANUAL DEBUGGING
                                        Log.i("CHARACTERSIZE", String.valueOf(characters.size()));

                                        incorrectAnswerLocation -= 1;

                                        while (incorrectAnswerLocation == (randomChar - 1)) {
                                            incorrectAnswerLocation = rand.nextInt((max - min) + 1) + min;
                                            Log.i("INCORRECTANSWER2", String.valueOf(incorrectAnswerLocation));
                                        }
                                        answers[i] = pinyins.get(incorrectAnswerLocation);
                                        Log.i("ANSWERi", pinyins.get(incorrectAnswerLocation));
                                    }
                                }
                                answer1_btn.setText(answers[0]);
                                answer2_btn.setText(answers[1]);
                                answer3_btn.setText(answers[2]);
                                answer4_btn.setText(answers[3]);
                            }

                        }
                    }

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void resetScore(){
        this.total = 0;
        this.correctAnswers = 0;
        score_txt.setText("00/00");
    }

    public void replay(View v){

        answer1_btn.setEnabled(true);
        answer2_btn.setEnabled(true);
        answer3_btn.setEnabled(true);
        answer4_btn.setEnabled(true);
        replay_btn.setVisibility(View.GONE);
        resetScore();

        generateQuizz();

    }

    public void checkAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))) {
            result_txt.setText("Correct");
            correctAnswers++;
            total++;
            score_txt.setText(correctAnswers + "/" + total);
            result_txt.setTextColor(Color.GREEN);
        } else {
            result_txt.setText("Incorrect");
            total++;
            score_txt.setText(correctAnswers + "/" + total);
            result_txt.setTextColor(Color.RED);
        }

        if(total == characters.size()){
            answer1_btn.setEnabled(false);
            answer2_btn.setEnabled(false);
            answer3_btn.setEnabled(false);
            answer4_btn.setEnabled(false);
            quizzQuestion_text.setText("");
            replay_btn.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "Correct: " + correctAnswers + "/" + "Incorrect: " + (total - correctAnswers), Toast.LENGTH_SHORT).show();



        }else{
            generateQuizz();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        characters = new ArrayList<>();
        pinyins = new ArrayList<>();
        trash = new ArrayList<>();

        quizzTitle_text = findViewById(R.id.quizzTitle_text);
        quizzQuestion_text = findViewById(R.id.quizzQuestion_text);
        result_txt = findViewById(R.id.result_txt);
        answer1_btn = findViewById(R.id.answer1_btn);
        answer2_btn = findViewById(R.id.answer2_btn);
        answer3_btn = findViewById(R.id.answer3_btn);
        answer4_btn = findViewById(R.id.answer4_btn);
        replay_btn = findViewById(R.id.replay_btn);
        score_txt = findViewById(R.id.score_txt);

        resetScore();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK1");
        query.setLimit(MAX_CHAR);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            characters.add(object.getString("character"));
                            pinyins.add(object.getString("pinyin"));
                        }
                        generateQuizz();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}