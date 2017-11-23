package com.example.alex.servustech.activities.registerFlow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alex.servustech.utils.UserDAOImpl;
import com.example.alex.servustech.utils.UserDAO;
import com.example.alex.servustech.utils.IUserValidator;
import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.example.alex.servustech.model.User;
import com.example.alex.servustech.utils.UserValidator;
import com.example.alex.servustech.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RegisterActivity extends Activity implements RegisterContract.View {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Unbinder unbinder;

    @BindView(R.id.ed_email) EditText mEmail;
    @BindView(R.id.ed_password) EditText mPassword;
    @BindView(R.id.ed_confirm_password) EditText mConfirmPassword;

    private boolean mHasErrors;
    private RegisterContract.Presenter mSignUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        unbinder = ButterKnife.bind(this);
        mSignUpPresenter = new RegisterPresenter(new UserDAOImpl(getApplicationContext()), this);
        mSignUpPresenter.setUserValidator(new UserValidator());
    }


    @Override
    public void onDestroy(){
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
        // We do not need to save the picture, just to display it. So we don't retrieve it

        User user = new User(email, password);
//            validator.validate(user, repeatPassword);
        /* If no error has been thrown, we can proceed further and register the user */
//            userDAO.create(user);
        mSignUpPresenter.addUser(user, repeatPassword);
        /* After the user was successfully added, we open the next screen*/
        if (!mHasErrors) {
            startActivity(new Intent(this, MainScreenActivity.class));
            // After the user is registered, if he presses back, we do not need to take him back to
            // sign up again. We close this activity.
            finish();
        }

    }


    /* We open a new Intent with the action ACTION_IMAGE_CAPTURE to open the camera */
    @OnClick(R.id.iv_user_avatar)
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
            /* Save the data via thread
            * + add a loading bar */
        }
    }


    private void showPictureDoubled(Bitmap photo) {
        ImageView avatar = (ImageView) findViewById(R.id.iv_user_avatar);
        // We double the image's size to make fit better in page and look better
        avatar.setImageBitmap(Bitmap.createScaledBitmap(photo, photo.getWidth() * 2, photo.getHeight() * 2, false));
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
}
