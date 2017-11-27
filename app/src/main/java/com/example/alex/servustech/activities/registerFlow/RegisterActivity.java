package com.example.alex.servustech.activities.registerFlow;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private static String TAG = "MainActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 2;
    public static final String BROADCAST_KEY = "com.example.alex.servustech.BROADCAST_KEY";

    private Unbinder unbinder;

    @BindView(R.id.ed_email)
    EditText mEmail;
    @BindView(R.id.ed_password)
    EditText mPassword;
    @BindView(R.id.ed_confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.pb_while_saving_image)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_user_avatar)
    ImageView mUserAvatar;

    private boolean mHasErrors;
    private String mCurrentPhotoPath;
    private File mPhotoFile;

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

        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString("pathToPhoto");
            displayTheImage();
        } else {
            mUserAvatar.setImageResource(R.drawable.ic_account_box_black_48dp);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("pathToPhoto", mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when the user presses "Sign up".
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
        // First we check for permissions
        boolean weHavePermission = checkForPermissions();
        if (weHavePermission) {
            startCameraForResult();
        }
    }

    private void startCameraForResult() {
        File photoFile;
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getPackageManager()) == null) {
            // display a short message saying the camera is not available atm
            Toast.makeText(getApplicationContext(), getText(R.string.camera_not_available), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoFile = createImageFile();
        } catch (IOException ioe) {
            Toast.makeText(getApplicationContext(), getText(R.string.file_not_created), Toast.LENGTH_SHORT).show();
            return;
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
        }
    }


    private boolean checkForPermissions() {
        if (Build.VERSION.SDK_INT < 23) {
            // Permission is already granted by default when installing the app
            return true;
        }
        // If the version is equal or above 23, we have to deal with the runtimePermission;
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // We have the permission
            return true;
        }
        // No permission were acquired, ask the user for them
        // First show a small explanation
        askForStoragePermission();
        return false;
    }

    private void askForStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                REQUEST_STORAGE_PERMISSION);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mUserAvatar.setVisibility(View.INVISIBLE);  // Hide the avatar
            mProgressBar.setVisibility(View.VISIBLE);   // Show the loading bar;
            Log.d(ProcessImageTask.TAG, "Accessing new task + " + mCurrentPhotoPath);
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
        alertDialog.setTitle(getString(R.string.register_failed));
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
        String imageFileName = "JPEG_" + timeStamp;
        File appStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator +
                        getString(R.string.app_name));

        if (!appStorageDir.exists())
            appStorageDir.mkdir();

        File image = new File(appStorageDir.getAbsolutePath() + File.separator + imageFileName + ".jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCameraForResult();
                } else { // Permission is not accepted.
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle(R.string.warning_title);
                    alertDialog.setMessage(getString(R.string.permission_not_granted));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
