package com.example.javafinal.com.Users;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javafinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView image;
    private EditText nameEdit, phoneEdit;
    private Button changePswrdBtn, saveBtn;
    public static final String PREFS_FILE = "Account";
    public static final String PREF_PHONE = "Phone";
    SharedPreferences prefs;


    private String saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(nameEdit.toString())) || (!(TextUtils.isEmpty(phoneEdit.toString())))) {
                    updateInfo();
                }
            }
        });
    }

    public void init() {
        image = findViewById(R.id.settingsImageView);
        nameEdit = findViewById(R.id.settingsName);
        phoneEdit = findViewById(R.id.settingsPhoneNumber);
        changePswrdBtn = findViewById(R.id.settingsChangePassword);
        saveBtn = findViewById(R.id.settingsSave);
        prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
    }

    public void updateInfo() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userSettingsmap = new HashMap<>();
        userSettingsmap.put("name", nameEdit.getText().toString());
        userSettingsmap.put("phone", phoneEdit.getText().toString());
        String phoneInPreferenses = prefs.getString(PREF_PHONE, "");
        databaseReference.child(phoneInPreferenses).updateChildren(userSettingsmap);
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(SettingsActivity.this, "Данные перезаписаны", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}