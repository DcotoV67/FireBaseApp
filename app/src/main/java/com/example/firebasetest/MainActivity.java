package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userEditText, passwordEditText;
    Button logInButton;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        userEditText = findViewById(R.id.user);
        passwordEditText = findViewById(R.id.password);
        logInButton = findViewById(R.id.login_btn);
        signInButton = findViewById(R.id.signin_btn);

    }

    void createAccount(){
        mAuth.createUserWithEmailAndPassword(userEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Crear email/password OK", Toast.LENGTH_SHORT).show();
                            emailSignIn();
                        } else {
                            Log.e("ABCD", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Crear email/password FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void emailSignIn() {
        mAuth.signInWithEmailAndPassword(userEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Email signin OK", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoggedActivity.class);
                            startActivity(intent);

                        } else {
                            Log.e("ABCD", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Email signin FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void login(){
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }
    void signin(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSignIn();
            }
        });
    }
}
