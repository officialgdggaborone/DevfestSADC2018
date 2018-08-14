package org.gdggaborone.devfestsadc2018.models;

import java.util.ArrayList;

/**
 * Created by dan on 07/10/17.
 */

public class SessionModel {

    private String title, description, language, presentation,
            complexity;
    private ArrayList<Integer> speakers;
    private ArrayList<String> tags;
    private int id;

    public SessionModel() {}

    public SessionModel(String title, String description, String language, String presentation,
                        String complexity, ArrayList<Integer> speakers, ArrayList<String> tags, int id) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.presentation = presentation;
        this.complexity = complexity;
        this.speakers = speakers;
        this.tags = tags;
        this.id = id;
    }

    public ArrayList<Integer> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList<Integer> speakers) {
        this.speakers = speakers;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
