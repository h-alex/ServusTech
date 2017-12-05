package com.example.alex.servustech.fragments.recycleView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.alex.servustech.R;
import com.example.alex.servustech.adapter.CustomAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecycleViewFragment extends Fragment {
    @BindView(R.id.recycle_view)
     RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private Unbinder mUnbinder;

    private static final int DATASET_LENGTH = 60;
    ArrayList<String> mDataSet;

    public RecycleViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        // We just load some data. This should usually come from DATABASE local or server type;
        // This should be done in BACKGROUND, as it will freeze the main Thread
        initDataSet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) { // the real shit
        // Get a view
        // So we have something to work with
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        // Bind the @Butterknife
        mUnbinder = ButterKnife.bind(this, rootView);

        // we set the type of layout we'll be using.
        // This tells our recycleView how to position the items in page.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new CustomAdapter(mDataSet));


        return rootView;
    }

    private void initDataSet() {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < DATASET_LENGTH; i++) {
            mDataSet.add("This is element #" + i);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
