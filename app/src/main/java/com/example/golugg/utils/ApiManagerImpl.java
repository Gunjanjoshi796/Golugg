package com.example.golugg.utils;

import com.example.golugg.App;
import com.example.golugg.models.LoginOTPResponse;
import com.example.golugg.models.RegistrationResponse;
import com.example.golugg.models.ResendOTPModel;
import com.example.golugg.models.ResendOTPResponse;
import com.example.golugg.models.UserLoginModel;
import com.example.golugg.models.UserRegistrationModel;
import com.example.golugg.models.VerifyOTPModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManagerImpl implements ApiManager{
    @Override
    public void registerUser(UserRegistrationModel model, DataCallback<RegistrationResponse.RegistrationResult> callback) {
        ApiUtilities.getApiInterface().registerUser(model).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().getResult() != null){
                            callback.onSuccess(response.body().getResult());
                        } else if (response.body().getError() != null){
                            callback.onError(response.body().getError().meaning);
                        }
                    } else callback.onError("Something's not right, Try again");
                } else callback.onError("Something's not right, Try again");
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void verifyOTP(VerifyOTPModel model, DataCallback<LoginOTPResponse.LoginOTPResult> callback) {
        ApiUtilities.getApiInterface().verifyOTP(model).enqueue(new Callback<LoginOTPResponse>() {
            @Override
            public void onResponse(Call<LoginOTPResponse> call, Response<LoginOTPResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().error != null){
                            callback.onError(response.body().error.meaning);
                        } else if (response.body().result != null){
                            callback.onSuccess(response.body().result);
                        }
                    } else callback.onError("Something's not right");
                } else callback.onError("Something's not right");
            }

            @Override
            public void onFailure(Call<LoginOTPResponse> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void loginUser(UserLoginModel model, DataCallback<LoginOTPResponse> callback) {
        ApiUtilities.getApiInterface().loginUser(model).enqueue(new Callback<LoginOTPResponse>() {
            @Override
            public void onResponse(Call<LoginOTPResponse> call, Response<LoginOTPResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().error != null){
                            callback.onError(response.body().error.meaning);
                        } else if (response.body().result != null){
                            callback.onSuccess(response.body());
                        }
                    } else callback.onError("Something's went wrong");
                } else callback.onError("Something's went wrong");
            }

            @Override
            public void onFailure(Call<LoginOTPResponse> call, Throwable t) {
                    callback.onError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void resendOTP(ResendOTPModel model, DataCallback<ResendOTPResponse.ResendOTPResult> callback) {
        ApiUtilities.getApiInterface().resendOTP(model).enqueue(new Callback<ResendOTPResponse>() {
            @Override
            public void onResponse(Call<ResendOTPResponse> call, Response<ResendOTPResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().error != null){
                            callback.onError(response.body().error.meaning);
                        } else if (response.body().result != null){
                            callback.onSuccess(response.body().result);
                        } else callback.onError("Something's went wrong");
                    } else callback.onError("Something's went wrong");
                }
            }

            @Override
            public void onFailure(Call<ResendOTPResponse> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });
    }
}
