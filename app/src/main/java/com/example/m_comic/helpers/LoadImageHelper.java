package com.example.m_comic.helpers;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;

public class LoadImageHelper extends AsyncTask<String, Void, Bitmap> {


    @SuppressLint("StaticFieldLeak")
    private ImageView image;
    private Bitmap retBitmap;

    public LoadImageHelper (ImageView image){
        this.image  = image;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
        retBitmap = bitmap;
    }

}
