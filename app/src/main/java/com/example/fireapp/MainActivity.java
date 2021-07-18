package com.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button login,register;

    FirebaseUser user;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.btn_Login);
        register=findViewById(R.id.btn_Register);

        user= FirebaseAuth.getInstance().getCurrentUser();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

        /*reference.child("Laptop").child("Asus").setValue(40000);
        reference.child("Laptop").child("Dell").setValue(52000);
        reference.child("Laptop").child("HP").setValue(46500);

        HashMap<String,Object> map=new HashMap<>();
        map.put("Anupam","anupam00basak@gmail.com");
        map.put("Hello","hello@gmail.com");

        reference.child("Profile").updateChildren(map);*/

    }
}