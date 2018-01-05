package com.servustech.alex.servustech.fragments.news;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.servustech.alex.servustech.utils.firebase.MessageReceiver;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends Fragment{
    public static final String KEY_TO_UPDATE = "key_to_update";

    private Unbinder mUnbinder;
    @BindView(R.id.tv_random_fact_title)
    protected TextView mTitle;
    @BindView(R.id.tv_random_fact_body)
    protected TextView mBody;


    public NewsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.random_facts, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        if (getArguments() != null) {
            String title = getArguments().getString(MainScreenActivity.FRAGMENT_TITLE_KEY);
            getActivity().setTitle(title);

            Serializable serializedData = getArguments().getSerializable(MessageReceiver.KEY_TO_DATA);
            if (serializedData != null) {
                Map<String, String> data = (Map<String,String>)serializedData;
                mTitle.setText(data.get(MessageReceiver.TITLE_KEY));
                mBody.setText(data.get(MessageReceiver.BODY_KEY));
            }

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
