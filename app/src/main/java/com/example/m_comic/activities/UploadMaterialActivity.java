package com.example.m_comic.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.m_comic.R;
import com.example.m_comic.activities.adapters.ComicUploadAdapter;
import com.example.m_comic.animations.LoadingAnimation;
import com.example.m_comic.authentications.SingletonFirebaseTool;
import com.example.m_comic.authentications.UserSession;
import com.example.m_comic.models.Comic;
import com.example.m_comic.models.ComicDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UploadMaterialActivity extends AppCompatActivity {

    private EditText comicName;
    private RecyclerView imageRec;
    private ImageView mainImage;
    private Button chooseFileBtn, addComicBtn, noButton, yesButton, noSoundButton, yesSoundButton, proceedButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_AUDIO_REQUEST = 2;
    private Uri mImageUri, mAudioUri;
    private boolean isAbleToAdd;
    private String tempComicId = "";
    private ArrayList<ComicDetail> tempComicDetail = null;
    private Comic tempComic;
    private StorageTask mUploadTask;
    private ConstraintLayout modalAddAgain, modalAddSound, modalProceed;
    private ScrollView scrollView;
    private String tempComicFolder;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_material);
        doInitializeItems();
        doButtonListener();
    }

    private void doButtonListener() {
        chooseFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        addComicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadMaterialActivity.this, R.string.uploadProgress, Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                comicName.setText("");
                mainImage.setImageResource(0);
                scrollView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                modalAddAgain.setVisibility(View.GONE);
            }
        });

        noSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalAddAgain.setVisibility(View.VISIBLE);
                modalAddSound.setVisibility(View.GONE);
            }
        });

        yesSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAudioChooser();
                modalProceed.setVisibility(View.VISIBLE);
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadSoundFile();
                modalProceed.setVisibility(View.GONE);
                modalAddAgain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void uploadSoundFile() {
        LoadingAnimation.startLoading(UploadMaterialActivity.this);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoadingAnimation.getDialog().dismiss();
            }
        }, 4000);
        modalAddSound.setVisibility(View.GONE);
        final StorageReference fileReference = SingletonFirebaseTool.getInstance().getMyStorageReference()
                .child(tempComicFolder)
                .child(System.currentTimeMillis() + "." + getFileExtension(mAudioUri));
        fileReference.putFile(mAudioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UploadMaterialActivity.this, R.string.uploadSoundSuccess, Toast.LENGTH_SHORT).show();
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        tempComicDetail.get(tempComicDetail.size() - 1).setSound(uri.toString());
                        SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics")
                                .document(tempComicId).set(tempComic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadMaterialActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {}
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void uploadFile() {
        if (comicName.getText().toString().trim().isEmpty()) {
            Toast.makeText(UploadMaterialActivity.this, R.string.comicNameFill, Toast.LENGTH_SHORT).show();
        } else if (!isAbleToAdd) {
            Toast.makeText(UploadMaterialActivity.this, R.string.fillComicAdd, Toast.LENGTH_SHORT).show();
        } else {
            LoadingAnimation.startLoading(UploadMaterialActivity.this);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LoadingAnimation.getDialog().dismiss();
                }
            }, 4000);
            modalAddSound.setVisibility(View.VISIBLE);
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            tempComicFolder = System.currentTimeMillis() + "";
            final StorageReference fileReference = SingletonFirebaseTool.getInstance().getMyStorageReference()
                    .child(tempComicFolder)
                    .child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadMaterialActivity.this, R.string.uploadSuccess, Toast.LENGTH_SHORT).show();
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                            if (tempComicId.isEmpty()) {
                                tempComic = new Comic();
                                SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics").add(tempComic)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                tempComicId = documentReference.getId();
                                                tempComic.setId(tempComicId);
                                                tempComic.setUser_id(UserSession.getCurrentUser().getId());
                                                ComicDetail newComicDetail = new ComicDetail();
                                                newComicDetail.setImage(uri.toString());
                                                newComicDetail.setName(comicName.getText().toString());
                                                newComicDetail.setSound("No Sound");
                                                tempComicDetail.add(newComicDetail);
                                                tempComic.setComic_details(tempComicDetail);
                                                SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics")
                                                        .document(tempComicId).set(tempComic);
                                            }
                                        });
                            } else {
                                ComicDetail newComicDetail = new ComicDetail();
                                newComicDetail.setImage(uri.toString());
                                newComicDetail.setName(comicName.getText().toString());
                                newComicDetail.setSound("No Sound");
                                tempComicDetail.add(newComicDetail);
                                tempComic.setComic_details(tempComicDetail);
                                SingletonFirebaseTool.getInstance().getMyFireStoreReference().collection("comics")
                                        .document(tempComicId).set(tempComic);
                            }
                            ComicUploadAdapter comicUploadAdapter = new ComicUploadAdapter(ctx, tempComicDetail);
                            imageRec.setAdapter(comicUploadAdapter);
                            imageRec.setLayoutManager(new LinearLayoutManager(ctx));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadMaterialActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {}
            });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openAudioChooser() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_AUDIO_REQUEST);
    }

    private void doInitializeItems() {
        comicName = findViewById(R.id.comic_name_text);
        imageRec = findViewById(R.id.recViewImage);
        mainImage = findViewById(R.id.mainImageId);
        chooseFileBtn = findViewById(R.id.chooseFileButton);
        addComicBtn = findViewById(R.id.addComic);
        isAbleToAdd = false;
        tempComicDetail = new ArrayList<>();
        modalAddAgain = findViewById(R.id.modalAddAgain);
        modalAddAgain.setVisibility(View.GONE);
        noButton = findViewById(R.id.noButton);
        yesButton = findViewById(R.id.yesButton);
        scrollView = findViewById(R.id.scrollView2);
        modalAddSound = findViewById(R.id.modalSoundAgain);
        modalAddSound.setVisibility(View.GONE);
        noSoundButton = findViewById(R.id.noSoundButton);
        yesSoundButton = findViewById(R.id.yesSoundButton);
        modalProceed = findViewById(R.id.modalProceedAgain);
        proceedButton = findViewById(R.id.proceedButton);
        modalProceed.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            mainImage.setImageURI(mImageUri);
            isAbleToAdd = true;
        }
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mAudioUri = data.getData();
            isAbleToAdd = true;
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}