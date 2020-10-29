package com.example.m_comic.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.m_comic.R;
import com.example.m_comic.activities.LoginActivity;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static ProfileFragment instance = null;
    private Button editProfileButton, logoutButton;
    private ConstraintLayout menuItem;
    private ImageView menuButton;
    private SharedPreferences sharedPreferences;

    private ProfileFragment() {}

    public static ProfileFragment getInstance() {
        if (instance == null) return instance = new ProfileFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        doInitializeItems(view);
        doButtonListener();
        return view;
    }

    private void doButtonListener() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_userId", "");
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.setVisibility(View.GONE);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.setVisibility(View.VISIBLE);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Prompt to edit profile page.
            }
        });
    }

    private void doInitializeItems(View view) {
        editProfileButton = view.findViewById(R.id.editProfileBtn);
        menuButton = view.findViewById(R.id.menuBtn);
        menuItem = view.findViewById(R.id.menuItem);
        logoutButton = view.findViewById(R.id.logoutBtn);
        menuItem.setVisibility(View.GONE);
        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
    }

}