package org.gdggaborone.devfestsadc2018;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dan on 07/10/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Persist data offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }


}
