package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

public class FarmerInfo {
    String firstname;
    String lastname;
    String username;
    String pan;
    String phoneno;
    String location;
    String aboutfarm;
    String farm_name;
    String email_value;

    public String getEmail_value() {
        return email_value;
    }

    public void setEmail_value(String email_value) {
        this.email_value = email_value;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    FarmerInfo() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAboutfarm() {
        return aboutfarm;
    }

    public void setAboutfarm(String aboutfarm) {
        this.aboutfarm = aboutfarm;
    }

    public FarmerInfo(String firstname, String lastname, String username, String pan, String phoneno, String location, String aboutfarm,String farm_name,String email_value) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.pan = pan;
        this.phoneno = phoneno;
        this.location = location;
        this.aboutfarm = aboutfarm;
        this.farm_name=farm_name;
        this.email_value=email_value;
    }
}
