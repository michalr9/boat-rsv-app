package com.example.boatrsvapp.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.boatrsvapp.R;
import com.example.boatrsvapp.data.model.Authority;
import com.example.boatrsvapp.data.model.SignInResponse;
import com.example.boatrsvapp.ui.admin.AdminPanelActivity;
import com.example.boatrsvapp.ui.customer.CustomerPanelActivity;

import java.util.Collection;
import java.util.LinkedList;


public class LoginActivity extends AppCompatActivity implements Callback<SignInResponse> {

    private SignInViewModel signinViewModel = new SignInViewModel(this);
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         usernameEditText = findViewById(R.id.username);
          passwordEditText = findViewById(R.id.password);
          loginButton = findViewById(R.id.login);
          loadingProgressBar = findViewById(R.id.loading);


// LOGOWANIE
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });

        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {
                
            }

            @Override
            public void onFinish() {
                String token = signinViewModel.getToken();
                if(!signinViewModel.isLogged(token)){
                    Toast.makeText(LoginActivity.this, "Nie zalogowano!", Toast.LENGTH_SHORT).show();
                }else{
                    JWT jwt = new JWT(token);
                    Collection<Authority> authorities = new LinkedList<>();
                    Authority authority = new Authority().setAuthority(jwt.getClaim("auth").asString());
                    authorities.add(authority);
                    SignInResponse signInResponse =  new SignInResponse().setAccessToken(jwt.getSignature()).setAuthorities(authorities).setTokenType("Bearer");
                    onLoginSuccess(signInResponse);
                }
            }
        }.start();

    }

    private void login(){
        loadingProgressBar.setVisibility(View.VISIBLE);
        signinViewModel.login(usernameEditText.getText().toString(),
                passwordEditText.getText().toString()).enqueue(this);
    }

    private void onLoginSuccess(SignInResponse signInResponse){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(signInResponse.getAuthorities().parallelStream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))){
                Intent customerPanel = new Intent(this, CustomerPanelActivity.class).putExtra("Token",signInResponse.getAccessToken());
                startActivity(customerPanel);
            }else if(signInResponse.getAuthorities().parallelStream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))){
                Intent adminPanel = new Intent(this, AdminPanelActivity.class).putExtra("Token",signInResponse.getAccessToken());
                startActivity(adminPanel);
            }
        }else{
            Toast.makeText(this, getString(R.string.version_err), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
        if (response.isSuccessful()) {
            SignInResponse data = response.body();
            signinViewModel.saveToken(response.body().getAccessToken());
            onLoginSuccess(data);
        } else {
            onLoginFailed();
        }
    }

    private void onLoginFailed() {
        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Call<SignInResponse> call, Throwable t) {
        Toast.makeText(this, getString(R.string.login_failed_err), Toast.LENGTH_LONG).show();
    }
}