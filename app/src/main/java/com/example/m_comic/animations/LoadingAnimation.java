package com.example.m_comic.animations;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.m_comic.R;

public class LoadingAnimation {

    @SuppressLint("InflateParams")
    public static void startLoading(Activity myActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);

        LayoutInflater inflater = myActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.animation_loading, null));
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
