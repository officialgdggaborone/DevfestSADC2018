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
import org.gdggaborone.devfestsadc2018.adapters.SpeakerAdapter;
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
public class SpeakersFragment extends Fragment {

    private ArrayList<SpeakerModel> mList;
    private SpeakerAdapter speakerAdapter;
    private ProgressBar progressBar;

    public SpeakersFragment() {
        // Required empty public constructor
        mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speakers, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        speakerAdapter = new SpeakerAdapter(getContext(), mList);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(speakerAdapter);

        loadData();

        // Inflate the layout for this fragment
        return view;
    }

    void loadData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference speakers = firebaseDatabase.getReference(Constants.SPEAKERS);
        speakers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
        speakers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SpeakerModel speakerModel = dataSnapshot.getValue(SpeakerModel.class);
                mList.add(speakerModel);
                speakerAdapter.notifyDataSetChanged();
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
