package com.example.m_comic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.m_comic.R;
import com.example.m_comic.activities.adapters.ManageUserAdapter;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ManageUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<User> userList;
    private ManageUserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        doInitializeItems();
        doGetAllUsers();
    }

    private void doGetAllUsers() {
        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        if (Objects.equals(documentSnapshot.getData().get("role"), "Guest") && !Objects.equals(documentSnapshot.getData().get("email"), UserSession.getCurrentUser().getEmail())) {
                            User user = new User(Objects.requireNonNull(documentSnapshot.getData().get("username")).toString()
                                    , documentSnapshot.getId(), Objects.requireNonNull(documentSnapshot.getData().get("email")).toString()
                                    , Objects.requireNonNull(documentSnapshot.getData().get("password")).toString()
                                    , Objects.requireNonNull(documentSnapshot.getData().get("role")).toString());
                            userList.add(user);
                        }
                    }
                    userAdapter = new ManageUserAdapter(ManageUserActivity.this, userList);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManageUserActivity.this));
                }
            }
        });
    }

    private void doInitializeItems() {
        this.recyclerView = findViewById(R.id.recViewManageUser);
        this.userList = new ArrayList<>();
    }
}