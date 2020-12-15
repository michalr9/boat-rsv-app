package com.example.boatrsvapp.ui.customer.account;

import android.content.Context;
import com.example.boatrsvapp.data.model.Customer;
import com.example.boatrsvapp.service.CustomerService;
import com.example.boatrsvapp.service.ServiceGenerator;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;

public class AccountViewModel extends ViewModel {
    CustomerService customerService;

    public AccountViewModel(Context context){
        customerService = new ServiceGenerator().createServiceWithToken(CustomerService.class,context);
    }

    public final Call<Customer> getCustomer() {
        return this.customerService.getCustomer();
    }

}
