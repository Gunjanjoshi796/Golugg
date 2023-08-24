package com.example.golugg;

import android.app.Application;
import android.content.Context;

import com.example.golugg.utils.ApiManager;
import com.example.golugg.utils.ApiManagerImpl;

public class App extends Application {

    private static Context context;

    public static ApiManagerImpl apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static ApiManager getApiManager(){
        if (apiManager == null){
            apiManager = new ApiManagerImpl();
        }
        return apiManager;
    }
    public static Context getContext(){
        return context;
    }
}
