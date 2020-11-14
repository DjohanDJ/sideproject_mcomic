package com.example.m_comic.activities.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView myText;
    private ImageView myImage;
    private CardView cardView;
    private Button giveAccess;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        myText = itemView.findViewById(R.id.titleRow);
        myImage = itemView.findViewById(R.id.imageRow);
        cardView = itemView.findViewById(R.id.cardView);
        giveAccess = itemView.findViewById(R.id.giveAccessBtn);
    }

    public TextView getMyText() {
        return myText;
    }

    public ImageView getMyImage() {
        return myImage;
    }

    public CardView getCardView() {
        return cardView;
    }

    public Button getGiveAccess() {
        return giveAccess;
    }

}
