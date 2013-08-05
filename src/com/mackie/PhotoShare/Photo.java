package com.mackie.PhotoShare;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.graphics.Matrix;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hubert
 * Date: 29.07.13
 * Time: 15:46
 */

public class Photo {
    public static final String TAG = "Photo";
    Uri imageUri;
    String filePath;
    ExifInterface exif;

    /*private enum Orientation {
        // values according to EXIF
        HORIZONTAL(1), VERTICAL(6);

        private int value;

        private Orientation(int v) {
            value = v;
        }
    }

    Orientation orientation = null;*/

    int orientation;

    public Photo(Context context, Intent intent) {
        imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        filePath = getRealPathFromURI(context, imageUri);

        Log.i(TAG, "Photo file path: " + filePath);

        orientation = getOrientationFromURI(context, imageUri);

        Log.i(TAG, "Photo orientation: " + orientation);

        /*try {
            exif = new ExifInterface(filePath);
        }
        catch(IOException e) {
             // TO-DO: Catch the IO exception
            Log.e(TAG, "IO exception while opening file: " + filePath);
        }*/

        /*switch(Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION))) {
            case 1:
                orientation = Orientation.HORIZONTAL;
                break;
            case 6:
                orientation = Orientation.VERTICAL;
                break;
        }*/
    }

    public Bitmap decodePhoto(int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //return BitmapFactory.decodeFile(filePath, options);

        // TODO: Do we need to create this bitmap?
        Bitmap inBitmap = BitmapFactory.decodeFile(filePath, options);

        // Rotate image if needed
        Matrix matrix = new Matrix();
        if (orientation == 90) {
            matrix.postRotate(90);
        }
        else if (orientation == 180) {
            matrix.postRotate(180);
        }
        else if (orientation == 270) {
            matrix.postRotate(270);
        }

        return Bitmap.createBitmap(inBitmap, 0, 0, inBitmap.getWidth(), inBitmap.getHeight(), matrix, true);
    }

    /*public boolean isHorizontal() {
        return (orientation == Orientation.HORIZONTAL);
    }

    public boolean isVertical() {
        return (orientation == Orientation.VERTICAL);
    }*/

    private String getRealPathFromURI(Context context, Uri photoUri) {
        // import android.support.v4.content.CursorLoader; import this for CursorLoader
        CursorLoader loader = new CursorLoader(context, photoUri,
                   new String[] { MediaStore.Images.Media.DATA }, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private int getOrientationFromURI(Context context, Uri photoUri) {
        // import android.support.v4.content.CursorLoader; import this for CursorLoader
        CursorLoader loader = new CursorLoader(context, photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
        cursor.moveToFirst();
        return cursor.getInt(column_index);
    }

    // If the image is smaller than requested picture size it will be displayed smaller as well
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            //inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            inSampleSize = widthRatio;
        }

        Log.i(TAG, "Photo inSampleSize: " + inSampleSize);

        return inSampleSize;
    }
}
