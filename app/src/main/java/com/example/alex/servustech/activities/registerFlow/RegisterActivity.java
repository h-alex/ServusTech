package com.example.alex.servustech.activities.registerFlow;

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

import com.example.alex.servustech.utils.ProcessImageTask;
import com.example.alex.servustech.utils.UserDAOImpl;
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
        mSignUpPresenter.addUser(user, repeatPassword);
        if (!mHasErrors) {
            startActivity(new Intent(this, MainScreenActivity.class));
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


    /* If we want to resize the picture, we first have to save it.
     * This way, we save the picture, then we send the path to it to ProcessImageTask which will
      * read it from there, resize it, and then save it again.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            showPictureDoubled((Bitmap) extras.get("data"));
            new ProcessImageTask(this).execute((Bitmap)extras.get("data"));
            /* Save the data via thread
            * + add a loading bar */
        }
    }


    private void showPictureDoubled(Bitmap photo) {
        ImageView avatar = (ImageView) findViewById(R.id.iv_user_avatar);
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
