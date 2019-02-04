package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

public class PostsAttributes {
    String imageUri;
    String post_id;
    String date;
    String post_title;
    String user_id;
    String post_description;
    String post_location;
    String email_value;


    public String getEmail_value() {
        return email_value;
    }

    public void setEmail_value(String email_value) {
        this.email_value = email_value;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    double lattitude, longitude, current_lat, current_long;

    public double getCurrent_lat() {
        return current_lat;
    }

    public void setCurrent_lat(double current_lat) {
        this.current_lat = current_lat;
    }

    public double getCurrent_long() {
        return current_long;
    }

    public void setCurrent_long(double current_long) {
        this.current_long = current_long;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public PostsAttributes() {
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    //for customer
    public PostsAttributes(String user_id, String post_id, String post_title, String post_description, String post_location, String date, String email_value) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.post_title = post_title;
        this.post_description = post_description;
        this.post_location = post_location;
        this.email_value = email_value;
        this.date = date;
    }

    //for farmers value
    public PostsAttributes(String user_id, String post_id, String title_value, String desc_value, String location, String date, String imageUri, double lattitude, double longitude, String email_value) {
        this.imageUri = imageUri;
        this.post_id = post_id;
        this.date = date;
        this.post_title = title_value;
        this.user_id = user_id;
        this.post_description = desc_value;
        this.post_location = location;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.email_value = email_value;
    }


    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public String getDate() {
        return date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getPost_description() {
        return post_description;
    }

    public String getPost_location() {
        return post_location;
    }
}
