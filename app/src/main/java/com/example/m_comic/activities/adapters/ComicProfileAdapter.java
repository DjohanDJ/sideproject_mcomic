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
import com.example.m_comic.activities.holders.ComicProfileViewHolder;
import com.example.m_comic.models.Comic;

import java.util.ArrayList;

public class ComicProfileAdapter extends RecyclerView.Adapter<ComicProfileViewHolder> {

    private ArrayList<Comic> comics;
    private Context ctx;

    public ComicProfileAdapter(Context ctx, ArrayList<Comic> comics) {
        this.ctx = ctx;
        this.comics = comics;
    }

    @NonNull
    @Override
    public ComicProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_comic_in_profile, parent, false);
        return new ComicProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicProfileViewHolder holder, final int position) {
        Glide.with(holder.getComicProfileImage().getContext()).load(comics.get(position).getComic_details().get(0).getImage())
                .apply(new RequestOptions().override(400, 400)).into(holder.getComicProfileImage());
        holder.getComicProfileCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComic = new Intent(v.getContext(), ComicActivity.class);
                intentComic.putExtra("comicId", comics.get(position).getId());
                ComicActivity.setComicsParsed(comics.get(position).getComic_details());
                v.getContext().startActivity(intentComic);
            }
        });
        holder.getComicProfileTitle().setText(comics.get(position).getComic_details().get(0).getName());
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }
}
