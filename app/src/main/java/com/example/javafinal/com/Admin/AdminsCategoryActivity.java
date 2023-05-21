package com.example.javafinal.com.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javafinal.R;


public class AdminsCategoryActivity extends AppCompatActivity {
    private ImageView nikeView, pumaView, filaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_category);
        init();
        nikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminsCategoryActivity.this, AdminsNewProductActivity.class);
                intent.putExtra("category", "Nike");
                startActivity(intent);
            }
        });
        pumaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminsCategoryActivity.this, AdminsNewProductActivity.class);
                intent.putExtra("category", "Puma");
                startActivity(intent);
            }
        });
        filaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminsCategoryActivity.this, AdminsNewProductActivity.class);
                intent.putExtra("category", "Fila");
                startActivity(intent);
            }
        });
    }
    public void init(){
        nikeView = (ImageView) findViewById(R.id.nike);
        filaView = (ImageView) findViewById(R.id.fila);
        pumaView = (ImageView) findViewById(R.id.puma);

    }
}