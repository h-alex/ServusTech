package com.example.alex.servustech.fragments.recycleView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alex.servustech.R;
import com.example.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.example.alex.servustech.adapter.CustomAdapter;
import com.example.alex.servustech.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecycleViewFragment extends Fragment implements RecycleViewContract.View {
    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.recycle_view_loading_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recycle_view_helping_text_view)
    TextView mTextView;

    private Unbinder mUnbinder;

    RecycleViewContract.Presenter mPresenter;

    public RecycleViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);

        // Bind the @Butterknife
        mUnbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            String title = getArguments().getString(MainScreenActivity.KEY_TO_FRAGMENT_TITLE);
            getActivity().setTitle(title);
        }

        mPresenter = new RecycleViewPresenter(this);
        mPresenter.requestResults();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void setPresenter(RecycleViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSuccess(List<Category> results) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new CustomAdapter(results));
        hideElements();
    }

    @Override
    public void onFailure(RecycleViewContract.OnFailure failure) {
        mProgressBar.setVisibility(View.INVISIBLE);
        switch (failure) {
            case LIST_EMPTY:
                mTextView.setText(R.string.null_list);
                break;
            case NO_INTERNET_CONNECTION:
                mTextView.setText(R.string.no_internet_connection);
                break;
        }
    }

    private void hideElements() {
        mTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }
}