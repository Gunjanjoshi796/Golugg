package com.example.golugg.signuplogin;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import com.example.golugg.R;
import com.example.golugg.models.RegistrationResponse;
import com.example.golugg.models.UserRegistrationModel;
import com.example.golugg.utils.ApiManager;
import com.example.golugg.utils.DataCallback;
import com.example.golugg.utils.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class SignupFragment extends Fragment {

    ImageView backBtn;
    TextView agreeTV;
    TextView loginTV;
    LinearLayout continueBtn;
    private String agreeText;
    private String loginText;
    SpannableStringBuilder termsSpannable;
    SpannableStringBuilder loginSpannable;
    private int termsStartIndex;
    private int termsEndIndex;
    private int privacyStartIndex;
    private int privacyEndIndex;
    private int loginStartIndex;
    private int loginEndIndex;
    private TextInputEditText firstNameET, lastNameET, emailET, phoneET, pswrdET, cnfrmPswrdET;
    private ApiManager apiManager = App.getApiManager();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        inItWidgets(view);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    Log.i("token", "onComplete: " + task.getResult());
                }
            }
        });

        backBtn.setOnClickListener(view1 -> requireActivity().onBackPressed());

        ClickableSpan loginSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ((SignupLoginActivity) requireActivity()).fragmentManagerHelper.replace(new LoginFragment(), true);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.red));
            }
        };

        loginSpannable.setSpan(loginSpan, loginStartIndex, loginEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Create custom ClickableSpans to remove underline and handle clicks
        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("Terms of Use clicked");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // Remove underline and set custom text color
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.red));
            }
        };

        termsSpannable.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("Privacy Policy clicked");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // Remove underline and set custom text color
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.red));
            }
        };
        // Change the color of "Terms of Use" to red
        termsSpannable.setSpan(privacyClickableSpan, privacyStartIndex, privacyEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        agreeTV.setText(termsSpannable);
        loginTV.setText(loginSpannable);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFieldsFilled()){
                    if (!pswrdET.getText().toString().equals(cnfrmPswrdET.getText().toString())){
                        showToast("Password and Confirm Password should be same");
                        return;
                    }
                    UserRegistrationModel model = new UserRegistrationModel();
                    UserRegistrationModel.UserParams params = new UserRegistrationModel.UserParams();
                    params.setFirst_name(firstNameET.getText().toString());
                    params.setLast_name(lastNameET.getText().toString());
                    if (!emailET.getText().toString().trim().equals("")){
                        params.setEmail(emailET.getText().toString());
                    }
                    params.setPhone(phoneET.getText().toString());
                    params.setPassword(pswrdET.getText().toString());
                    params.setPassword_confirmation(cnfrmPswrdET.getText().toString());

                    model.setParams(params);
                    Dialogs.showProgressDialog(getContext());
                    apiManager.registerUser(model, new DataCallback<RegistrationResponse.RegistrationResult>() {
                        @Override
                        public void onSuccess(RegistrationResponse.RegistrationResult registrationResult) {
                            Dialogs.dismissProgressDialog();
                            ((SignupLoginActivity) requireActivity()).fragmentManagerHelper.replace(new Otpfragment(new Gson().toJson(registrationResult)), true);
                            Log.i("registrationRes", "onSuccess: " + new Gson().toJson(registrationResult));
                        }

                        @Override
                        public void onError(String serverError) {
                            Dialogs.dismissProgressDialog();
                            showToast(serverError);
                            Log.i("registrationRes", "onSuccess: " + serverError);
                        }
                    });
                }

            }
        });

        return view;

    }

    private boolean allFieldsFilled() {
        if (firstNameET.getText().toString().trim().equals("")){
            showToast("Enter your name first");
            return false;
        }
        if (lastNameET.getText().toString().trim().equals("")){
            showToast("Enter your full name first");
            return false;
        }
        if (phoneET.getText().toString().trim().equals("")){
            showToast("Enter phone number first");
            return false;
        }
        if (pswrdET.getText().toString().trim().equals("")){
            showToast("Enter password first");
            return false;
        }
        if (cnfrmPswrdET.getText().toString().equals("")){
            showToast("Confirm your password first");
            return false;
        }
        return true;
    }

    private void showToast(String termsOfUseClicked) {
        Toast.makeText(getContext(), termsOfUseClicked, Toast.LENGTH_SHORT).show();
    }

    private void inItWidgets(View view) {
        firstNameET = view.findViewById(R.id.first_name_et);
        lastNameET = view.findViewById(R.id.last_name_et);
        emailET = view.findViewById(R.id.email_et);
        phoneET = view.findViewById(R.id.ph_no_et);
        pswrdET = view.findViewById(R.id.pswrd_et);
        cnfrmPswrdET = view.findViewById(R.id.cnfrm_pswrd_et);

        backBtn = view.findViewById(R.id.back_btn);
        agreeTV = view.findViewById(R.id.agree_tv);
        loginTV = view.findViewById(R.id.login_tv);
        continueBtn = view.findViewById(R.id.continue_btn);

        agreeText = "By clicking an option below, I agree to the Terms of Use and have read the Privacy Policy";
        loginText = "Already have an account on Golugg? Login";
        loginTV.setMovementMethod(LinkMovementMethod.getInstance());
        agreeTV.setMovementMethod(LinkMovementMethod.getInstance());

        // Make strings spannable
        termsSpannable = new SpannableStringBuilder(agreeText);
        loginSpannable = new SpannableStringBuilder(loginText);

        // Find the start and end indices of "Terms of Use" and "Privacy Policy"
        termsStartIndex = agreeText.indexOf("Terms of Use");
        termsEndIndex = termsStartIndex + "Terms of Use".length();

        privacyStartIndex = agreeText.indexOf("Privacy Policy");
        privacyEndIndex = privacyStartIndex + "Privacy Policy".length();

        loginStartIndex = loginText.indexOf("Login");
        loginEndIndex = loginStartIndex + "Login".length();
    }
}
