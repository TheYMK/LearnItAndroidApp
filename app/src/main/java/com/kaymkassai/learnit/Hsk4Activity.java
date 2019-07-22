package com.kaymkassai.learnit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Hsk4Activity extends AppCompatActivity {


    ListView hsk4List;
    CustomWordListAdapter adapter;
    ArrayList<HskModel> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsk4);


        hsk4List = findViewById(R.id.hsk4List);
        adapter = new CustomWordListAdapter(words, getApplicationContext());

        //Filling the ListView
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK4");
        query.addAscendingOrder("identifiant");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){

                    if(objects.size() > 0){

                        for(ParseObject object : objects){
                            words.add(new HskModel(object.get("identifiant").toString(),
                                    object.get("character").toString(),
                                    "(" + object.get("pinyin").toString()+ ")",
                                    object.get("meaning").toString()));
                        }

                        hsk4List.setAdapter(adapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

    }
}
