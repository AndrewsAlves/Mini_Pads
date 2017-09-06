package com.music.chillin.launchpad;

/**
 * Created by Andrews on 7/10/2017.
 */

import android.app.Application;

public class MiniPads extends Application {

    private static MiniPads mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MiniPads getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
