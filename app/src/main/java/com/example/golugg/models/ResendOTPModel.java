package com.example.golugg.models;

public class ResendOTPModel {

    public String jsonrpc = "2.0";
    public ResendParams params;

    public void setParams(ResendParams params) {
        this.params = params;
    }

    public static class ResendParams{
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String mobile;
    }
}
