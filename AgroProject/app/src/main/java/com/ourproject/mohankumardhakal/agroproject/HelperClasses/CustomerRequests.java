package com.ourproject.mohankumardhakal.agroproject.HelperClasses;
public class CustomerRequests {
    String name_value;
    String contact_value;
    String qty_value;
    String title;
    String user_id;
    String location;
    String device_token;
    String post_id;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getDevice_token() {

        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CustomerRequests(String name_value, String contact_value, String qty_value, String post_id, String user_id,String location) {
        this.name_value = name_value;
        this.contact_value = contact_value;
        this.qty_value = qty_value;
        this.post_id = post_id;
        this.user_id = user_id;
        this.location=location;
    }

    CustomerRequests() {

    }

    public String getName_value() {
        return name_value;
    }

    public void setName_value(String name_value) {
        this.name_value = name_value;
    }

    public String getContact_value() {
        return contact_value;
    }

    public void setContact_value(String contact_value) {
        this.contact_value = contact_value;
    }

    public String getQty_value() {
        return qty_value;
    }

    public void setQty_value(String qty_value) {
        this.qty_value = qty_value;
    }
}
