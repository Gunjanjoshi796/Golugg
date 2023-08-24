package com.example.golugg.signuplogin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.golugg.R;
import com.example.golugg.utils.FragmentManagerHelper;

public class SignupLoginActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentManagerHelper fragmentManagerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        inItWidgets();
        fragmentManagerHelper.replace(new SignupFragment(), false);
    }

    private void inItWidgets() {
        fragmentManagerHelper = new FragmentManagerHelper(fragmentManager);
    }

}
