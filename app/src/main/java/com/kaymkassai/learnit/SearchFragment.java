package com.kaymkassai.learnit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment implements View.OnKeyListener{


    EditText txtSearch;
    ImageView btnSearch;
    ListView searchList;
    CustomWordListAdapter adapter;
    ArrayList<HskModel> words;


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            search();
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_search, container, false);

        txtSearch = v.findViewById(R.id.txtSearch);
        btnSearch = v.findViewById(R.id.btnSearch);
        searchList = v.findViewById(R.id.searchList);

        txtSearch.setOnKeyListener(this);

        words = new ArrayList<>();
        adapter = new CustomWordListAdapter(words, getContext());

        searchList.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        return v;
    }

    public void search(){

        if(txtSearch.getText().toString().matches("")){
            Toast.makeText(getContext(),"Type a word to search", Toast.LENGTH_SHORT).show();
        }else{
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HSK1");
            query.whereEqualTo("character", txtSearch.getText().toString());

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
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getContext(), "Word not found", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
