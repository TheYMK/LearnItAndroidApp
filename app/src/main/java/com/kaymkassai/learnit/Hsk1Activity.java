package com.kaymkassai.learnit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Hsk1Activity extends AppCompatActivity {


    ListView hsk1List;
    CustomWordListAdapter adapter;
    ArrayList<HskModel> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsk1);

        hsk1List = findViewById(R.id.hsk1List);
        adapter = new CustomWordListAdapter(words, getApplicationContext());

        //Filling the ListView
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK1");
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

                        hsk1List.setAdapter(adapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

    }
}
