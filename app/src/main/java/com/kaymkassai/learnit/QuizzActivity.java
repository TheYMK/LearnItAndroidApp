package com.kaymkassai.learnit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizzActivity extends AppCompatActivity {

    TextView quizzTitle_text, quizzQuestion_text;
    Button answer1_txt, answer2_txt, answer3_txt, answer4_txt;

    ArrayList<String> characters;
    ArrayList<String> pinyins;

    String[] answers = new String[4];

    int randomChar = 0;
    int locationCorrectAnswer;
    int incorrectAnswerLocation;


    public void generateQuizz(){
        final Random rand = new Random();
        randomChar = rand.nextInt(characters.size());

        answers[0] = "a";
        answers[1] = "b";
        answers[2] = "c";
        answers[3] = "d";


        System.out.println(randomChar);

        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("HSK1");
        q.whereEqualTo("identifiant", randomChar);
        q.setLimit(1);

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){

                    if(objects.size() > 0){
                        for (ParseObject object : objects){
                            quizzQuestion_text.setText(object.getString("character"));
                            answer1_txt.setText(object.getString("pinyin"));

                            locationCorrectAnswer = rand.nextInt(4);

                            for(int i = 0; i < 4; i++){
                                if(i == locationCorrectAnswer){
                                    answers[i] = object.getString("pinyin");
                                }else{
                                    incorrectAnswerLocation = rand.nextInt(characters.size());

                                    while(incorrectAnswerLocation == randomChar){
                                        incorrectAnswerLocation = rand.nextInt(characters.size());
                                    }

                                    answers[i] = pinyins.get(incorrectAnswerLocation);
                                }
                            }

                            answer1_txt.setText(answers[0]);
                            answer2_txt.setText(answers[1]);
                            answer3_txt.setText(answers[2]);
                            answer4_txt.setText(answers[3]);

                        }
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }


    public void checkAnswer(View view){


        if(view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))){


            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(getApplicationContext(), "Wrong it was ", Toast.LENGTH_LONG).show();

        }


        generateQuizz();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        characters = new ArrayList<>();
        pinyins = new ArrayList<>();

        quizzTitle_text = findViewById(R.id.quizzTitle_text);
        quizzQuestion_text = findViewById(R.id.quizzQuestion_text);
        answer1_txt = findViewById(R.id.answer1_txt);
        answer2_txt = findViewById(R.id.answer2_txt);
        answer3_txt = findViewById(R.id.answer3_txt);
        answer4_txt = findViewById(R.id.answer4_txt);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK1");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        for(ParseObject object : objects){
                            characters.add(object.getString("character"));
                            pinyins.add(object.getString("pinyin"));
                        }

                        generateQuizz();
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

    }

}
