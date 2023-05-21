package com.example.javafinal.com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javafinal.R;

public class Welcome_page extends AppCompatActivity {
    private Button joinButton, loginButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);
        joinButton = (Button) findViewById(R.id.registration);
        loginButton = (Button) findViewById(R.id.logIn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Welcome_page.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Welcome_page.this, RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}
