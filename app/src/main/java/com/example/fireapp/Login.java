package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private TextInputEditText text1,text2;
    private Button login;
    private LinearLayout layout;
    private Pattern password=Pattern.compile("^" +
            "(?=.*[a-z])" +
            "(?=.*[A-z])" +
            "(?=.*[0-9])" +
            ".{6,}" +
            "$");

    private FirebaseAuth auth;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1=findViewById(R.id.textInputEditText1);
        text2=findViewById(R.id.textInputEditText2);

        login=findViewById(R.id.btn_Login);

        layout=findViewById(R.id.layout_Linear);

        auth=FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        /*try {
            Toast.makeText(this,"User: "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            Toast.makeText(this,"Exception: "+e,Toast.LENGTH_LONG).show();
        }*/


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=text1.getText().toString();
                String pass=text2.getText().toString();

                if(email.isEmpty() || pass.isEmpty())
                {
                    Snackbar.make(layout,"Invalid Credentials!",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            DynamicToast.makeSuccess(Login.this,"Login Successful",3000).show();
                            startActivity(new Intent(Login.this, Second.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {

                            DynamicToast.makeError(Login.this,"Login Failed",3000).show();
                        }
                    });
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