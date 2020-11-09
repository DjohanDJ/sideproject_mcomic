package com.example.m_comic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m_comic.R;
import com.example.m_comic.animations.LoadingAnimation;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.fragments.ProfileFragment;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {

    private EditText usernameText, passwordText, rePasswordText;
    private Button updateButton;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        doInitializeItems();
        doButtonListener();
    }

    private void doButtonListener() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckValidation();
            }
        });
    }

    private void doCheckValidation() {
        final String username = this.usernameText.getText().toString();
        final String password = this.passwordText.getText().toString();
        String rePassword = this.rePasswordText.getText().toString();

        if (username.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.username_filled), Toast.LENGTH_SHORT).show();
        } else if (username.trim().length() > 10) {
            Toast.makeText(this, getResources().getString(R.string.username_failed), Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.passFilled), Toast.LENGTH_SHORT).show();
        } else if (password.trim().length() < 5) {
            Toast.makeText(this, getResources().getString(R.string.password_failed), Toast.LENGTH_SHORT).show();
        } else if (!rePassword.trim().equals(password.trim())) {
            Toast.makeText(this, getResources().getString(R.string.re_pass_failed), Toast.LENGTH_SHORT).show();
        } else {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(UserSession.getCurrentUser().getEmail(), UserSession.getCurrentUser().getPassword());
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    LoadingAnimation.startLoading(EditProfileActivity.this);
                    if (task.isSuccessful()) {
                        firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    final User updatedUser = new User(username, UserSession.getCurrentUser().getId(), UserSession.getCurrentUser().getEmail(), password, UserSession.getCurrentUser().getRole());
                                    SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users")
                                            .document(UserSession.getCurrentUser().getId())
                                            .set(updatedUser)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    UserSession.setCurrentUser(updatedUser);
                                                    ProfileFragment.setUsernameText(username);
                                                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void doInitializeItems() {
        this.usernameText = findViewById(R.id.username_text);
        this.passwordText = findViewById(R.id.pass_text);
        this.rePasswordText = findViewById(R.id.re_pass_text);
        this.updateButton = findViewById(R.id.updateBtn);
        this.usernameText.setText(UserSession.getCurrentUser().getUsername());
    }
}