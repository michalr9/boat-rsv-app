package com.example.boatrsvapp.ui.customer.products;

import com.example.boatrsvapp.data.model.Product;
import com.example.boatrsvapp.data.model.ProductPage;
import com.example.boatrsvapp.service.ProductService;
import com.example.boatrsvapp.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;

public class ProductsViewModel extends ViewModel {
    List<Product> productList = new ArrayList<Product>();
    ProductPage currentPage;
    ProductService productService = new ServiceGenerator().createService(ProductService.class);

    public final Call<ProductPage> getProducts(int currentPage){
        return productService.getProducts(currentPage, 10);

    }
}
