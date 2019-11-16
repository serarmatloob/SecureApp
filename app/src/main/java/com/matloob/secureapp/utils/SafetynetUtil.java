package com.matloob.secureapp.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.matloob.secureapp.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Serar Matloob on 11/16/2019.
 */
class SafetynetUtil {
    //TAG
    private static final String TAG = "SafetynetUtil";

    private static SafetynetUtil safetynetUtil;
    private final Random mRandom = new SecureRandom();
    private SafetyNetCallback safetyNetCallback;

    private OnSuccessListener<SafetyNetApi.AttestationResponse> mSuccessListener =
            new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
                @Override
                public void onSuccess(SafetyNetApi.AttestationResponse attestationResponse) {
                    Log.d(TAG, "Success! SafetyNet result:\n" + attestationResponse.getJwsResult() + "\n");
                    safetyNetCallback.onDone(attestationResponse.getJwsResult());
                }
            };
    private OnFailureListener mFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                Log.d(TAG, "Error: " +
                        CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()) + ": " +
                        apiException.getStatusMessage());
            } else {
                Log.d(TAG, "ERROR! " + e.getMessage());
                safetyNetCallback.onError(e.getMessage());
            }
        }
    };

    static SafetynetUtil getInstance() {
        if (safetynetUtil == null) {
            safetynetUtil = new SafetynetUtil();
        }
        return safetynetUtil;
    }

    void sendSafetyNetRequest(Context context, SafetyNetCallback safetyNetCallback) {
        this.safetyNetCallback = safetyNetCallback;
        Log.i(TAG, "Sending SafetyNet API request.");

        String nonceData = "Safety Net Sample: " + System.currentTimeMillis();
        byte[] nonce = getRequestNonce(nonceData);

        SafetyNetClient client = SafetyNet.getClient(context);
        Task<SafetyNetApi.AttestationResponse> task = client.attest(nonce, BuildConfig.API_KEY);

        task.addOnSuccessListener(mSuccessListener).addOnFailureListener(mFailureListener);
    }

    private byte[] getRequestNonce(String data) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[24];
        mRandom.nextBytes(bytes);
        try {
            byteStream.write(bytes);
            byteStream.write(data.getBytes());
        } catch (IOException e) {
            return null;
        }

        return byteStream.toByteArray();
    }

    interface SafetyNetCallback {
        void onDone(String safetyJwsResult);

        void onError(String message);
    }
}
