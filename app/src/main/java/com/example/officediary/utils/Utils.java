package com.example.officediary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
//    public static boolean isInternetConnected(Context context) {
//        boolean connected = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){
//            //we are connected to a network
//            connected = true;
//        } else {
//            connected = false;
//        }
//
//        return connected;
//    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
