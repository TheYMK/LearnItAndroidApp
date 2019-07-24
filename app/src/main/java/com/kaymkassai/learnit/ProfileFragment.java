package com.kaymkassai.learnit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ProfileFragment extends Fragment {



    EditText profileEmail, profileUsername, profilePassword;
    Button btnEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

        profileEmail = v.findViewById(R.id.profileEmail);
        profileUsername = v.findViewById(R.id.profileUsername);
        profilePassword = v.findViewById(R.id.profilePassword);
        btnEdit = v.findViewById(R.id.btnEdit);

        profileEmail.setText(ParseUser.getCurrentUser().getEmail());
        profileUsername.setText(ParseUser.getCurrentUser().getUsername());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileEmail.getText().toString().matches("") || profileUsername.getText().toString().matches("") || profilePassword.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else{

                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String userID = currentUser.getObjectId();

                    final ParseQuery<ParseUser> query = ParseUser.getQuery();

                    query.getInBackground(userID, new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser object, ParseException e) {
                            if(e == null){
                                object.put("email", profileEmail.getText().toString());
                                object.put("username", profileUsername.getText().toString());
                                object.put("password", profilePassword.getText().toString());

                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e == null){
                                            Toast.makeText(getContext(), "Updated successful", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getContext(), "Updated failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                e.printStackTrace();
                            }

                        }
                    });

                }
            }
        });

        return v;
    }


}
