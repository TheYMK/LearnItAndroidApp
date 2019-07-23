package com.kaymkassai.learnit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    ListView favList;
    CustomWordListAdapter adapter;
    ArrayList<HskModel> words;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_favorite, container, false);


        favList = v.findViewById(R.id.favList);
        words = new ArrayList<>();
        adapter = new CustomWordListAdapter(words, getContext());

        //Filling the ListView
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Favorites");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("identifiant");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){

                    if(objects.size() > 0){

                        for(ParseObject object : objects){
                            words.add(new HskModel(object.get("identifiant").toString(),
                                    object.get("character").toString(),
                                    object.get("pinyin").toString(),
                                    object.get("meaning").toString()));
                        }

                        favList.setAdapter(adapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });

        favList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to remove this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ParseQuery<ParseObject> myQuery = new ParseQuery<ParseObject>("Favorites");
                                myQuery.whereEqualTo("character", words.get(position).getCharacter());
                                myQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                                myQuery.setLimit(1);


                                myQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objs, ParseException e) {
                                        if(e == null){
                                            objs.get(0).deleteInBackground(new DeleteCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if(e == null){
                                                        Toast.makeText(getContext(), words.get(position).getCharacter() + "Deleted in your favorites", Toast.LENGTH_SHORT).show();
                                                        words.remove(position);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });




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



        return v;
    }
}
