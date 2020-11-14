package com.example.m_comic.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;
import com.example.m_comic.activities.NavigationActivity;
import com.example.m_comic.activities.holders.UserViewHolder;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class ManageUserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> users;
    private Context ctx;

    public ManageUserAdapter(Context ctx, ArrayList<User> users){
        this.ctx = ctx;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_manage_user_in_landscape, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        holder.getMyText().setText(users.get(position).getUsername());
        holder.getMyImage().setImageResource(R.drawable.guest_avatar);
        holder.getGiveAccess().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users").document(users.get(position).getId()).update("role", "Admin");
                ((Activity)v.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
