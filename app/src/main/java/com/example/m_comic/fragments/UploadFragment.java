package com.example.m_comic.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m_comic.R;
import com.example.m_comic.activities.UploadMaterialActivity;
import com.example.m_comic.authentications.UserSession;

import java.util.Objects;

public class UploadFragment extends Fragment {

    private Button uploadButton;
    private ImageView adminUpload, guestUpload;
    private TextView guestText;

    public UploadFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        doInitializeItems(view);
        doUserFetcher();
        doButtonListener();
        return view;
    }

    private void doButtonListener() {
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UploadMaterialActivity.class));
            }
        });
    }

    private void doUserFetcher() {
        if (UserSession.getCurrentUser().getRole().equals("Guest")) {
            adminUpload.setVisibility(View.GONE);
            uploadButton.setVisibility(View.GONE);
        } else {
            guestText.setText("");
            guestUpload.setVisibility(View.GONE);
        }
    }

    private void doInitializeItems(View view) {
        uploadButton = view.findViewById(R.id.uploadButton);
        adminUpload = view.findViewById(R.id.imageUploadAdmin);
        guestUpload = view.findViewById(R.id.imageUploadGuest);
        guestText = view.findViewById(R.id.textUploadGuest);
    }

}