package com.matloob.secureapp.api;

import com.matloob.secureapp.models.ComputeRequestBody;
import com.matloob.secureapp.models.ResultModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SecureApiEndpoint {
    // Request method and URL specified in the annotation
    @POST("compute")
    Call<ResultModel> postComputeRequest(@Body ComputeRequestBody num);
}