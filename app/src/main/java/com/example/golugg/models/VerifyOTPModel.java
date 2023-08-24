package com.example.golugg.models;

public class VerifyOTPModel {
    public String jsonrpc = "2.0";
    public OTPParams params;

    public void setParams(OTPParams params) {
        this.params = params;
    }

    public static class OTPParams{
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setMobile_otp(String mobile_otp) {
            this.mobile_otp = mobile_otp;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }

        public String mobile;
        public String mobile_otp;
        public String firebase_token;
    }
}
