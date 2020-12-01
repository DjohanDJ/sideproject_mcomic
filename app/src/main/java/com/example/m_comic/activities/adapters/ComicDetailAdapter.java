package com.example.m_comic.activities.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.m_comic.activities.holders.ComicDetailViewHolder;
import com.example.m_comic.models.ComicDetail;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.m_comic.R.*;

public class ComicDetailAdapter extends RecyclerView.Adapter<ComicDetailViewHolder> {

    private ArrayList<ComicDetail> comicDetails;
    private Context ctx;
    private static MediaPlayer mediaPlayer = null;

    public ComicDetailAdapter(Context ctx, ArrayList<ComicDetail> comicDetails){
        this.ctx = ctx;
        this.comicDetails = comicDetails;
    }

    @NonNull
    @Override
    public ComicDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(layout.card_comic_detail, parent, false);
        return new ComicDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicDetailViewHolder holder, final int position) {

        holder.getComicDetailTitle().setText(comicDetails.get(position).getName());

        Glide.with(holder.getComicDetailImage().getContext()).load(comicDetails.get(position).getImage())
                .apply(new RequestOptions().override(400, 400)).into(holder.getComicDetailImage());
        holder.getPlayButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comicDetails.get(position).getSound().equals("No Sound")) {
                    Toast.makeText(ctx, string.cant_play_sound, Toast.LENGTH_SHORT).show();
                } else {
                    doManageSound(comicDetails.get(position).getSound());
                }
            }
        });

        holder.getPauseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        holder.getStopButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    private void doManageSound(String sound) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(sound);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void stop() {
        stopPlayer();
    }

    public static void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public int getItemCount() {
        return comicDetails.size();
    }


}
