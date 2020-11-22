package com.example.m_comic.activities.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;

public class ComicDetailViewHolder extends RecyclerView.ViewHolder {

    private ImageView comicDetailImage;
    private CardView comicDetailCardView;
    private TextView comicDetailTitle;
    private Button playButton, stopButton, pauseButton;

    public ComicDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        comicDetailImage = itemView.findViewById(R.id.comicDetailImage);
        comicDetailTitle = itemView.findViewById(R.id.comicDetailTitle);
        comicDetailCardView = itemView.findViewById(R.id.comicDetailCardView);
        playButton = itemView.findViewById(R.id.playButton);
        stopButton = itemView.findViewById(R.id.stopButton);
        pauseButton = itemView.findViewById(R.id.pauseButton);
    }

    public ImageView getComicDetailImage() {
        return comicDetailImage;
    }

    public CardView getComicDetailCardView() {
        return comicDetailCardView;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public TextView getComicDetailTitle() {
        return comicDetailTitle;
    }
}
