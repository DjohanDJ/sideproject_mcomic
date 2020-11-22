package com.example.m_comic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m_comic.R;
import com.example.m_comic.activities.adapters.ComicAdapter;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.models.Comic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Comic> comicList = null;
    private ComicAdapter comicAdapter;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        doInitializeItems(view);
        doGetAllComics(view);
        return view;
    }

    private void doGetAllComics(final View view) {
        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    comicList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        Comic comic = documentSnapshot.toObject(Comic.class);
                        comicList.add(comic);
                    }
                    comicAdapter = new ComicAdapter(view.getContext(), comicList);
                    recyclerView.setAdapter(comicAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });
    }

    private void doInitializeItems(View view) {
        this.recyclerView = view.findViewById(R.id.recViewComicHome);
        this.comicList = new ArrayList<>();
    }
}