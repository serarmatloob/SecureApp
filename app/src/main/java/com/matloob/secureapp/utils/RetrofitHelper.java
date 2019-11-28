package com.matloob.secureapp.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matloob.secureapp.api.SecureApiEndpoint;
import com.matloob.secureapp.models.AndroidIdRequestBody;
import com.matloob.secureapp.models.ComputeRequestBody;
import com.matloob.secureapp.models.NonceModel;
import com.matloob.secureapp.models.ResultModel;
import com.matloob.secureapp.presenter.ComputeResultCallback;
import com.matloob.secureapp.presenter.NonceResultCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.matloob.secureapp.BuildConfig.SECURE_API_URL;

/**
 * Created by Serar Matloob on 11/15/2019.
 */
public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";
    private static RetrofitHelper retrofitHelper = null;
    private SecureApiEndpoint service;

    private RetrofitHelper() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SECURE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(SecureApiEndpoint.class);
    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    public void makeComputeRequest(final Context context, final int num1, final int num2, final ComputeResultCallback computeResultCallback) {
        SafetyNetUtil.getInstance().sendSafetyNetRequest(context, new SafetyNetUtil.SafetyNetCallback() {
            @Override
            public void onDone(final String safetyJwsResult) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fulfilRequestWithSafetyNetResult(context, safetyJwsResult, num1, num2, computeResultCallback);
                    }
                }, 500);

            }

            @Override
            public void onError(String message) {
                computeResultCallback.onComputeResultFailed("Failed to get safetynet results");
            }
        });
    }

    void makeNonceRequest(final String androidId, final NonceResultCallback nonceResultCallback) {
        AndroidIdRequestBody androidIdRequestBody = new AndroidIdRequestBody(androidId);
        Call<NonceModel> repos = service.postNonceRequest(androidIdRequestBody);
        repos.enqueue(new Callback<NonceModel>() {
            @Override
            public void onResponse(Call<NonceModel> call, Response<NonceModel> response) {
                Log.i(TAG, "Response nonce: " + response.body().getNonce());
                if (response.body() != null && nonceResultCallback != null) {
                    if (response.code() == 200) {
                        nonceResultCallback.onNonceResultReady(response.body().getNonce());
                    } else {
                        nonceResultCallback.onNonceResultFailed("Failed to obtain nonce");
                    }
                }
            }

            @Override
            public void onFailure(Call<NonceModel> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                nonceResultCallback.onNonceResultFailed(t.getMessage());
            }
        });
    }

    private void fulfilRequestWithSafetyNetResult(Context context, String safetyJwsResult, int num1, int num2, final ComputeResultCallback computeResultCallback) {
        ComputeRequestBody computeRequestBody = new ComputeRequestBody(safetyJwsResult, Util.getGsfAndroidId(context), num1, num2);
        Call<ResultModel> repos = service.postComputeRequest(computeRequestBody);
        repos.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                Log.i(TAG, "Response: " + response.body());
                if (response.body() != null && computeResultCallback != null) {
                    if (response.code() == 200) {
                        if (!response.body().isError()) {
                            computeResultCallback.onComputeResultReady(response.body().getResult());
                        } else {
                            computeResultCallback.onComputeResultFailed(response.body().getMessage());
                        }
                    } else {
                        computeResultCallback.onComputeResultFailed("Error while retrieving results");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                computeResultCallback.onComputeResultFailed(t.getMessage());
            }
        });
    }
}
