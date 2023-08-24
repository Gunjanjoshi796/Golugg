package com.example.golugg.utils;

import com.example.golugg.models.LoginOTPResponse;
import com.example.golugg.models.RegistrationResponse;
import com.example.golugg.models.ResendOTPModel;
import com.example.golugg.models.ResendOTPResponse;
import com.example.golugg.models.UserLoginModel;
import com.example.golugg.models.UserRegistrationModel;
import com.example.golugg.models.VerifyOTPModel;

public interface ApiManager {
    void registerUser(UserRegistrationModel model, DataCallback<RegistrationResponse.RegistrationResult> callback);
    void verifyOTP(VerifyOTPModel model, DataCallback<LoginOTPResponse.LoginOTPResult> callback);
    void loginUser(UserLoginModel model, DataCallback<LoginOTPResponse> callback);
    void resendOTP(ResendOTPModel model, DataCallback<ResendOTPResponse.ResendOTPResult> callback);
}
