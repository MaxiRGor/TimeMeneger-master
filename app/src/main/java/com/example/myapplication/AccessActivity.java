package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccessActivity extends AppCompatActivity implements View.OnClickListener {

    Button go, registration;
    EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        email = (EditText) findViewById(R.id.email_etext);
        password = (EditText) findViewById(R.id.password_etext);
        go = (Button) findViewById(R.id.go_btn);
        registration = (Button) findViewById(R.id.registration_btn);
        go.setOnClickListener(this);
        registration.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                } else {
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_btn:
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    go(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(AccessActivity.this, "Введите почту и пароль",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registration_btn:
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    registration(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(AccessActivity.this, "Введите почту и пароль",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void go(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(AccessActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(AccessActivity.this, SecretCodesActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AccessActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(AccessActivity.this, "Registration success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(AccessActivity.this, SecretCodesActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AccessActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
