package com.example.alex.servustech.activities.mainScreenFlow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alex.servustech.R;
import com.example.alex.servustech.fragments.randomFacts.RandomFactsFragment;
import com.example.alex.servustech.fragments.details.DetailsFragment;
import com.example.alex.servustech.utils.UserDAOImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainScreenActivity extends AppCompatActivity {
    private static final int DEFAULT_FRAGMENT_POSITION = 0;
    public static final String KEY_TO_FRAGMENT_TITLE = "fragment_title";

    private Unbinder mUnbinder;
    private MainScreenContract.Presenter mPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_list)
    ListView mDrawerList;

    private String[] mDrawerOptions;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_drawer);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new MainScreenPresenter();
        mPresenter.setDAO(new UserDAOImpl(getApplicationContext()));

        mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        // Set the adapter: context, How should the objects be displayed, what objects should be displayed
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerOptions));
        // Listener for the click events
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                changeFragment(position); // change the fragment
                mDrawerLayout.closeDrawers(); // close the drawer
            }
        });

        // we set the default fragment
        changeFragment(DEFAULT_FRAGMENT_POSITION);
    }

    private void changeFragment(int position) {
        Fragment fragment = getSelectedFragment(mDrawerOptions[position]);
        Bundle args = new Bundle();
        args.putString(KEY_TO_FRAGMENT_TITLE, mDrawerOptions[position]);
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private Fragment getSelectedFragment(String fragmentName) {
        switch (fragmentName) {
            case "Random fact":
                return new RandomFactsFragment();
            default: // set here the presenter, etc
                DetailsFragment fragment = new DetailsFragment();
                mPresenter.setView(fragment);
                fragment.setPresenter(mPresenter);
                return fragment;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}