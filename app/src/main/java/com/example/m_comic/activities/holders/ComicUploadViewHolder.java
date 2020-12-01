package com.example.m_comic.activities.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;

public class ComicUploadViewHolder extends RecyclerView.ViewHolder {

    private ImageView comicUploadImage;
    private TextView comicUploadTitle;

    public ComicUploadViewHolder(@NonNull View itemView) {
        super(itemView);
        comicUploadImage = itemView.findViewById(R.id.comicDetailUploadImage);
        comicUploadTitle = itemView.findViewById(R.id.comicDetailUploadTitle);
    }

    public ImageView getComicUploadImage() {
        return comicUploadImage;
    }

    public TextView getComicUploadTitle() {
        return comicUploadTitle;
    }

}
