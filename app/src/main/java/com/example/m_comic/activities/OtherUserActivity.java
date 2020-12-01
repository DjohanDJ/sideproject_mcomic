package com.example.m_comic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.m_comic.R;
import com.example.m_comic.fragments.ProfileFragment;
import com.example.m_comic.models.User;

public class OtherUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        Intent intent = getIntent();
        final String userSelectedId = intent.getStringExtra("userId");
        final String userSelectedUsername = intent.getStringExtra("username");
        final String userSelectedRole = intent.getStringExtra("userRole");
        final String userSelectedEmail = intent.getStringExtra("userEmail");

        ProfileFragment.getInstance().setSelectedUser(new User(userSelectedUsername, userSelectedId, userSelectedEmail, "", userSelectedRole));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, ProfileFragment.getInstance()).commit();
    }
}