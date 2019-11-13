package com.matloob.secureapp;

import com.matloob.secureapp.models.NumBody;
import com.matloob.secureapp.models.ResultModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SecureApiEndpoint {
    // Request method and URL specified in the annotation
    @POST("compute")
    Call<ResultModel> postNumbers(@Body NumBody num);
}