package com.example.golugg.models;

public class ResendOTPResponse {

    public String jsonrpc;
    public ResendOTPError error;
    public ResendOTPResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public ResendOTPError getError() {
        return error;
    }

    public ResendOTPResult getResult() {
        return result;
    }

    public class ResendOTPError{
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
    }

    public class ResendOTPResult{
        public String mobile_otp;
        public String phone;
        public String message_2;

        public String getMobile_otp() {
            return mobile_otp;
        }

        public String getPhone() {
            return phone;
        }

        public String getMessage_2() {
            return message_2;
        }
    }
}
