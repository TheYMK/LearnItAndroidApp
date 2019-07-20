package com.kaymkassai.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    //Views
    EditText txtUsername, txtPassword, txtEmail;
    TextView logo, txtSwitch;
    Button btnLogin;
    RelativeLayout background;

    //Boolean to check if in login mode or signup mode
    Boolean loginModeActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //Check if a user is already logged in
        if(ParseUser.getCurrentUser() != null){
            showMainMenu();
        }

        // Setting my views
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtSwitch = findViewById(R.id.txtSwitch);
        txtEmail = findViewById(R.id.txtEmail);
        logo = findViewById(R.id.logo);
        btnLogin = findViewById(R.id.btnLogin);
        background = findViewById(R.id.background);


        background.setOnClickListener(this);
        logo.setOnClickListener(this);
        txtSwitch.setOnClickListener(this);
        txtPassword.setOnKeyListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


    //Show main menu activity
    public void showMainMenu(){
        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);

        if(ParseUser.getCurrentUser().getUsername() != null) {
            i.putExtra("username", ParseUser.getCurrentUser().getUsername());
            i.putExtra("email", ParseUser.getCurrentUser().getEmail());
        }

        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.txtSwitch) {

            if (loginModeActive) {
                loginModeActive = false;
                btnLogin.setText("Signup");
                txtSwitch.setText("Or, Login?");
                txtEmail.setVisibility(View.VISIBLE);
            } else {
                loginModeActive = true;
                btnLogin.setText("Login");
                txtSwitch.setText("Or, Signup?");
                txtEmail.setVisibility(View.INVISIBLE);
            }
        }else if(v.getId() == R.id.logo || v.getId() == R.id.background){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            loginOrSignup(v);
        }

        return false;
    }

    //Login Or SignUp method
    public void loginOrSignup(View view){

        if(txtUsername.getText().toString().matches("") || txtPassword.getText().toString().matches("")){

           // Added code to check if the email EditText is visible or not
            if(txtEmail.getVisibility() == View.VISIBLE){
                //If visible check if it's empty or not
                if(txtEmail.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Username, Password and email required", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Username and Password required", Toast.LENGTH_SHORT).show();
            }

        }else{
            if(loginModeActive){
                ParseUser.logInInBackground(txtUsername.getText().toString(), txtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null){
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            showMainMenu();
                        }else{
                            Toast.makeText(getApplicationContext(), "Login failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                ParseUser user = new ParseUser();
                user.setUsername(txtUsername.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                user.setEmail(txtEmail.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                            showMainMenu();
                        }else{
                            Toast.makeText(getApplicationContext(),"Signup failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }



}
