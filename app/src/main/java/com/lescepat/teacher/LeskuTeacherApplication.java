package com.lescepat.teacher;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.hawk.Hawk;

import com.lescepat.teacher.data.remote.retrofit.RetrofitServiceFactory;
import com.lescepat.teacher.data.remote.retrofit.LeskuAPIService;

public class LeskuTeacherApplication extends Application {
    private static LeskuTeacherApplication sApp;
    public LeskuAPIService mAPIService;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;

    public static LeskuTeacherApplication getInstance ()
    {
        if (sApp == null)
        {
            sApp = new LeskuTeacherApplication();
        }

        return sApp;
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();
        Hawk.init(getApplicationContext()).build();
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, LeskuTeacherApplication.this);
    }

    public boolean isNetworkAvailable ()
    {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }
}
