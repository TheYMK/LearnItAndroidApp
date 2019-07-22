package com.kaymkassai.learnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordListFragment extends Fragment {


    ListView hskLevelList;
    ArrayAdapter adapter;
    ArrayList<String> hskLevels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_wordlist, container, false);

        hskLevelList = v.findViewById(R.id.hskLevelList);
        hskLevels.add("HSK 1");
        hskLevels.add("HSK 2");
        hskLevels.add("HSK 3");
        hskLevels.add("HSK 4");
        hskLevels.add("HSK 5");
        hskLevels.add("HSK 6");

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, hskLevels);
        hskLevelList.setAdapter(adapter);

        hskLevelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (hskLevels.get(position)){
                    case "HSK 1":
                        Intent i = new Intent(getContext(), Hsk1Activity.class);
                        startActivity(i);
                        break;
                    case "HSK 2":
                        Intent j = new Intent(getContext(), Hsk2Activity.class);
                        startActivity(j);
                        break;
                    case "HSK 3":
                        Intent k = new Intent(getContext(), Hsk3Activity.class);
                        startActivity(k);
                        break;
                    case "HSK 4":
                        Intent l = new Intent(getContext(), Hsk4Activity.class);
                        startActivity(l);
                        break;
                    case "HSK 5":
                        Intent m = new Intent(getContext(), Hsk5Activity.class);
                        startActivity(m);
                        break;
                    case "HSK 6":
                        Intent n = new Intent(getContext(), Hsk6Activity.class);
                        startActivity(n);
                        break;
                }

            }
        });

        return v;
    }
}
