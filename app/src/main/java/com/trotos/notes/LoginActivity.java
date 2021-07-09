package com.trotos.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trotos.notes.models.LogIn;
import com.trotos.notes.models.responseModels.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText mail;
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkLogInStatus()) {
            toMainActivity();
        } else {
            setContentView(R.layout.activity_login);

            mail = findViewById(R.id.mail);
            password = findViewById(R.id.password);
            login = findViewById(R.id.login);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logIn();
                }
            });
        }
    }

    private Boolean checkLogInStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        return token !=null;
    }

    private void logIn() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUtils as = retrofit.create(APIUtils.class);

        String passwordText = password.getText().toString();
        String mailText = mail.getText().toString();

        LogIn logIn = new LogIn();
        logIn.setMail(mailText);
        logIn.setPassword(passwordText);
        Call<ResponseLogin> call = as.logIn(logIn);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin responseLogin = response.body();
                    saveToken(responseLogin.getToken());
                    toMainActivity();
                } else {
                    // TODO: check other errors
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void toMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("Token", token);
        prefsEditor.apply();
    }
}