package it.sal.disco.unimib.avemanager;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import it.sal.disco.unimib.avemanager.util.NetworkChangeReceiver;
import it.sal.disco.unimib.avemanager.util.NetworkChangeReceiverFactory;

@HiltAndroidApp
public class AveManagerApplication extends Application {
    @Inject
    NetworkChangeReceiverFactory networkChangeReceiverFactory;

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        networkChangeReceiver = networkChangeReceiverFactory.create();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

}