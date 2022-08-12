package com.equinox.reddittop.models;


public class Post {
    private String author_name;
    private double creation_date;
    private String image_source;
    private String comments_count;

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public double getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(double creation_date) {
        this.creation_date = creation_date;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }
}
