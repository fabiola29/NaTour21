package com.example.natour2022.model;

public class User {

    private String userId;
    private String nickName;
    private String email;
    private String password;
    private String dataOfBirth;
    private String gender;


    public User(String userId, String nickname, String email, String password, String dataOfBirth, String gender) {
        this.userId = userId;
        this.nickName = nickname;
        this.email = email;
        this.password = password;
        this.dataOfBirth = dataOfBirth;
        this.gender = gender;
    }

    public User() {
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(String dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}





