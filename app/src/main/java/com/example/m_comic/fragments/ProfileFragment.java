package com.example.m_comic.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m_comic.R;
import com.example.m_comic.activities.EditProfileActivity;
import com.example.m_comic.activities.LoginActivity;
import com.example.m_comic.activities.ManageUserActivity;
import com.example.m_comic.activities.UploadMaterialActivity;
import com.example.m_comic.activities.adapters.ComicAdapter;
import com.example.m_comic.activities.adapters.ComicProfileAdapter;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.models.Comic;
import com.example.m_comic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static ProfileFragment instance = null;
    private Button editProfileButton, logoutButton, manageUserButton, uploadMaterialButton;
    private ConstraintLayout menuItem;
    private ImageView menuButton;
    private SharedPreferences sharedPreferences;
    @SuppressLint("StaticFieldLeak")
    private static TextView usernameText;
    private TextView roleText, textGuest;
    private ImageView avatarIcon, imageGuest;
    private User selectedUser = null;
    private ArrayList<Comic> selectedUserComics = null;
    private ComicProfileAdapter comicProfileAdapter;
    private RecyclerView recyclerView;

    private ProfileFragment() {}

    public static ProfileFragment getInstance() {
        if (instance == null) return instance = new ProfileFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        doInitializeItems(view);
        doUserFetcher(view);
        doButtonListener();
        return view;
    }

    private void doGetComicData(final User user, final View view) {
        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    selectedUserComics.clear();
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        Comic comic = documentSnapshot.toObject(Comic.class);
                        if (comic.getUser_id().equals(user.getId())) {
                            selectedUserComics.add(comic);
                        }
                    }
                    comicProfileAdapter = new ComicProfileAdapter(view.getContext(), selectedUserComics);
                    recyclerView.setAdapter(comicProfileAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
                }
            }
        });
    }

    private void doUserFetcher(View view) {
        if (this.selectedUser == null) {
            usernameText.setText(UserSession.getCurrentUser().getUsername());
            roleText.setText(UserSession.getCurrentUser().getRole());
            if (UserSession.getCurrentUser().getRole().equals("Guest")) {
                avatarIcon.setImageResource(R.drawable.guest_avatar);
                imageGuest.setVisibility(View.VISIBLE);
                textGuest.setVisibility(View.VISIBLE);
                manageUserButton.setVisibility(View.GONE);
                uploadMaterialButton.setVisibility(View.GONE);
            }
            doGetComicData(UserSession.getCurrentUser(), view);
        } else {
            usernameText.setText(selectedUser.getUsername());
            roleText.setText(selectedUser.getRole());
            avatarIcon.setImageResource(R.drawable.admin_avatar);
            imageGuest.setVisibility(View.GONE);
            textGuest.setVisibility(View.GONE);
            editProfileButton.setVisibility(View.GONE);
            menuButton.setVisibility(View.GONE);
            doGetComicData(selectedUser, view);
        }
    }

    private void doButtonListener() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_userId", "");
                editor.apply();
                UserSession.setCurrentUser(null);
                startActivity(new Intent(getContext(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.setVisibility(View.GONE);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.setVisibility(View.VISIBLE);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
        manageUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManageUserActivity.class));
                menuItem.setVisibility(View.GONE);
            }
        });
        uploadMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadMaterialActivity.class));
                menuItem.setVisibility(View.GONE);
            }
        });
    }

    private void doInitializeItems(View view) {
        editProfileButton = view.findViewById(R.id.editProfileBtn);
        menuButton = view.findViewById(R.id.menuBtn);
        menuItem = view.findViewById(R.id.menuItem);
        logoutButton = view.findViewById(R.id.logoutBtn);
        usernameText = view.findViewById(R.id.usernameShow);
        roleText = view.findViewById(R.id.roleShow);
        avatarIcon = view.findViewById(R.id.avatarIcon);
        imageGuest = view.findViewById(R.id.imageNoneGuest);
        textGuest = view.findViewById(R.id.textNoneGuest);
        manageUserButton = view.findViewById(R.id.manageUserBtn);
        uploadMaterialButton = view.findViewById(R.id.uploadMaterialBtn);
        menuItem.setVisibility(View.GONE);
        imageGuest.setVisibility(View.GONE);
        textGuest.setVisibility(View.GONE);
        selectedUserComics = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recViewProfileComic);
        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static void setUsernameText(String editText) {
        usernameText.setText(editText);
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

}