package it.sal.disco.unimib.avemanager.util;

import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface NetworkChangeReceiverFactory {
    NetworkChangeReceiver create();
}

