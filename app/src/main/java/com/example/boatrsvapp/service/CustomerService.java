package com.example.boatrsvapp.service;

import com.example.boatrsvapp.data.model.Customer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface CustomerService {
    @GET("account")
    Call<Customer> getCustomer();

    @PUT("account")
    Call<Customer> updateCustomer(@Body Customer customer);
}
