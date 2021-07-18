package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private TextInputEditText text1,text2,text3;
    private TextInputLayout tilName,tilEmail,tilPass;
    private Button reg;
    private LinearLayout layout;
    private Pattern password=Pattern.compile("^" +
                                    "(?=.*[a-z])" +
                                    "(?=.*[A-z])" +
                                    "(?=.*[0-9])" +
                                    ".{6,}" +
                                    "$");
    private Pattern pat_name=Pattern.compile("^[A-Z].{3,}$");

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private HashMap<String,Object> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1=findViewById(R.id.textInputEditText1);
        text2=findViewById(R.id.textInputEditText2);
        text3=findViewById(R.id.textInputEditText3);

        tilEmail=findViewById(R.id.textInputLayout1);
        tilPass=findViewById(R.id.textInputLayout2);
        tilName=findViewById(R.id.textInputLayout3);

        reg=findViewById(R.id.btn_Reg);

        layout=findViewById(R.id.layout_Linear);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=text1.getText().toString();
                String pass=text2.getText().toString();
                String name=text3.getText().toString();

                tilName.setError(null);
                tilEmail.setError(null);
                tilPass.setError(null);


                /*if(email.isEmpty())
                {
                    tilEmail.setError("Email cannot be empty");
                }
                if(pass.isEmpty())
                {
                    tilPass.setError("Password cannot be empty");
                }
                if(name.isEmpty())
                {
                    tilName.setError("Name cannot be empty");
                }*/
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
                {
                    tilEmail.setError("Invalid Email!");
                }
                if(!pat_name.matcher(name).matches() || name.isEmpty())
                {
                    tilName.setError("Invalid Name!");
                }
                if(!password.matcher(pass).matches() || pass.isEmpty())
                {
                    tilPass.setError("Invalid Password!");
                }
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty() && pat_name.matcher(name).matches() && !name.isEmpty() && password.matcher(pass).matches() && !pass.isEmpty())
                {
                    registerUser(name,email,pass);
                    tilName.setError(null);
                    tilEmail.setError(null);
                    tilPass.setError(null);
                }

            }
        });

    }

    private void registerUser(String name, String email, String pass)
    {

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    DynamicToast.makeSuccess(Registration.this,"Registration Successful",3000).show();

                    map.put(name,email);

                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                    reference.child(uid).child("Profile").updateChildren(map);

                    startActivity(new Intent(Registration.this,Login.class));
                    finish();
                }
                else
                {
                    DynamicToast.makeError(Registration.this,"Registration Failed",3000).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();

        }

        return super.onOptionsItemSelected(item);
    }
}