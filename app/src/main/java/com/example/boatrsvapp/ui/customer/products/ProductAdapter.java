package com.example.boatrsvapp.ui.customer.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boatrsvapp.R;
import com.example.boatrsvapp.data.model.Product;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    public static final String PRODUCT_ID="PRODUCT_ID_MESSAGE";
    List<Product> products;
    Context context;
    public ProductAdapter(List<Product> products){
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView textViewTitle;
       TextView textViewShortDesc;
       TextView textViewPrice;
       ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=   itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc=itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        final View item = inflater.inflate(R.layout.layout_product,parent,false);
        return new ViewHolder(item);
    }

    // ustawienie warrtosci
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(products.size()!=0 || products!=null){
            Product product = products.get(position);
            String short_desc;
            if(product.getDescription().length()>15){
                short_desc = product.getDescription().substring(0,15);
            }else {
                short_desc = product.getDescription();
            }
            holder.textViewTitle.setText(product.getName());
            holder.textViewPrice.setText(new StringBuilder().append(product.getPrice()).append(" zł").toString());
            holder.textViewShortDesc.setText(short_desc);

            Glide.with(context).load(product.getImgUrl()).into(holder.imageView);
//TODO wejscie w detale produktu
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    goToProductDetails(product.getId());
//                }
//            });
        }
    }

//    private void goToProductDetails(Long productId) {
//        Intent intent = new Intent(this, ProductDetailsActivity.class).putExtra(PRODUCT_ID,productId);
//        context.startActivity(intent);
//    }


    @Override
    public int getItemCount() {
        return products.size();
    }

}
