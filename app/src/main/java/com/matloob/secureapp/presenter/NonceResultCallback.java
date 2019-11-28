package com.matloob.secureapp.presenter;

/**
 * Created by Serar Matloob on 11/16/2019.
 */
public interface NonceResultCallback {
    void onNonceResultReady(String nonce);

    void onNonceResultFailed(String message);
}
