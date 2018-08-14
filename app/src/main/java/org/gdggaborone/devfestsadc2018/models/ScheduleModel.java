package org.gdggaborone.devfestsadc2018.models;

/**
 * Created by dan on 08/10/17.
 */

public class ScheduleModel {

    private String startTime, endTime;

    public ScheduleModel() {
    }

    public ScheduleModel(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
