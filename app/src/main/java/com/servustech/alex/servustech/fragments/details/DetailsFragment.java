package com.servustech.alex.servustech.fragments.details;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.servustech.alex.servustech.MainActivity;
import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.servustech.alex.servustech.model.User;
import com.servustech.alex.servustech.utils.UserDAO;
import com.servustech.alex.servustech.utils.UserDAOImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsFragment extends Fragment implements DetailsContract.View {
    private DetailsContract.Presenter mPresenter;
    private Unbinder mUnbinder;

    @BindView(R.id.tv_user_email)
    TextView mUserEmail;
    @BindView(R.id.tv_user_password)
    TextView mUserPassword;


    private String mTitle;

    public DetailsFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main_screen, container, false);

        mPresenter = new DetailsPresenter(this, new UserDAOImpl(getActivity().getApplicationContext()));
        mPresenter.setView(this);

        mUnbinder = ButterKnife.bind(this, root);
        if (getArguments() != null) {
            mTitle = getArguments().getString(MainScreenActivity.FRAGMENT_TITLE_KEY);
            getActivity().setTitle(mTitle);
        }
        mPresenter.getCredentials();
        return root;
    }



    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }



    @Override
    public void showCredentials(User user) {
        mUserEmail.setText(user.getEmail());
        mUserPassword.setText(user.getPassword());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }



    /* TODO DELETE ME!
* I'm used for testing only! */
    @OnClick(R.id.button_delete_me)
    public void deleteMe() {

        UserDAO userDAO = new UserDAOImpl(getActivity().getApplicationContext());
        userDAO.delete(null);
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

}
