package com.tutorial.exercise.Handler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aniketrao Rane on 24-03-2017.
 */

public class NetworkHandler
{

    private static NetworkHandler sInstance;
    private Context context;
    public NetworkHandler(Context applicationContext) {
        context = applicationContext;
    }

    public static synchronized NetworkHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NetworkHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
