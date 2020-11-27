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
import android.widget.Toast;

import com.example.m_comic.R;
import com.example.m_comic.animations.LoadingAnimation;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.helpers.ValidationHelper;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameText, emailText, passwordText, rePasswordText;
    private Button signUpButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        doInitializeItems();
        doButtonListener();
    }

    private void doButtonListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckValidation();
            }
        });
    }

    private void doCheckValidation() {
        final String username = this.usernameText.getText().toString();
        final String email = this.emailText.getText().toString();
        final String password = this.passwordText.getText().toString();
        String rePassword = this.rePasswordText.getText().toString();

        if (username.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.username_filled), Toast.LENGTH_SHORT).show();
        } else if (username.trim().length() > 10) {
            Toast.makeText(this, getResources().getString(R.string.username_failed), Toast.LENGTH_SHORT).show();
        } else if (email.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.emailFilled), Toast.LENGTH_SHORT).show();
        } else if (!ValidationHelper.doEmailValidation(email.trim())) {
            Toast.makeText(this, getResources().getString(R.string.emailFormat), Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.passFilled), Toast.LENGTH_SHORT).show();
        } else if (password.trim().length() < 5) {
            Toast.makeText(this, getResources().getString(R.string.password_failed), Toast.LENGTH_SHORT).show();
        } else if (!rePassword.trim().equals(password.trim())) {
            Toast.makeText(this, getResources().getString(R.string.re_pass_failed), Toast.LENGTH_SHORT).show();
        } else {
            LoadingAnimation.startLoading(RegisterActivity.this);
            SingletonFirebaseTool.getInstance().getMyFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.sign_up_error), Toast.LENGTH_SHORT).show();
                    } else {
                        String userId = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                        User newUser = new User(username, userId, email, password, "Guest");
                        UserSession.setCurrentUser(newUser);
                        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users").document(userId).set(newUser);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_userId", userId);
                        editor.apply();
                        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                        finish();
                    }
                }
            });
        }

    }

    private void doInitializeItems() {
        this.usernameText = findViewById(R.id.user_text);
        this.emailText = findViewById(R.id.email_text);
        this.passwordText = findViewById(R.id.pass_text);
        this.rePasswordText = findViewById(R.id.re_pass_text);
        this.signUpButton = findViewById(R.id.sign_up_button);
        this.sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    }
}