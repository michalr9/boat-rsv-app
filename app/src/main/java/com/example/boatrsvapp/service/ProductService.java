package com.example.boatrsvapp.service;

import com.example.boatrsvapp.data.model.Product;
import com.example.boatrsvapp.data.model.ProductPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    // product page zwraca
    @GET("gallery")
    Call<ProductPage> getProducts(@Query("page") int pageNumber, @Query("size")int pageSize);

    @GET("gallery/{id}/detail")
    Call<Product> getProductById(@Path("id")int id);

}
