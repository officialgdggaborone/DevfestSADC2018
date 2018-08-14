package org.gdggaborone.devfestsadc2018.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.gdggaborone.devfestsadc2018.R;
import org.gdggaborone.devfestsadc2018.activities.SessionInfoActivity;
import org.gdggaborone.devfestsadc2018.models.ScheduleModel;
import org.gdggaborone.devfestsadc2018.models.SessionModel;
import org.gdggaborone.devfestsadc2018.models.SpeakerModel;

import java.util.ArrayList;

/**
 * Created by dan on 07/10/17.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SessionModel> sessionModels;
    private ArrayList<SpeakerModel> speakerModels;
    private ArrayList<ScheduleModel> scheduleModels;

    public ScheduleAdapter(Context context, ArrayList<SessionModel> sessionModels, ArrayList<SpeakerModel> speakers, ArrayList<ScheduleModel> scheduleModels) {
        mContext = context;
        this.sessionModels = sessionModels;
        this.speakerModels = speakers;
        this.scheduleModels = scheduleModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.session_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final SessionModel sessionModel = sessionModels.get(position);

        final int speakerId = sessionModel.getSpeakers().get(0);

        ScheduleModel scheduleModel = scheduleModels.get(position);
        SpeakerModel speakerModel = speakerModels.get(speakerId-1);

        String tag = sessionModel.getTags().get(0);
        holder.tagsTextView.setText(tag);
        switch (tag) {
            case "Android":
                holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.android));
                break;
            case "Cloud":
                holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.cloud));
                break;
            case "Web":
                holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.web));
                break;
        }

        if (sessionModel.getPresentation()!=null) {
            holder.presentationAvailableTextView.setVisibility(View.VISIBLE);
        } else {
            holder.presentationAvailableTextView.setVisibility(View.GONE);
        }

        if (sessionModel.getDescription()!=null) {
            holder.descriptionTextView.setText(sessionModel.getDescription());
        } else {
            holder.descriptionTextView.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SessionInfoActivity.class);
                intent.putExtra("id", String.valueOf(sessionModel.getId()));
                intent.putExtra("sid", String.valueOf(speakerId));
                intent.putExtra("tid", String.valueOf(position));
                mContext.startActivity(intent);
            }
        });

        holder.schedulesTimes.setText(String.format("%s - %s", scheduleModel.getStartTime(), scheduleModel.getEndTime()));
        holder.speakerNameTextView.setText(speakerModel.getName());
        holder.locationTextView.setText(speakerModel.getCountry());
        holder.titleTextView.setText(sessionModel.getTitle());
        holder.languageAndComplexityTextView.setText(String.format("%s / %s", sessionModel.getLanguage(), sessionModel.getComplexity()));

    }

    @Override
    public int getItemCount() {
        return sessionModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        RelativeLayout bottomBg;
        TextView speakerNameTextView, titleTextView, languageAndComplexityTextView,
                tagsTextView, presentationAvailableTextView, locationTextView, descriptionTextView, schedulesTimes;

        ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);

            bottomBg = itemView.findViewById(R.id.bottomBg);

            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            schedulesTimes = itemView.findViewById(R.id.schedules_times);
            speakerNameTextView = itemView.findViewById(R.id.speakerNameTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            languageAndComplexityTextView = itemView.findViewById(R.id.languageAndComplexityTextView);
            tagsTextView = itemView.findViewById(R.id.tagsTextView);
            presentationAvailableTextView = itemView.findViewById(R.id.presentationAvailableTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);

        }
    }

}
