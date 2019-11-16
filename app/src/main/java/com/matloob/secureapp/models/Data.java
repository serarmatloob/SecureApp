package com.matloob.secureapp.models;

import android.content.Context;

import com.matloob.secureapp.presenter.ComputeResultCallback;
import com.matloob.secureapp.utils.RetrofitHelper;

/**
 * Created by Serar Matloob on 11/16/2019.
 */
public class Data {
    /**
     * This method make request to retrieve computation result of 2 numbers.
     *
     * @param context a {@link Context} of the calling component
     * @param num1 a {@link Integer} of first number
     * @param num2 a {@link Integer} of second number
     * @param computeResultCallback a {@link ComputeResultCallback} of computation results
     */
    public static void computeResult(Context context, int num1, int num2, ComputeResultCallback computeResultCallback) {
        RetrofitHelper.getInstance().makeComputeRequest(context, num1, num2, computeResultCallback);
    }
}
