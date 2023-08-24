package com.example.golugg.signuplogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.golugg.App;
import com.example.golugg.MainActivity;
import com.example.golugg.R;
import com.example.golugg.models.LoginOTPResponse;
import com.example.golugg.models.UserLoginModel;
import com.example.golugg.utils.ApiManager;
import com.example.golugg.utils.DataCallback;
import com.example.golugg.utils.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    ImageView backBtn;
    TextView signupTV;
    private String signupText;
    SpannableStringBuilder signupSpannable;
    private int signupStartIndex;
    private int signupEndIndex;
    private TextInputEditText phoneET, pswrdET;
    LinearLayout loginBTN;
    private String FIREBASE_TOKEN = "";
    private ApiManager apiManager = App.getApiManager();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        inItWidgets(view);
        getToken();
        backBtn.setOnClickListener(view1 -> requireActivity().onBackPressed());

        ClickableSpan signupSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                requireActivity().onBackPressed();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.red));
            }
        };

        signupSpannable.setSpan(signupSpan, signupStartIndex, signupEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupTV.setText(signupSpannable);

        loginBTN.setOnClickListener(view12 -> {
            if (phoneET.getText().toString().trim().equals("")){
                showToast("Please enter your number");
                return;
            }
            if (pswrdET.getText().toString().trim().equals("")){
                showToast("Please enter your password");
                return;
            }
            if (FIREBASE_TOKEN.equals("")){
                getToken();
            }
            UserLoginModel model = new UserLoginModel();
            UserLoginModel.UserParams params = new UserLoginModel.UserParams();
            params.setPassword(pswrdET.getText().toString());
            params.setMobile(phoneET.getText().toString());
            params.setFirebase_token(FIREBASE_TOKEN);
            model.setParams(params);

            Dialogs.showProgressDialog(getContext());
            apiManager.loginUser(model, new DataCallback<LoginOTPResponse>() {
                @Override
                public void onSuccess(LoginOTPResponse loginOTPResponse) {
                    Dialogs.dismissProgressDialog();
                    showToast(loginOTPResponse.result.meaning);
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(getContext(), MainActivity.class));
                        requireActivity().finish();
                    }, 1500);
                }

                @Override
                public void onError(String serverError) {
                        Dialogs.dismissProgressDialog();
                        showToast(serverError);
                }
            });
        });
        return view;
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
        phoneET = view.findViewById(R.id.ph_no_et);
        pswrdET = view.findViewById(R.id.pswrd_et);
        loginBTN = view.findViewById(R.id.login_btn);

        backBtn = view.findViewById(R.id.back_btn);
        signupTV = view.findViewById(R.id.signup_tv);
        signupTV.setMovementMethod(LinkMovementMethod.getInstance());
        signupText = "Don't have an account on Golugg? Signup";

        signupSpannable = new SpannableStringBuilder(signupText);
        signupStartIndex = signupText.indexOf("Signup");
        signupEndIndex = signupStartIndex + "Signup".length();
    }
}
