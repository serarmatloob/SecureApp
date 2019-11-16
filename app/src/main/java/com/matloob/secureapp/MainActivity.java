package com.matloob.secureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matloob.secureapp.models.Data;
import com.matloob.secureapp.presenter.ComputeResultCallback;

public class MainActivity extends AppCompatActivity implements ComputeResultCallback {
    // TAG
    private static final String TAG = "SecureApp";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void addNumbers(final int num1, final int num2) {
        progressBar.setVisibility(View.VISIBLE);
        Data.computeResult(this, num1, num2, this);
    }

    // fired on button click
    public void add(View view) {
        EditText num1 = findViewById(R.id.editText);
        EditText num2 = findViewById(R.id.editText2);
        TextView resultText = findViewById(R.id.result_textView);
        resultText.setText("");
        addNumbers(Integer.valueOf(num1.getText().toString()), Integer.valueOf(num2.getText().toString()));
    }

    @Override
    public void onComputeResultReady(int result) {
        Log.i(TAG, "onComputeResultReady" + result);
        progressBar.setVisibility(View.GONE);
        TextView resultText = findViewById(R.id.result_textView);
        resultText.setText(String.valueOf(result));
    }

    @Override
    public void onComputeResultFailed(String message) {
        Log.i(TAG, "onComputeResultFailed" + message);
        progressBar.setVisibility(View.GONE);
        TextView resultText = findViewById(R.id.result_textView);
        resultText.setText(message);
    }
}
