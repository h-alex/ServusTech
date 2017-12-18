package com.servustech.alex.servustech.activities.mainScreenFlow;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.fragments.details.DetailsFragment;
import com.servustech.alex.servustech.fragments.googlemaps.GoogleMapsFragment;
import com.servustech.alex.servustech.fragments.randomFacts.RandomFactsFragment;
import com.servustech.alex.servustech.fragments.recycleView.RecycleViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainScreenActivity extends AppCompatActivity {
    private static final int DEFAULT_FRAGMENT_POSITION = R.id.drawer_credentials;
    private static final int DEFAULT_TITLE = R.string.credentials;
    private static final String FRAGMENT_NUMBER_KEY = "current_fragment_position";
    public static final String FRAGMENT_TITLE_KEY = "fragment_title";

    private Unbinder mUnbinder;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private String mCurrentTitle;
    private int mSelectedFragment;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_drawer);
        mUnbinder = ButterKnife.bind(this);
        mNavigationView.setNavigationItemSelectedListener(setupListener());

        if (savedInstance == null) {
            mCurrentTitle = getResources().getString(DEFAULT_TITLE);
            changeFragment(DEFAULT_FRAGMENT_POSITION);
        }
    }

    private NavigationView.OnNavigationItemSelectedListener setupListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mCurrentTitle = item.getTitle().toString();
                changeFragment(item.getItemId());
                mDrawerLayout.closeDrawers();
                return true;
            }
        };
    }


    private void changeFragment(int id) {
        mSelectedFragment = id;
        Fragment fragment = getSelectedFragment(id);
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE_KEY, mCurrentTitle);
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }


    private Fragment getSelectedFragment(int id) {
        switch (id) {
            case R.id.drawer_random_fact:
                return new RandomFactsFragment();
            case R.id.drawer_categories:
                return RecycleViewFragment.newInstance();
            case R.id.drawer_map:
                return new GoogleMapsFragment();
            default:
                return new DetailsFragment();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* even if we do not need to remember the number,
         we must put something in the bundle so it won't be null */
        outState.putInt(FRAGMENT_NUMBER_KEY, mSelectedFragment);
    }

}