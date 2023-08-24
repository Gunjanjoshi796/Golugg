package com.example.golugg.models;

public class LoginOTPResponse {

    public String jsonrpc;
    public RegistrationResponse.RegisterError error;

    public LoginOTPResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public RegistrationResponse.RegisterError getError() {
        return error;
    }

    public LoginOTPResult getResult() {
        return result;
    }

    public class LoginOTPResult{
        public String code;
        public String message;
        public String meaning;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getMeaning() {
            return meaning;
        }

        public UserData getUserdata() {
            return userdata;
        }

        public UserData userdata;
    }

    public class UserData{
        public int id;
        public String first_name;
        public String last_name;
        public String mobile;

        public int getId() {
            return id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getMobile() {
            return mobile;
        }
    }
}
