package com.matloob.secureapp.presenter;

/**
 * Created by Serar Matloob on 11/16/2019.
 */
public interface ComputeResultCallback {
    void onComputeResultReady(int result);

    void onComputeResultFailed(String message);
}
