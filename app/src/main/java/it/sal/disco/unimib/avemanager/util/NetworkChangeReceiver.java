package it.sal.disco.unimib.avemanager.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dagger.assisted.AssistedInject;
import it.sal.disco.unimib.avemanager.data.repository.InvitatiRepository;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private final InvitatiRepository invitatiRepository;
    private final NetworkUtil networkUtil;

    @AssistedInject
    public NetworkChangeReceiver(InvitatiRepository invitatiRepository, NetworkUtil networkUtil) {
        this.invitatiRepository = invitatiRepository;
        this.networkUtil = networkUtil;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (networkUtil.isNetworkAvailable()) {
            invitatiRepository.syncPendingChanges();
        }
    }
}
