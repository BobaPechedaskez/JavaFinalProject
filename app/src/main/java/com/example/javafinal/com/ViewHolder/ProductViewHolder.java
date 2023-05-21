package com.example.javafinal.com.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javafinal.R;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productDescription, productPrice;
    public ImageView image;
    public AdapterView.OnItemClickListener clickListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.product_image);
        productName = itemView.findViewById(R.id.product_name);
        productDescription = itemView.findViewById(R.id.product_description);
        productPrice = itemView.findViewById(R.id.product_price);
    }

    @Override
    public void onClick(View v) {
    }


    public void setItemClickListener(AdapterView.OnItemClickListener listener){
        this.clickListener = listener;
    }
}
