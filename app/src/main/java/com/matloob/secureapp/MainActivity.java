package com.matloob.secureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matloob.secureapp.models.NumBody;
import com.matloob.secureapp.models.ResultModel;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.matloob.secureapp.BuildConfig.SECURE_API_URL;

public class MainActivity extends AppCompatActivity {
    // TAG
    private static final String TAG = "SecureApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @ParametersAreNonnullByDefault
    private void addNumbers(int num1, int num2) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SECURE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SecureApiEndpoint apiService = retrofit.create(SecureApiEndpoint.class);

        NumBody numBody = new NumBody(num1, num2);
        Call<ResultModel> call = apiService.postNumbers(numBody);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, final Response<ResultModel> response) {
                if (response.body() != null) {
                    Log.i(TAG, "onResponse. Result is: " + response.body().getResult());
                    TextView resultText = findViewById(R.id.result_textView);
                    resultText.setText(String.valueOf(response.body().getResult()));
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Log.i(TAG, "onFailure " + t.getMessage());
                TextView resultText = findViewById(R.id.result_textView);
                resultText.setText(t.getMessage());
            }
        });
    }
    // fired on button click
    public void add(View view) {
        EditText num1 = findViewById(R.id.editText);
        EditText num2 = findViewById(R.id.editText2);
        addNumbers(Integer.valueOf(num1.getText().toString()), Integer.valueOf(num2.getText().toString()));
    }
}
