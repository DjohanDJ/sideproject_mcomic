package com.example.m_comic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.m_comic.R;
import com.example.m_comic.activities.adapters.UserAdapter;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<User> userList = null, userListFull = null;
    private SearchView mySearchView;
    private UserAdapter userAdapter;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        doInitializeItems(view);
        doGetAllUsers(view);
        doSearchListener();
        return view;
    }

    private void doSearchListener() {
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void doGetAllUsers(final View view) {
        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userList.clear();
                    userListFull.clear();
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        if (Objects.equals(documentSnapshot.getData().get("role"), "Admin") && !Objects.equals(documentSnapshot.getData().get("email"), UserSession.getCurrentUser().getEmail())) {
                            User user = new User(Objects.requireNonNull(documentSnapshot.getData().get("username")).toString()
                                    , documentSnapshot.getId(), Objects.requireNonNull(documentSnapshot.getData().get("email")).toString()
                                    , Objects.requireNonNull(documentSnapshot.getData().get("password")).toString()
                                    , Objects.requireNonNull(documentSnapshot.getData().get("role")).toString());
                            userList.add(user);
                            userListFull.add(user);
                        }
                    }

                    userAdapter = new UserAdapter(view.getContext(), userList, userListFull);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });
    }

    private void doInitializeItems(View view) {
        this.recyclerView = view.findViewById(R.id.recViewUser);
        this.userList = new ArrayList<>();
        this.userListFull = new ArrayList<>();
        this.mySearchView = view.findViewById(R.id.searchViewUser);
    }


}