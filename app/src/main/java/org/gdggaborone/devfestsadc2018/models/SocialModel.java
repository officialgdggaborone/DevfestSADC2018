package org.gdggaborone.devfestsadc2018.models;

/**
 * Created by dan on 07/10/17.
 */

public class SocialModel {

    private String name, link, icon;

    public SocialModel() {}

    public SocialModel(String name, String link, String icon) {
        this.name = name;
        this.link = link;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
