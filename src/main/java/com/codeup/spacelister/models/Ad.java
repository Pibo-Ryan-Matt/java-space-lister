package com.codeup.spacelister.models;

public class Ad {
    private long id;
    private long userId;
    private String title;
    private String description;
    private String picture;
    private String category;

    public Ad(long id, long userId, String title, String description, String picture, String category) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.category = category;
    }

    public Ad(long userId, String title, String description, String picture, String category) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}