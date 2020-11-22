package com.example.m_comic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.m_comic.R;
import com.example.m_comic.activities.adapters.ComicDetailAdapter;
import com.example.m_comic.models.ComicDetail;
import java.util.ArrayList;

public class ComicActivity extends AppCompatActivity {

    private static ArrayList<ComicDetail> comicDetailsParsed;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        doInitializeItems();
        ComicDetailAdapter comicDetailAdapter = new ComicDetailAdapter(ComicActivity.this, comicDetailsParsed);
        recyclerView.setAdapter(comicDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ComicActivity.this));

    }

    private void doInitializeItems() {
        this.recyclerView = findViewById(R.id.recViewComicDetail);
    }

    public static void setComicsParsed(ArrayList<ComicDetail> comicDetailsParsed) {
        ComicActivity.comicDetailsParsed = comicDetailsParsed;
    }
}