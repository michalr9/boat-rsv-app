package com.example.boatrsvapp.ui.customer.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.boatrsvapp.R;
import com.example.boatrsvapp.data.model.ProductPage;

public class ProductsActivity extends AppCompatActivity {

    private final String TAG = "ProductsActivity";
    ProductsViewModel productsViewModel;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    Boolean loading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productsViewModel  = new ProductsViewModel();
        recyclerView = findViewById(R.id.rvProducts);
        initRecyclerView(recyclerView);

    }

    private void initRecyclerView(RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(productsViewModel.productList);
        recyclerView.setAdapter(productAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!productsViewModel.currentPage.getLast() && !loading){
                    recyclerView.setPadding(0,0,0,100);
//                    showToastInIntentService("Pobieram dane");
                    getProducts();
                }
            }
        });
        getProducts();
    }

    private void getProducts(){
        loading=true;
        int nextPage=0;
        if(!productsViewModel.productList.isEmpty()){
            nextPage = productsViewModel.currentPage.getNumber()+1;
        }

        productsViewModel.getProducts(nextPage).enqueue(new Callback<ProductPage>() {
            @Override
            public void onResponse(Call<ProductPage> call, Response<ProductPage> response) {
                onSuccessResponse(response);
            }

            @Override
            public void onFailure(Call<ProductPage> call, Throwable t) {
                onProductPageFailure(t);
            }
        });
    }

    private void onProductPageFailure(Throwable t) {
        loading=false;
        Log.e(TAG,t.getMessage());
        Toast.makeText(this, getString(R.string.data_load_err), Toast.LENGTH_LONG).show();

    }

    private void onSuccessResponse(Response<ProductPage> response) {
        productsViewModel.currentPage = response.body();
        productsViewModel.productList.addAll(productsViewModel.currentPage.getContent());
        productAdapter.notifyDataSetChanged();
        loading=false;
        recyclerView.setPadding(0,0,0,0);
        Toast.makeText(this, getString(R.string.data_load), Toast.LENGTH_LONG).show();

    }

    public void showToastInIntentService(final String text) {
        final Context MyContext = this;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast1 = Toast.makeText(MyContext, text, Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }
}