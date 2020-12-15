package com.example.boatrsvapp.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.boatrsvapp.R;
import com.example.boatrsvapp.ui.LoginActivity;
import com.example.boatrsvapp.ui.customer.account.AccountActivity;
import com.example.boatrsvapp.ui.customer.products.ProductsActivity;

public class CustomerPanelActivity extends AppCompatActivity {
    private Button buttonAccount,buttonProducts,buttonReservations,buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel);

        buttonAccount = findViewById(R.id.button_account);
        buttonProducts = findViewById(R.id.button_yachts);
        buttonReservations = findViewById(R.id.button_reservations);
        buttonLogout = findViewById(R.id.button_logout);

        initButtonAccountListener();
        initButtonProductsListener();
        initButtonReservationsListener();
        initLogoutButton();
    }

    private void initLogoutButton() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });}

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        getSharedPreferences("myPrefs", Activity.MODE_PRIVATE).edit().remove("token").apply();
        startActivity(intent);
    }


    private void initButtonAccountListener(){
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();
            }
        });}

    private void initButtonProductsListener(){
        buttonProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProducts();
            }
        });}

    private void initButtonReservationsListener(){
        buttonReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReservations();
            }
        });}

    public void goToAccount(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
    public void goToProducts(){
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }
    public void goToReservations(){
        Intent intent = new Intent(this, ReservationsActivity.class);
        startActivity(intent);
    }
}