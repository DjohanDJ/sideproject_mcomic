package com.example.m_comic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_comic.R;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.helpers.ValidationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signInButton;
    private TextView signUpButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        doInitializeItems();
        doButtonListener();
    }

    private void doButtonListener() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckCredential();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void doCheckCredential() {
        String email = this.email.getText().toString();
        String pass = this.password.getText().toString();
        if (email.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.emailFilled), Toast.LENGTH_SHORT).show();
        } else if (!ValidationHelper.doEmailValidation(email.trim())) {
            Toast.makeText(this, getResources().getString(R.string.emailFormat), Toast.LENGTH_SHORT).show();
        } else if (pass.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.passFilled), Toast.LENGTH_SHORT).show();
        } else {
            SingletonFirebaseTool.getInstance().getMyFirebaseAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalidCredential), Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_userId", Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid());
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                        finish();
                    }
                }
            });
        }
    }

    private void doInitializeItems() {
        this.email = findViewById(R.id.email_text);
        this.password = findViewById(R.id.password_text);
        this.signInButton = findViewById(R.id.sign_in_button);
        this.signUpButton = findViewById(R.id.sign_up_button);
        this.sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    }
}