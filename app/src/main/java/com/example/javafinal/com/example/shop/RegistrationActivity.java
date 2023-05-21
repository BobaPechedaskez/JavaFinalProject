package com.example.javafinal.com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javafinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerBtn;
    private EditText registerPhonenumber, registerPassword;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registerBtn = (Button) findViewById(R.id.register_login_btn);
        registerPassword = (EditText) findViewById(R.id.register_password);
        registerPhonenumber = (EditText) findViewById(R.id.register_phone_number);
        loadingBar = new ProgressDialog(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
                
            }
        });
    }

    private void CreateAccount() {
        String phoneNumber = registerPhonenumber.getText().toString();
        String password = registerPassword.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_LONG).show();
        }
        else{
            loadingBar.setTitle("Создание аккаунта");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhone(phoneNumber, password);
        }
    }

    private void ValidatePhone(String phoneNumber, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phoneNumber).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phoneNumber);
                    userDataMap.put("password", password);

                    RootRef.child("Users").child(phoneNumber).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loadingBar.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                                Intent registerIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(registerIntent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Ошибка((", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    loadingBar.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Номер"+" "+phoneNumber+" "+ "Уже зарегистрирован", Toast.LENGTH_LONG).show();
                    Intent registerIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}