package com.kaymkassai.learnit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizzActivity extends AppCompatActivity {

    TextView quizzTitle_text, quizzQuestion_text, result_txt;
    Button answer1_txt, answer2_txt, answer3_txt, answer4_txt;

    final static int MAX_CHAR = 153;

    ArrayList<String> characters;
    ArrayList<String> pinyins;

    String[] answers;

    ArrayList<ParseObject> trash;

    final int min = 1;
    final int max = 109;
    int randomChar = 0;
    int locationCorrectAnswer;
    int incorrectAnswerLocation;


    public void generateQuizz(){
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
                if(e == null){

                    if(objects.size() > 0){
                        for (ParseObject object : objects){

                            if(trash.contains(object)){

                                //MANUAL DEBUGGING
                                Toast.makeText(getApplicationContext(), "" + object.getString("character") + " already displayed", Toast.LENGTH_SHORT).show();

                                generateQuizz();
                            }else{
                              trash.add(object);
                              quizzQuestion_text.setText(object.getString("character"));
//                            answer1_txt.setText(object.getString("pinyin"));

                                locationCorrectAnswer = rand.nextInt(4);
                                Log.i("LOCATIONCORRECT", String.valueOf(locationCorrectAnswer));

                                for(int i = 0; i < 4; i++){
                                    if(i == locationCorrectAnswer){
                                        answers[i] = object.getString("pinyin");
                                    }else{
                                        //incorrectAnswerLocation = rand.nextInt(characters.size());
                                        incorrectAnswerLocation = rand.nextInt((max - min) + 1) + min;

                                        Log.i("INCORRECTANSWER", String.valueOf(incorrectAnswerLocation));

                                        //MANUAL DEBUGGING
                                        Log.i("CHARACTERSIZE", String.valueOf(characters.size()));

                                        incorrectAnswerLocation -= 1;


                                        while(incorrectAnswerLocation == (randomChar -1)){
                                            incorrectAnswerLocation = rand.nextInt((max - min) + 1) + min;;
                                            Log.i("INCORRECTANSWER2", String.valueOf(incorrectAnswerLocation));
                                        }

                                        answers[i] = pinyins.get(incorrectAnswerLocation);
                                        Log.i("ANSWERi", pinyins.get(incorrectAnswerLocation));
                                    }
                                }

                                answer1_txt.setText(answers[0]);
                                answer2_txt.setText(answers[1]);
                                answer3_txt.setText(answers[2]);
                                answer4_txt.setText(answers[3]);
                            }

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
            result_txt.setText("Correct");
            result_txt.setTextColor(Color.GREEN);
        }else{
            result_txt.setText("Incorrect");
            result_txt.setTextColor(Color.RED);
        }

        generateQuizz();


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
        answer1_txt = findViewById(R.id.answer1_txt);
        answer2_txt = findViewById(R.id.answer2_txt);
        answer3_txt = findViewById(R.id.answer3_txt);
        answer4_txt = findViewById(R.id.answer4_txt);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK1");
        query.setLimit(MAX_CHAR);

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
