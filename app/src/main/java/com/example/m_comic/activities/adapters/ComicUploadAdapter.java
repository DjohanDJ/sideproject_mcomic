package com.example.m_comic.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.m_comic.R;
import com.example.m_comic.activities.holders.ComicProfileViewHolder;
import com.example.m_comic.activities.holders.ComicUploadViewHolder;
import com.example.m_comic.models.ComicDetail;

import java.util.ArrayList;

public class ComicUploadAdapter extends RecyclerView.Adapter<ComicUploadViewHolder> {

    private ArrayList<ComicDetail> comicDetails;
    private Context ctx;

    public ComicUploadAdapter(Context ctx, ArrayList<ComicDetail> comicDetails){
        this.ctx = ctx;
        this.comicDetails = comicDetails;
    }

    @NonNull
    @Override
    public ComicUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_comic_in_upload, parent, false);
        return new ComicUploadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicUploadViewHolder holder, int position) {
        holder.getComicUploadTitle().setText(comicDetails.get(position).getName());

        Glide.with(holder.getComicUploadImage().getContext()).load(comicDetails.get(position).getImage())
                .apply(new RequestOptions().override(400, 400)).into(holder.getComicUploadImage());
    }

    @Override
    public int getItemCount() {
        return comicDetails.size();
    }
}
