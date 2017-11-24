package com.example.alex.servustech.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.alex.servustech.MainActivity;
import com.example.alex.servustech.R;
import com.example.alex.servustech.activities.registerFlow.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class ProcessImageTask extends AsyncTask<String, Void, Void> {
    public static final String TAG = "ProcessImageTask";



    private Bitmap image;
    private Context mContext;

    public ProcessImageTask(Context context) {
        mContext = context;
    }


    @Override
    protected Void doInBackground(String... pathToBitmap) {
        String mCurrentPhotoPath = pathToBitmap[0];

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap avatar = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Log.d(TAG, "Path to the image: " + mCurrentPhotoPath);
        // We resize the picture to our desired sized
        image = getResizedPicture(avatar);

        saveImage(mCurrentPhotoPath);

        // A little delay for the progress bar
        for (int i = 0; i < 1; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }


        return null;
    }

    private void saveImage(String path) {
        File pictureFile = new File(path);
        if (pictureFile == null) {
            // Throw an error here saying you can't save the image. Maybe not enough space.
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.d(TAG, "Successful!");
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }

    }

    private Bitmap getResizedPicture(Bitmap bitmap) {
        float ratio = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int imgRatio = (int) mContext.getResources().getDimension(R.dimen.avatar_target_size);
        /* We calculate the ratio of the image to our desired size,
         * then we divide the other member by it. This way, the ratio is kept */
        if (height > width) {
            /* Portrait mode */
            ratio = (1.0f * height) / imgRatio;
            height = imgRatio;
            width /= ratio;
        } else {
            /* Landscape mode OR square picture*/
            ratio = (1.0f * width) / imgRatio;
            width = imgRatio;
            height /= ratio;
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        /* Send the signal to say that our image has been successfully processed */
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(RegisterActivity.BROADCAST_KEY));
    }
}
