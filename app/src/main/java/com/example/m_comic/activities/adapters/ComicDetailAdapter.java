package com.example.m_comic.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.m_comic.R;
import com.example.m_comic.activities.ComicActivity;
import com.example.m_comic.activities.OtherUserActivity;
import com.example.m_comic.activities.holders.ComicDetailViewHolder;
import com.example.m_comic.activities.holders.ComicViewHolder;
import com.example.m_comic.models.Comic;
import com.example.m_comic.models.ComicDetail;
import com.example.m_comic.models.User;

import java.util.ArrayList;

public class ComicDetailAdapter extends RecyclerView.Adapter<ComicDetailViewHolder> {

    private ArrayList<ComicDetail> comicDetails;
    private Context ctx;

    public ComicDetailAdapter(Context ctx, ArrayList<ComicDetail> comicDetails){
        this.ctx = ctx;
        this.comicDetails = comicDetails;
    }

    @NonNull
    @Override
    public ComicDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_comic_detail, parent, false);
        return new ComicDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicDetailViewHolder holder, int position) {

        holder.getComicDetailTitle().setText(comicDetails.get(position).getName());

        Glide.with(holder.getComicDetailImage().getContext()).load(comicDetails.get(position).getImage())
                .apply(new RequestOptions().override(400, 400)).into(holder.getComicDetailImage());
        holder.getPlayButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.getPauseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.getStopButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return comicDetails.size();
    }
}
