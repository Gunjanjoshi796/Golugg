package com.example.golugg.models;

public class RegistrationResponse {

    public String jsonrpc;
    public RegisterError error;
    public RegistrationResult result;

    public RegistrationResult getResult() {
        return result;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public RegisterError getError() {
        return error;
    }

    public class RegisterError{
        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getMeaning() {
            return meaning;
        }

        public String code;
        public String message;
        public String meaning;
    }

    public class RegistrationResult{
        public String phone;
        public int mobile_otp;
        public String message_2;
        public String mail_message;
        public String email;

        public String getMail_message() {
            return mail_message;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public int getMobile_otp() {
            return mobile_otp;
        }

        public String getMessage_2() {
            return message_2;
        }

    }
}
