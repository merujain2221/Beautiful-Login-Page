package com.example.beautifulloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registerr;
    EditText passWord,usernamelogin;
    Button button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernamelogin = findViewById(R.id.usernamelogin);
        registerr = findViewById(R.id.registerButton);
        passWord = findViewById(R.id.passWord);
        button = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(this);
        registerr.setOnClickListener(this);












    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                startActivity(new Intent(this,resgistation.class));
                break;
            case R.id.button:
                userLogin();
                break;





        }

    }

    private void userLogin() {
        String email = usernamelogin.getText().toString().trim();
        String password = passWord.getText().toString().trim();

        if(email.isEmpty()){
            usernamelogin.setError("Plz Enter Email");
            usernamelogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passWord.setError("Plz Enter Password");
            passWord.requestFocus();
            return;
        } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            usernamelogin.setError("Plz Enter valid Email Address");
            usernamelogin.requestFocus();
            return;
        } if(password.length() < 4){
            passWord.setError("Min length should be 4");
            passWord.requestFocus();
            return;

        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        Toast.makeText(MainActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,login.class));

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verified your account", Toast.LENGTH_SHORT).show();

                    }



                }else {
                    Toast.makeText(MainActivity.this, "Login Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}