package com.example.javafinal.com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javafinal.com.Admin.AdminsCategoryActivity;
import com.example.javafinal.com.Model.Users;
import com.example.javafinal.com.Users.MainPageActivity;
import com.example.javafinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText phone_number, password;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    private CheckBox checkBox;
    public static final String PREFS_FILE = "Account";
    public static final String PREF_PHONE= "Phone";
    public static final String PREF_PASSWORD = "PASSWORD";
    private TextView AdminLink, NonAdminLink;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.login_btn);
        phone_number = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NonAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);
        checkBox = (CheckBox) findViewById(R.id.login_checkbox);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLink.setVisibility(View.INVISIBLE);
                NonAdminLink.setVisibility(View.VISIBLE);
                loginButton.setText("Вход для администратора");
                parentDbName = "Admins";

            }
        });
        NonAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLink.setVisibility(View.VISIBLE);
                NonAdminLink.setVisibility(View.INVISIBLE);
                loginButton.setText("Войти");
                parentDbName = "Users";
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setParams();
    }

    private void loginUser() {
        String phone = phone_number.getText().toString();
        String pasword = password.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pasword)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_LONG).show();
        }
        else{
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateUser(phone, pasword);
        }
    }

    private void ValidateUser(String phone, String pasword) {
        if (checkBox.isChecked()){
            saveParams();
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){
                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                    assert usersData != null;
                    if(usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(pasword)){
                            if(parentDbName.equals("Users")){
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Авторизация прошла успешно!", Toast.LENGTH_LONG).show();
                                Intent mainPageIntent = new Intent(LoginActivity.this, MainPageActivity.class);
                                startActivity(mainPageIntent);
                            } else if (parentDbName.equals("Admins")) {
                                loadingBar.dismiss();
                                Intent adminsNewProductIntent = new Intent(LoginActivity.this, AdminsCategoryActivity.class);
                                startActivity(adminsNewProductIntent);
                            }
                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Аккаунта с номером"+" "+ phone+" "+" не существует", Toast.LENGTH_LONG).show();
                    Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void saveParams(){
        String pass = password.getText().toString();
        String phoneNum = phone_number.getText().toString();
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(PREF_PASSWORD, pass);
        prefEditor.putString(PREF_PHONE, phoneNum);
        prefEditor.apply();
    }
    public void setParams(){
        String number = settings.getString(PREF_PHONE, "");
        phone_number.setText(number);
        String pasw = settings.getString(PREF_PASSWORD, "");
        password.setText(pasw);
    }
}