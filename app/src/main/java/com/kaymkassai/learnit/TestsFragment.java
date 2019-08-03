package com.kaymkassai.learnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TestsFragment extends Fragment implements View.OnClickListener {


    CardView cvQuizz, cvHskTest;


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.cvQuizz){

            Intent intent = new Intent(getContext(), QuizzActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.cvHskTest){
            Toast.makeText(getContext(), "HSK test", Toast.LENGTH_SHORT).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_tests, container, false);

        cvQuizz = v.findViewById(R.id.cvQuizz);
        cvHskTest = v.findViewById(R.id.cvHskTest);

        cvQuizz.setOnClickListener(this);
        cvHskTest.setOnClickListener(this);

        return v;
    }

}
