package org.gdggaborone.devfestsadc2018.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.gdggaborone.devfestsadc2018.Constants;
import org.gdggaborone.devfestsadc2018.R;
import org.gdggaborone.devfestsadc2018.adapters.ScheduleAdapter;
import org.gdggaborone.devfestsadc2018.models.ScheduleModel;
import org.gdggaborone.devfestsadc2018.models.SessionModel;
import org.gdggaborone.devfestsadc2018.models.SpeakerModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private ArrayList<SessionModel> sessionModels;
    private ArrayList<SpeakerModel> speakerModels;
    private ArrayList<ScheduleModel> scheduleModels;
    private ScheduleAdapter scheduleAdapter;
    private ProgressBar progressBar;

    public ScheduleFragment() {
        // Required empty public constructor
        sessionModels = new ArrayList<>();
        speakerModels = new ArrayList<>();
        scheduleModels = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        scheduleAdapter = new ScheduleAdapter(getContext(), sessionModels, speakerModels, scheduleModels);

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scheduleAdapter);

        loadData();

        // Inflate the layout for this fragment
        return view;
    }

    void loadData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference schedules = firebaseDatabase.getReference(Constants.SCHEDULES);
        final DatabaseReference session_times = firebaseDatabase.getReference(Constants.SCHEDULES_TIMES).child("0").child("timeslots");
        final DatabaseReference speakers = firebaseDatabase.getReference(Constants.SPEAKERS);


        session_times.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                speakers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        schedules.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        schedules.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                SessionModel sessionModel = dataSnapshot.getValue(SessionModel.class);
                                sessionModels.add(sessionModel);
                                scheduleAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        session_times.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ScheduleModel scheduleModel = dataSnapshot.getValue(ScheduleModel.class);
                scheduleModels.add(scheduleModel);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        speakers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SpeakerModel speakerModel = dataSnapshot.getValue(SpeakerModel.class);
                speakerModels.add(speakerModel);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
