package com.equinox.reddittop.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String author_name;
    private double creation_date;
    private String title;
    private String image_source;
    private String comments_count;
    private String thumbnail_source;


    protected Post(Parcel in) {
        author_name = in.readString();
        creation_date = in.readDouble();
        title = in.readString();
        image_source = in.readString();
        comments_count = in.readString();
        thumbnail_source = in.readString();
    }

    public Post() {
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCreation_date() {
        long time = System.currentTimeMillis() / 1000 - (long) creation_date;
        if (time / 31556926 > 0) return time / 31556926 + "y";
        if (time / 2629743 > 0) return time / 2629743 + "m";
        if (time / 604800 > 0) return time / 604800 + "w";
        if (time / 86400 > 0) return time / 86400 + "d";
        if (time / 3600 > 0) return time / 3600 + "h";
        if (time / 60 > 0) return time / 60 + "s";
        return null;
    }

    public void setCreation_date(double creation_date) {
        this.creation_date = creation_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getComments_count() {
        return comments_count + " comments";
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getThumbnail_source() {
        return thumbnail_source;
    }

    public void setThumbnail_source(String thumbnail_source) {
        this.thumbnail_source = thumbnail_source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author_name);
        parcel.writeDouble(creation_date);
        parcel.writeString(title);
        parcel.writeString(image_source);
        parcel.writeString(comments_count);
        parcel.writeString(thumbnail_source);
    }
}
