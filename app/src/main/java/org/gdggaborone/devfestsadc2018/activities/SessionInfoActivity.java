package org.gdggaborone.devfestsadc2018.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.gdggaborone.devfestsadc2018.Constants;
import org.gdggaborone.devfestsadc2018.R;
import org.gdggaborone.devfestsadc2018.models.ScheduleModel;
import org.gdggaborone.devfestsadc2018.models.SessionModel;
import org.gdggaborone.devfestsadc2018.models.SpeakerModel;

public class SessionInfoActivity extends AppCompatActivity {

    private RelativeLayout bottomBg;
    private TextView speakerNameTextView, titleTextView, languageAndComplexityTextView,
            tagsTextView, presentationAvailableTextView, locationTextView, descriptionTextView, schedules_times;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_info);

        String id = getIntent().getStringExtra("id");
        String sid = getIntent().getStringExtra("sid");
        String tid = getIntent().getStringExtra("tid");

        loadData(id, sid, tid);

        bottomBg = (RelativeLayout) findViewById(R.id.bottomBg);

        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        schedules_times = (TextView) findViewById(R.id.schedules_times);
        speakerNameTextView = (TextView) findViewById(R.id.speakerNameTextView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        languageAndComplexityTextView = (TextView) findViewById(R.id.languageAndComplexityTextView);
        tagsTextView = (TextView) findViewById(R.id.tagsTextView);
        presentationAvailableTextView = (TextView) findViewById(R.id.presentationAvailableTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

    }

    void loadData(String id, String sid, String tid) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference schedule = firebaseDatabase.getReference(Constants.SCHEDULES).child(id);
        DatabaseReference sessionTimes = firebaseDatabase.getReference(Constants.SCHEDULES_TIMES)
                .child("0").child("timeslots").child(tid);
        DatabaseReference speaker = firebaseDatabase.getReference(Constants.SPEAKERS).child(sid);

        sessionTimes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScheduleModel scheduleModel = dataSnapshot.getValue(ScheduleModel.class);
                schedules_times.setText(String.format("%s - %s", scheduleModel.getStartTime(), scheduleModel.getEndTime()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        speaker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SpeakerModel speakerModel = dataSnapshot.getValue(SpeakerModel.class);

                speakerNameTextView.setText(speakerModel.getName());
                locationTextView.setText(speakerModel.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        schedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SessionModel sessionModel = dataSnapshot.getValue(SessionModel.class);

                String tag = sessionModel.getTags().get(0);
                tagsTextView.setText(tag);
                switch (tag) {
                    case "Android":
                        bottomBg.setBackgroundColor(ContextCompat.getColor(SessionInfoActivity.this, R.color.android));
                        break;
                    case "Cloud":
                        bottomBg.setBackgroundColor(ContextCompat.getColor(SessionInfoActivity.this, R.color.cloud));
                        break;
                    case "Web":
                        bottomBg.setBackgroundColor(ContextCompat.getColor(SessionInfoActivity.this, R.color.web));
                        break;
                }

                if (sessionModel.getPresentation()!=null) {
                    presentationAvailableTextView.setVisibility(View.VISIBLE);
                } else {
                    presentationAvailableTextView.setVisibility(View.GONE);
                }

                if (sessionModel.getDescription()!=null) {
                    descriptionTextView.setText(sessionModel.getDescription());
                } else {
                    descriptionTextView.setVisibility(View.GONE);
                }
                titleTextView.setText(sessionModel.getTitle());
                languageAndComplexityTextView.setText(sessionModel.getLanguage()+" / "+sessionModel.getComplexity());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void back(View view) {
        finish();
    }
}
