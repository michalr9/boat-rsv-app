package com.example.boatrsvapp.ui.customer.account;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boatrsvapp.R;
import com.example.boatrsvapp.data.model.Address;
import com.example.boatrsvapp.data.model.Customer;

public class AccountActivity extends AppCompatActivity {
    private final String TAG = "AccountActivity";

    private EditText editText_name,editText_surname,editText_phoneNumber,editText_email,editText_street,editText_number,editText_city,editText_postalCode,edit_cusId,edit_adrId;
    Button buttonSaveData;
    AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editText_name = findViewById(R.id.editTextTextPersonName);
        editText_surname = findViewById(R.id.editTextTextPersonSurname);
        editText_phoneNumber = findViewById(R.id.editTextPhone);
        editText_email = findViewById(R.id.editTextTextEmailAddress);
        editText_street = findViewById(R.id.editTextTextStreet);
        editText_number = findViewById(R.id.editTextNumber);
        editText_city = findViewById(R.id.editTextTextCity);
        editText_postalCode = findViewById(R.id.editTextTextPostalAddress);
        edit_cusId = findViewById(R.id.customerId);
        edit_adrId = findViewById(R.id.addressId);

        buttonSaveData = findViewById(R.id.button_save);
        initSaveButtonListener();

        accountViewModel = new AccountViewModel(getApplicationContext());

        getCustomerData();
    }

    private void getCustomerData() {
        accountViewModel.getCustomer().enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.isSuccessful())
                setCustomerDataForm(response);
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                onCustomerFailure( call,t);
            }
        });
    }

    private void onCustomerFailure(Call<Customer> call,Throwable t) {
        Log.e(TAG,"Call "+call.request().toString()+" ERROR: " +t.getMessage());
        Toast.makeText(this, getString(R.string.data_load_err), Toast.LENGTH_LONG).show();
    }

    private void setCustomerDataForm(Response<Customer> response) {
        assert response.body() != null;
        editText_name.setText(response.body().getName());
        editText_surname.setText(response.body().getSurname());
        editText_phoneNumber.setText(response.body().getPhoneNumber());
        editText_email.setText(response.body().getEmail());
        editText_street.setText(response.body().getAddress().getStreet());
        editText_number.setText(response.body().getAddress().getNum());
        editText_city.setText(response.body().getAddress().getCity());
        editText_postalCode.setText(response.body().getAddress().getZipCode());
        edit_cusId.setText(response.body().getId().toString());
        edit_adrId.setText(response.body().getAddress().getId().toString());
    }

    private void initSaveButtonListener() {
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });}

    private void saveData() {
        Address address = new Address().setId(Long.parseLong(edit_adrId.getText().toString()))
                .setStreet(editText_street.getText().toString())
                .setCity(editText_city.getText().toString())
                .setNum(editText_number.getText().toString())
                .setZipCode(editText_postalCode.getText().toString());
        Customer customer = new Customer().setId(Long.parseLong(edit_cusId.getText().toString()))
                .setName(editText_name.getText().toString())
                .setSurname(editText_surname.getText().toString())
                .setPhoneNumber(editText_phoneNumber.getText().toString())
                .setEmail(editText_email.getText().toString())
                .setAddress(address);

        accountViewModel.updateCustomer(customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.isSuccessful()){
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                onCustomerFailure(call, t);
            }
        });
    }

    private void onSuccess() {
        Toast.makeText(this, "Zapisano zmiany", Toast.LENGTH_LONG).show();
    }


}