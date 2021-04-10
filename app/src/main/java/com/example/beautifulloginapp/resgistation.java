package com.example.beautifulloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class resgistation extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText editFullname,editage,editEmail,editPasword;
    Button register;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistation);
        mAuth = FirebaseAuth.getInstance();
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        editage=(EditText) findViewById(R.id.age);
        editFullname=(EditText) findViewById(R.id.fullname);
        editEmail=(EditText) findViewById(R.id.email);
        editPasword=(EditText) findViewById(R.id.password);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        final String fullName= editFullname.getText().toString();
        final String age= editage.getText().toString();
        final String email= editEmail.getText().toString();
        String password= editPasword.getText().toString();
        if(fullName.isEmpty()){
            editFullname.setError("Plz Enter Full Name");
            editFullname.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editage.setError("Plz Enter Age");
            editage.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Plz Enter Email");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPasword.setError("Plz Enter Password");
            editPasword.requestFocus();
            return;
        } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Plz Enter valid Email Address");
            editEmail.requestFocus();
            return;
        } if(password.length() < 4){
            editPasword.setError("Min length should be 4");
            editPasword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName,age,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(resgistation.this, "User has been registered succesfully!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(resgistation.this, "Failed to register! try again", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }else {
                            Toast.makeText(resgistation.this, "Failed to register! try again", Toast.LENGTH_SHORT).show();


                        }
                    }
                });






    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure yo want to exit?").setCancelable(false).
                setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resgistation.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }
}
