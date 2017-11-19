package com.example.alex.servustech.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alex.servustech.Exceptions.InvalidCredentials;
import com.example.alex.servustech.R;


public class SignUpActivity extends Activity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    IUserValidator validator;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validator = new UserValidator();
        userDAO = new SharedPreferencesAccessor(getApplicationContext());
        setContentView(R.layout.activity_sign_up_screen);
    }


    /**
     * Called when the user presses "Signup".
     * If the credentials are correct, we register him.
     * Otherwise we show a Dialog with what is wrong
     *
     * @param view
     */
    public void signUpUser(View view) {

        String email = ((EditText) findViewById(R.id.ed_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.ed_password)).getText().toString();
        String repeatPassword = ((EditText) findViewById(R.id.ed_confirm_password)).getText().toString();
        // We do not need to save the picture, just to display it. So we don't retrieve it

        User user = new User(email, password);
        try {
            validator.validate(user, repeatPassword);
            /* If no error has been thrown, we can proceed further and register the user */
            userDAO.create(user);

            /* After the user was successfully added, we open the next screen*/
            startActivity(new Intent(this, MainScreenActivity.class));
            // After the user is registered, if he presses back, we do not need to take him back to
            // sign up again. We close this activity.
            finish();
        } catch (InvalidCredentials ic) {
            /* If an error of type "InvalidCredentials" occurs, we show it */
            createDialog(ic.getMessage());
        }
    }


    /* We open a new Intent with the action ACTION_IMAGE_CAPTURE to open the camera */
    public void takePhoto(View view) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
        }
    }


    /* After the user takes a photo, this function will be called.
    * If the requestCode is the one we sent and the resultCode is OK, then we
    * successfully captured a picture. We display it*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            showPictureDoubled((Bitmap) extras.get("data"));
        }
    }


    /* Shows a dialog with only one button*/
    private void createDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
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

    private void showPictureDoubled(Bitmap photo) {
        ImageView avatar = (ImageView) findViewById(R.id.iv_user_avatar);
        // We double the image's size to make fit better in page and look better
        avatar.setImageBitmap(Bitmap.createScaledBitmap(photo, photo.getWidth() * 2, photo.getHeight() * 2, false));
    }
}
