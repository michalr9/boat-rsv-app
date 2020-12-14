package com.example.boatrsvapp.service;

import com.example.boatrsvapp.data.model.Customer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CustomerService {
    @GET("account")
    Call<Customer> getCustomer();

}
