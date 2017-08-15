package com.fisheradelakin.drafts.model;

import java.io.Serializable;

/**
 * Created by temidayo on 1/7/16.
 */
public class Thought implements Serializable {

    private long id;
    private String drafts;
    private long createdAt;
    private long updatedAt;
    private String title;

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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
