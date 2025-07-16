package it.sal.disco.unimib.avemanager.util;

// DataCallback.java
public interface DataCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable t);
}
