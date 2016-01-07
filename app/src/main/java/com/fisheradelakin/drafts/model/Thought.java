package com.fisheradelakin.drafts.model;

import java.io.Serializable;

/**
 * Created by temidayo on 1/7/16.
 */
public class Thought implements Serializable {

    private long id;
    private String drafts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrafts() {
        return drafts;
    }

    public void setDrafts(String drafts) {
        this.drafts = drafts;
    }
}
