package com.example.m_comic.activities.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;

public class ComicViewHolder extends RecyclerView.ViewHolder {

    private ImageView comicImage, userImage;
    private CardView comicCardView, userCardView;
    private TextView userText;

    public ComicViewHolder(@NonNull View itemView) {
        super(itemView);
        comicImage = itemView.findViewById(R.id.comicImage);
        comicCardView = itemView.findViewById(R.id.comicCardView);
        userImage = itemView.findViewById(R.id.userImage);
        userCardView = itemView.findViewById(R.id.userCardView);
        userText = itemView.findViewById(R.id.userTitle);
    }

    public ImageView getComicImage() {
        return comicImage;
    }

    public ImageView getUserImage() {
        return userImage;
    }

    public CardView getComicCardView() {
        return comicCardView;
    }

    public CardView getUserCardView() {
        return userCardView;
    }

    public TextView getUserText() {
        return userText;
    }
}
