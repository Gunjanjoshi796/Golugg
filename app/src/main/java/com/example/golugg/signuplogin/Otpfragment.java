package com.example.golugg.signuplogin;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaos.view.PinView;
import com.example.golugg.App;
import com.example.golugg.MainActivity;
import com.example.golugg.R;
import com.example.golugg.models.LoginOTPResponse;
import com.example.golugg.models.RegistrationResponse;
import com.example.golugg.models.ResendOTPModel;
import com.example.golugg.models.ResendOTPResponse;
import com.example.golugg.models.VerifyOTPModel;
import com.example.golugg.utils.ApiManager;
import com.example.golugg.utils.DataCallback;
import com.example.golugg.utils.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.Objects;

public class Otpfragment extends Fragment {

    ImageView backBtn;
    PinView otpPinView;
    LinearLayout continueSignupBtn;
    private TextView instructTV;
    private ApiManager apiManager = App.getApiManager();
    private String FIREBASE_TOKEN = "";
    private TextView resendCodeBTN;
    private RegistrationResponse.RegistrationResult userResponse;

    public Otpfragment(String userRes) {
        userResponse = new Gson().fromJson(userRes, RegistrationResponse.RegistrationResult.class);
    }

    @SuppressLint("StringFormatMatches")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        inItWidgets(view);
        getToken();
        if (userResponse != null){
            updateUI(String.format(getContext().getString(R.string.otp_string), userResponse.phone, userResponse.mobile_otp));
        }

        backBtn.setOnClickListener(view1 -> requireActivity().onBackPressed());
        otpPinView.requestFocus();

        otpPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 6){
                    verifyOTP();
                    hideKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        continueSignupBtn.setOnClickListener(view12 -> {
            if (Objects.requireNonNull(otpPinView.getText()).toString().length() < 6){
                Toast.makeText(getContext(), "OTP must be 6 digit long", Toast.LENGTH_SHORT).show();
                return;
            }
            verifyOTP();
        });

        resendCodeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOTPModel model = new ResendOTPModel();
                ResendOTPModel.ResendParams params = new ResendOTPModel.ResendParams();
                params.setMobile(userResponse.phone);
                model.setParams(params);
                Dialogs.showProgressDialog(getContext());
                apiManager.resendOTP(model, new DataCallback<ResendOTPResponse.ResendOTPResult>() {
                    @Override
                    public void onSuccess(ResendOTPResponse.ResendOTPResult resendOTPResult) {
                        Dialogs.dismissProgressDialog();
                        updateUI(String.format(getContext().getString(R.string.otp_string), userResponse.phone, resendOTPResult.mobile_otp));
                    }

                    @Override
                    public void onError(String serverError) {
                        Dialogs.dismissProgressDialog();
                        showToast(serverError);
                    }
                });
            }
        });

        return view;
    }

    private void updateUI(String string) {
        instructTV.setText(string);
    }

    private void verifyOTP() {
        Dialogs.showProgressDialog(getContext());
        if (FIREBASE_TOKEN.equals("")){
            getToken();
        }
        VerifyOTPModel model = new VerifyOTPModel();
        VerifyOTPModel.OTPParams params = new VerifyOTPModel.OTPParams();
        params.setMobile(userResponse.phone);
        params.setMobile_otp(String.valueOf(userResponse.mobile_otp));
        params.setFirebase_token(FIREBASE_TOKEN);
        model.setParams(params);
        apiManager.verifyOTP(model, new DataCallback<LoginOTPResponse.LoginOTPResult>() {
            @Override
            public void onSuccess(LoginOTPResponse.LoginOTPResult loginOTPResult) {
                Dialogs.dismissProgressDialog();
                showToast(loginOTPResult.meaning);
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(requireActivity(), MainActivity.class));
                    requireActivity().finish();
                }, 2000);
            }

            @Override
            public void onError(String serverError) {
                Dialogs.dismissProgressDialog();
                showToast(serverError);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FIREBASE_TOKEN = task.getResult();
            }
        });
    }

    private void inItWidgets(View view) {
        resendCodeBTN = view.findViewById(R.id.resend_code_btn);
        instructTV = view.findViewById(R.id.instruct_tv);
        backBtn = view.findViewById(R.id.back_btn);
        otpPinView = view.findViewById(R.id.otp_pin_view);
        continueSignupBtn = view.findViewById(R.id.continue_btn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
