package com.mackie.PhotoShare;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: hubert
 * Date: 30.07.13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class MyImageView extends ImageView {
    public static final String TAG = "MyImageView";
    private int width;
    private int height;
    private Photo photo;

    /**
     * Constructor.  This version is only needed if you will be instantiating
     * the object manually (not from a layout XML file).
     * @param context
     */
    public MyImageView(Context context) {
        super(context);
    }

    /**
     * Construct object, initializing with any attributes we understand from a
     * layout file. These attributes are defined in
     * SDK/assets/res/any/classes.xml.
     *
     * @see android.view.View#View(android.content.Context, android.util.AttributeSet)
     */
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPhoto(Photo p) {
        photo = p;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // onSizeChanged is invoked twice: first time with old 0 sizes, second time when bitmap is set
        if((oldh == 0) && (oldw == 0)) {
            width = w;
            // TODO: comment next line to use UI editor (why?)
            //setImageBitmap(photo.decodePhoto(width, width));
            Log.i(TAG, "onSizeChanged width: " + width);
        } else {
            height = h;
        }
    }
//
//    /*public int getMyImageViewWidth() {
//        return width;
//    }
//
//    public int getMyImageViewHeight() {
//        return height;
//    }*/
}
