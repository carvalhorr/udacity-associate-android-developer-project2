package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by carvalhorr on 2/11/17.
 */

public class MovieVideo {
    @SerializedName("key")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("site")
    private String site;

    public String getKey() {
        return id;
    }

    public void setKey(String key) {
        this.id = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
