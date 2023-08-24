package com.example.golugg.models;

public class UserRegistrationModel {

    public String jsonrpc = "2.0";
    public UserParams params;

    public void setParams(UserParams params) {
        this.params = params;
    }

    public static class UserParams {
        public String first_name;
        public String last_name;
        public String password;
        public String phone;
        public String email;

        public void setEmail(String email) {
            this.email = email;
        }

        public String password_confirmation;
        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPassword_confirmation(String password_confirmation) {
            this.password_confirmation = password_confirmation;
        }


    }
}
