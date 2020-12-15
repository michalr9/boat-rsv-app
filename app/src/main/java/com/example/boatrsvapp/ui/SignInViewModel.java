package com.example.boatrsvapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.boatrsvapp.data.model.SignInResponse;
import com.example.boatrsvapp.data.model.UserDTO;
import com.example.boatrsvapp.service.AuthenticationService;
import com.example.boatrsvapp.service.ServiceGenerator;
import java.util.Calendar;
import java.util.Date;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;

public class SignInViewModel extends ViewModel {
    private AuthenticationService service = new ServiceGenerator().createService(AuthenticationService.class);
    private Context context;

    public SignInViewModel(Context context){
        this.context=context;
    }

    public final Call<SignInResponse> login(String username, String password) {
        UserDTO user = new UserDTO(username, password);
        return this.service.signin(user);
    }

    public final boolean isLogged(String token) {
        boolean isLogged;
        if (!token.equals("")) {
            JWT jwt = new JWT(token);
            Date isActive = jwt.getExpiresAt();

            isLogged = isActive.after(Calendar.getInstance().getTime());
        } else {
            isLogged = false;
        }

        return isLogged;
    }

    public final String getToken() {
        SharedPreferences preferences = this.context.getSharedPreferences("myPrefs", Activity.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        return token;
    }

    public final void saveToken(String accessToken) {
        SharedPreferences preferences = this.context.getSharedPreferences("myPrefs", Activity.MODE_PRIVATE);
        preferences.edit().putString("token", accessToken).apply();
    }

}
