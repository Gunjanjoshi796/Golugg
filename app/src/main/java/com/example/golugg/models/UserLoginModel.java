package com.example.golugg.models;

public class UserLoginModel {

    public String jsonrpc = "2.0";
    public UserParams params;

    public void setParams(UserParams params) {
        this.params = params;
    }

    public static class UserParams{
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }

        public String mobile;
        public String password;
        public String firebase_token;
    }
}
