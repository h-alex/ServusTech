package com.example.alex.servustech.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.alex.servustech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProcessImageTask extends AsyncTask<Bitmap, Void, Void> {
    public static final int TARGET_SIZE = 100;

    @SuppressLint("StaticFieldLeak")
    @BindView(R.id.iv_user_avatar)
    ImageView mUserAvatar;
    @SuppressLint("StaticFieldLeak")
    @BindView(R.id.pb_while_saving_image)
    ProgressBar mSavingImageProgressBar;

    private Unbinder unbinder;

    public ProcessImageTask(Activity activity) {
        unbinder = ButterKnife.bind(this, activity);
    }

    @Override
    protected void onPreExecute() {
        // TODO Set the loading bar to visible and hide the avatar
        // Hide the Avatar
        mUserAvatar.setVisibility(View.INVISIBLE);
        // Show the ProgressBar
        mSavingImageProgressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        // Save the image in the device
        // TODO 1: Resize the image
        Bitmap resizedPicture = getResizedPicture(bitmaps[0]);
        // TODO 2: Save the image.
        saveImage(resizedPicture);
        // TODO 3: Display the image
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }


        return null;
    }

    private void saveImage(Bitmap resizedPicture) {

    }

    private Bitmap getResizedPicture(Bitmap bitmap) {
        float ratio = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        /* We calculate the ratio of the image to our desired size,
         * then we divide the other member by it. This way, the ratio is kept */
        if (height > width) {
            /* Portrait mode */
            ratio = (1.0f * height) / TARGET_SIZE;
            height = TARGET_SIZE;
            width /= ratio;
        } else {
            /* Landscape mode OR square picture*/
            ratio = (1.0f * width) / TARGET_SIZE;
            width = TARGET_SIZE;
            height /= ratio;
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        // TODO Display the photo taken by the user
        // TODO Hide the loading bar
        mSavingImageProgressBar.setVisibility(View.INVISIBLE);
        mUserAvatar.setVisibility(View.VISIBLE);

        unbinder.unbind();
    }
}
