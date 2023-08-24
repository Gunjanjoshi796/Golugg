package com.example.golugg.utils;

import com.example.golugg.models.LoginOTPResponse;
import com.example.golugg.models.RegistrationResponse;
import com.example.golugg.models.ResendOTPModel;
import com.example.golugg.models.ResendOTPResponse;
import com.example.golugg.models.UserLoginModel;
import com.example.golugg.models.UserRegistrationModel;
import com.example.golugg.models.VerifyOTPModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(StringHelper.REGISTER)
    Call<RegistrationResponse> registerUser(@Body UserRegistrationModel model);

    @POST(StringHelper.VERIFY_OTP_LOGIN)
    Call<LoginOTPResponse> verifyOTP(@Body VerifyOTPModel model);

    @POST(StringHelper.VERIFY_OTP_LOGIN)
    Call<LoginOTPResponse> loginUser(@Body UserLoginModel model);

    @POST(StringHelper.RESEND_OTP)
    Call<ResendOTPResponse> resendOTP(@Body ResendOTPModel model);
}
