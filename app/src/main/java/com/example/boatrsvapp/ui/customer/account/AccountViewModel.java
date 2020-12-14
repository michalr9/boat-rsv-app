package com.example.boatrsvapp.ui.customer.account;

import android.content.Context;

import com.example.boatrsvapp.data.model.Customer;
import com.example.boatrsvapp.service.CustomerService;
import com.example.boatrsvapp.service.ServiceGenerator;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;

public class AccountViewModel extends ViewModel {
    Context context;
    CustomerService customerService = new ServiceGenerator().createServiceWithToken(CustomerService.class,context);

    public AccountViewModel(Context context){
        this.context=context;
    }

    public final Call<Customer> getCustomer() {
        return this.customerService.getCustomer();
    }

}
