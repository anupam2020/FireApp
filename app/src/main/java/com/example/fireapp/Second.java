package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Second extends AppCompatActivity {

    private Button add;
    private TextInputLayout layout;
    private TextInputEditText text;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add=findViewById(R.id.buttonAdd);

        layout=findViewById(R.id.textLayout);

        text=findViewById(R.id.editText);

        database=FirebaseDatabase.getInstance();

        String uid1= FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference=database.getReference().child(uid1).child("Texts");

        list=findViewById(R.id.listView);

        ArrayList<String> arrayList=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        list.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    arrayList.add(dataSnapshot.getValue().toString());

                    /*User user=dataSnapshot.getValue(User.class);
                    String txt=user.getName()+" : "+user.getEmail();
                    arrayList.add(txt);*/
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str=text.getText().toString();

                //HashMap<String,Object> map=new HashMap<>();

                if(str.isEmpty())
                {
                    layout.setError("Field cannot be empty");
                }
                else
                {

                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference();

                    ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Texts").push().setValue(str);

                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Second.this,MainActivity.class));
                finish();
                break;
            case android.R.id.home:
                AlertDialog.Builder alert=new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Do you really want to logout?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(Second.this,MainActivity.class));
                        finish();

                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                alert.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }
}