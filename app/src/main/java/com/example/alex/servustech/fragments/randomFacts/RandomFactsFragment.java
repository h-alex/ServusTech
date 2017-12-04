package com.example.alex.servustech.fragments.randomFacts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alex.servustech.R;
import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;

public class RandomFactsFragment extends Fragment{
    private String mTitle;

    public RandomFactsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.random_facts, container, false);
        if (getArguments() != null) {
            mTitle = getArguments().getString(MainScreenActivity.KEY_TO_FRAGMENT_TITLE);
            getActivity().setTitle(mTitle);
            Toast.makeText(getActivity().getApplicationContext(), mTitle, Toast.LENGTH_SHORT).show();
        }
        return root;
    }
}
