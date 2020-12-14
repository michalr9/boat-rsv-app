package com.example.boatrsvapp.service;

import com.example.boatrsvapp.data.model.SignInResponse;
import com.example.boatrsvapp.data.model.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

 public interface AuthenticationService {
    @POST("auth/login")
    Call<SignInResponse> signin(@Body UserDTO userDTO);
}
