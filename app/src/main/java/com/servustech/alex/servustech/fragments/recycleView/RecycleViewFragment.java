package com.servustech.alex.servustech.fragments.recycleView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.itemdetails.CategoryActivity;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;
import com.servustech.alex.servustech.adapter.CustomAdapter;
import com.servustech.alex.servustech.model.category.Category;
import com.servustech.alex.servustech.model.category.ParcelableCategory;
import com.servustech.alex.servustech.model.interfaces.OnRecycleViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecycleViewFragment extends Fragment implements RecycleViewContract.View, OnRecycleViewClick {
    private static final String TAG = RecycleViewFragment.class.getSimpleName();
    public static final String KEY_TO_PARCELABLE = "key_to_parcelable";

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycle_view_loading_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.recycle_view_helping_text_view)
    TextView mTextView;
    @BindView(R.id.recycle_view_refresher)
    SwipeRefreshLayout mRefreshLayout;

    private Unbinder mUnbinder;

    private RecycleViewContract.Presenter mPresenter;
    private CustomAdapter mAdapter;

    public static RecycleViewFragment newInstance() {

        Bundle args = new Bundle();

        RecycleViewFragment fragment = new RecycleViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RecycleViewFragment() {}



    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        // Bind the @ButterKnife
        mUnbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            String title = getArguments().getString(MainScreenActivity.FRAGMENT_TITLE_KEY);
            getActivity().setTitle(title);
        }

        mPresenter = new RecycleViewPresenter(this);
        mPresenter.requestResults();
        mRefreshLayout.setOnRefreshListener(getSwipeToRefreshListener());
        mAdapter = new CustomAdapter(new ArrayList<Category>());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.setClickListener(this);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(RecycleViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSuccess(List<Category> results) {
            mAdapter.replaceAll(results);
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
        mRefreshLayout.setRefreshing(false);
    }


    private SwipeRefreshLayout.OnRefreshListener getSwipeToRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestResults();
            }
        };
    }

    @Override
    public void onRecycleViewClick(int i) {
        Intent categoryPage = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
        categoryPage.putExtra(KEY_TO_PARCELABLE, new ParcelableCategory(mAdapter.getItemAt(i)));
        startActivity(categoryPage);
    }
}
