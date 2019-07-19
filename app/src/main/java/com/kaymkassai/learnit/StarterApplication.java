package com.kaymkassai.learnit;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local Datastore
        Parse.enableLocalDatastore(this);

        //Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("6026b41fbed8e56bae94fb883d851affd6fc51d0")
                .clientKey("2b895884e07cbc9c43e049b65ec975c08b184831")
                .server("http://18.234.205.224:80/parse/")
                .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

