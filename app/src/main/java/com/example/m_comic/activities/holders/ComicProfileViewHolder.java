package com.example.m_comic.activities.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;

public class ComicProfileViewHolder extends RecyclerView.ViewHolder {

    private ImageView comicProfileImage;
    private CardView comicProfileCardView;
    private TextView comicProfileTitle;

    public ComicProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        comicProfileImage = itemView.findViewById(R.id.comicProfileImage);
        comicProfileTitle = itemView.findViewById(R.id.comicProfileTitle);
        comicProfileCardView = itemView.findViewById(R.id.comicProfileCardView);
    }

    public ImageView getComicProfileImage() {
        return comicProfileImage;
    }

    public CardView getComicProfileCardView() {
        return comicProfileCardView;
    }

    public TextView getComicProfileTitle() {
        return comicProfileTitle;
    }

}
