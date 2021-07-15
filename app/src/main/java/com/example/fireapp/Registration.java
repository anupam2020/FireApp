package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private TextInputEditText text1,text2;
    private Button reg;
    private LinearLayout layout;
    private Pattern password=Pattern.compile("^" +
                                    "(?=.*[a-z])" +
                                    "(?=.*[A-z])" +
                                    "(?=.*[0-9])" +
                                    ".{6,}" +
                                    "$");

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1=findViewById(R.id.textInputEditText1);
        text2=findViewById(R.id.textInputEditText2);

        reg=findViewById(R.id.btn_Reg);

        layout=findViewById(R.id.layout_Linear);

        auth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=text1.getText().toString();
                String pass=text2.getText().toString();

                if(email.isEmpty() || pass.isEmpty())
                {
                    Snackbar.make(layout,"Invalid Credentials "+email+" "+pass,Snackbar.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(Registration.this,"Invalid Email "+email+" "+pass,Toast.LENGTH_SHORT).show();
                }
                else if(!password.matcher(pass).matches())
                {
                    Toast.makeText(Registration.this,"Password too weak "+email+" "+pass,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    registerUser(email,pass);
                }

            }
        });

    }

    private void registerUser(String email, String pass)
    {

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Snackbar.make(layout,"Registration Successful!",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    Snackbar.make(layout,"Registration Failed!",Snackbar.LENGTH_LONG).show();
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