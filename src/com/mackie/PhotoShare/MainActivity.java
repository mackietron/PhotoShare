package com.mackie.PhotoShare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    private MyImageView myImageView;
    private Photo photo = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myImageView = (MyImageView) findViewById(R.id.myImageView);
        // change view bounds so they fit an image
        myImageView.setAdjustViewBounds(true);


    }

    @Override
    public void onResume() {
        super.onResume();
        // Get intent
        Intent intent = getIntent();

        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            photo = new Photo(getApplicationContext(), intent);
            myImageView.setPhoto(photo);
        }
    }

}