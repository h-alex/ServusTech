package com.example.alex.servustech.activities.registerFlow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.alex.servustech.R;
import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.ProcessImageTask;
import com.example.alex.servustech.utils.UserDAOImpl;
import com.example.alex.servustech.utils.UserValidator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RegisterActivity extends Activity implements RegisterContract.View {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String BROADCAST_KEY = "com.example.alex.servustech.BROADCAST_KEY";

    private Unbinder unbinder;

    @BindView(R.id.ed_email) EditText mEmail;
    @BindView(R.id.ed_password) EditText mPassword;
    @BindView(R.id.ed_confirm_password) EditText mConfirmPassword;
    @BindView(R.id.pb_while_saving_image) ProgressBar mProgressBar;
    @BindView(R.id.iv_user_avatar) ImageView mUserAvatar;

    private boolean mHasErrors;
    private String mCurrentPhotoPath;

    private RegisterContract.Presenter mSignUpPresenter;

    private BroadcastReceiver imageSavedSuccessfully = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // When we received the signal, we display the image.
            // Everything's local so worry not about the data leak
            displayTheImage();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        unbinder = ButterKnife.bind(this);
        mSignUpPresenter = new RegisterPresenter(new UserDAOImpl(getApplicationContext()), this);
        mSignUpPresenter.setUserValidator(new UserValidator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the receiver.
        // This is called after the `onCreate` method and most of the time after the application is only partially visible
        LocalBroadcastManager.getInstance(this).registerReceiver(imageSavedSuccessfully, new IntentFilter(RegisterActivity.BROADCAST_KEY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the receiver so it won't possibly interfere with other receivers
        LocalBroadcastManager.getInstance(this).unregisterReceiver(imageSavedSuccessfully);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Called when the user presses "Signup".
     * If the credentials are correct, we register him.
     * Otherwise we show a Dialog with what is wrong
     */
    @OnClick(R.id.btn_register_user)
    public void registerUser() {
        mHasErrors = false;
        String email = ((EditText) findViewById(R.id.ed_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.ed_password)).getText().toString();
        String repeatPassword = ((EditText) findViewById(R.id.ed_confirm_password)).getText().toString();

        User user = new User(email, password);
        mSignUpPresenter.addUser(user, repeatPassword);
        if (!mHasErrors) {
            startActivity(new Intent(this, MainScreenActivity.class));
            finish();
        }

    }


    /* We open a new Intent with the action ACTION_IMAGE_CAPTURE to open the camera */
    @OnClick(R.id.iv_user_avatar)
    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                showErrors(getString(R.string.errors_taking_photo));
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mUserAvatar.setVisibility(View.INVISIBLE);  // Hide the avatar
            mProgressBar.setVisibility(View.VISIBLE);   // Show the loading bar;
            new ProcessImageTask(getApplicationContext()).execute(mCurrentPhotoPath);
        }
    }


    private void displayTheImage() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888; // we make sure the alpha channel is kept ok.
        Bitmap avatar = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mUserAvatar.setImageBitmap(avatar);

        // Hide the progress bar & show the avatar
        mUserAvatar.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    /* Contract */
    @Override
    public void showErrors(String errors) {
        mHasErrors = true;
        createDialog(errors);
    }

    /* Shows a dialog with only one button*/
    private void createDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setTitle("Cannot signup!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    /* Create a file where our image will be stored into*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
