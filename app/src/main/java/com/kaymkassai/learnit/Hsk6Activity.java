package com.kaymkassai.learnit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Hsk6Activity extends AppCompatActivity {


    ListView hsk6List;
    CustomWordListAdapter adapter;
    ArrayList<HskModel> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsk6);


        hsk6List = findViewById(R.id.hsk6List);
        adapter = new CustomWordListAdapter(words, getApplicationContext());

        //Filling the ListView
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK6");
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

                        hsk6List.setAdapter(adapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

        hsk6List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Hsk6Activity.this)
                        .setIcon(android.R.drawable.ic_menu_add)
                        .setTitle("Add to favorite")
                        .setMessage("Do you want to add this item to your favorites?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Saving favorites words in the parse server
                                final ParseObject favWord = new ParseObject("Favorites");
                                favWord.put("identifiant", Integer.parseInt(words.get(position).getId()));
                                favWord.put("character", words.get(position).getCharacter());
                                favWord.put("pinyin", words.get(position).getPinyin());
                                favWord.put("meaning", words.get(position).getMeaning());
                                favWord.put("username", ParseUser.getCurrentUser().getUsername());

                                final ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Favorites");
                                query1.whereEqualTo("character", words.get(position).getCharacter());

                                query1.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if(e == null){
                                            if(objects.size() > 0){

                                                query1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

                                                query1.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> obj, ParseException e) {
                                                        if(e == null){
                                                            if(obj.size() > 0){
                                                                Toast.makeText(getApplicationContext(), "Word already in your favorites", Toast.LENGTH_SHORT).show();
                                                            }else{

                                                                favWord.saveInBackground(new SaveCallback() {
                                                                    @Override
                                                                    public void done(ParseException e) {
                                                                        if(e == null){
                                                                            Toast.makeText(getApplicationContext(), "Added in favorites successfully", Toast.LENGTH_SHORT).show();
                                                                        }else{
                                                                            Toast.makeText(getApplicationContext(), "Error while adding data in favorites", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                });

                                                            }
                                                        }
                                                    }
                                                });
                                            }else{

                                                favWord.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if(e == null){
                                                            Toast.makeText(getApplicationContext(), "Added in favorites successfully", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(getApplicationContext(), "Error while adding data in favorites", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                            }
                                        }else{
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }
}
