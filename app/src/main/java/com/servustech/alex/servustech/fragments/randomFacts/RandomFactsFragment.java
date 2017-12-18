package com.servustech.alex.servustech.fragments.randomFacts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;

public class RandomFactsFragment extends Fragment{
    private String mTitle;

    public RandomFactsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.random_facts, container, false);
        if (getArguments() != null) {
            mTitle = getArguments().getString(MainScreenActivity.FRAGMENT_TITLE_KEY);
            getActivity().setTitle(mTitle);
        }
        return root;
    }
}
