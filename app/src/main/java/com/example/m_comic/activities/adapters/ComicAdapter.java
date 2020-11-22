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
import com.example.m_comic.activities.holders.ComicViewHolder;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.models.Comic;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;

public class ComicAdapter extends RecyclerView.Adapter<ComicViewHolder> {

    private ArrayList<Comic> comics;
    private Context ctx;
    private User selectedUser;
    private ArrayList<User> users;

    public ComicAdapter(Context ctx, ArrayList<Comic> comics){
        this.ctx = ctx;
        this.comics = comics;
        this.users = new ArrayList<>();
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_comic_in_home, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComicViewHolder holder, final int position) {
        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users")
                .document(comics.get(position).getUser_id()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                assert documentSnapshot != null;
                selectedUser = documentSnapshot.toObject(User.class);
                holder.getUserText().setText(selectedUser.getUsername());
                users.add(selectedUser);
            }
        });
        holder.getUserImage().setImageResource(R.drawable.admin_avatar);
        holder.getUserCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OtherUserActivity.class);
                intent.putExtra("userId", users.get(position).getId());
                intent.putExtra("userEmail", users.get(position).getEmail());
                intent.putExtra("userRole", users.get(position).getRole());
                intent.putExtra("username", users.get(position).getUsername());
                v.getContext().startActivity(intent);
            }
        });
        Glide.with(holder.getComicImage().getContext()).load(comics.get(position).getComic_details().get(0).getImage())
                .apply(new RequestOptions().override(400, 400)).into(holder.getComicImage());
        holder.getComicCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComic = new Intent(v.getContext(), ComicActivity.class);
                intentComic.putExtra("comicId", comics.get(position).getId());
                ComicActivity.setComicsParsed(comics.get(position).getComic_details());
                v.getContext().startActivity(intentComic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }
}
