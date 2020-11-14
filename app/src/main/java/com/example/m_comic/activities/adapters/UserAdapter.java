package com.example.m_comic.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_comic.R;
import com.example.m_comic.activities.OtherUserActivity;
import com.example.m_comic.activities.holders.UserViewHolder;
import com.example.m_comic.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements Filterable {

    private ArrayList<User> users, listUserFull;
    private Context ctx;

    public UserAdapter(Context ctx, ArrayList<User> users, ArrayList<User> listUserFull){
        this.ctx = ctx;
        this.users = users;
        this.listUserFull = listUserFull;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_user_in_landscape, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        holder.getMyText().setText(users.get(position).getUsername());
        holder.getMyImage().setImageResource(R.drawable.admin_avatar);
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listUserFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User item : listUserFull) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users.clear();
            users.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
