package com.example.bloodpocket;

public class User {
    private String email, password, fullName, phone, profession, location, twitter, profileImg;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String fullName, String phone, String profession, String location) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.profession = profession;
        this.location = location;
    }

    public String getProfileImg() { return profileImg; }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfession() {
        return profession;
    }

    public String getLocation() {
        return location;
    }
}
