package com.mackie.PhotoShare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    private ImageView imageView;

    private Photo photo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        imageView = (ImageView) findViewById(R.id.imageView);

        // Get intent
        Intent intent = getIntent();

        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            photo = new Photo(getApplicationContext(), intent);

            int width = imageView.getWidth();

            imageView.setImageBitmap(photo.decodePhoto(width, width));

            Log.i(TAG, "imageView width: " + imageView.getWidth());
            Log.i(TAG, "imageView height: " + imageView.getHeight());
        }
    }

}