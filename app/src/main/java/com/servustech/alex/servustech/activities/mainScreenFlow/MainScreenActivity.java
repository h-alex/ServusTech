package com.servustech.alex.servustech.activities.mainScreenFlow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.fragments.details.DetailsFragment;
import com.servustech.alex.servustech.fragments.googlemaps.GoogleMapsFragment;
import com.servustech.alex.servustech.fragments.news.NewsFragment;
import com.servustech.alex.servustech.fragments.recycleView.RecycleViewFragment;
import com.servustech.alex.servustech.utils.firebase.InstanceIdService;
import com.servustech.alex.servustech.utils.firebase.MessageReceiver;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainScreenActivity extends AppCompatActivity {
    private static final String TAG = "MainScreenActivity";

    private static final int DEFAULT_FRAGMENT_POSITION = R.id.drawer_credentials;
    private static final int DEFAULT_TITLE = R.string.credentials;
    private static final String FRAGMENT_NUMBER_KEY = "current_fragment_position";
    public static final String FRAGMENT_TITLE_KEY = "fragment_title";
    private static final int DEFAULT_FRAGMENT_FROM_NOTIFICATION = R.string.drawer_news;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* To detect when we create the Activity for the first time,
         * so we won't be changing the fragment twice */
        if (savedInstance == null) {
            mCurrentTitle = getResources().getString(DEFAULT_TITLE);
            changeFragment(DEFAULT_FRAGMENT_POSITION);
        }
        /* To detect when we are calling the activity from a Notification */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            putBundleStartFragment(bundle);
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


    @Override
    protected void onNewIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            putBundleStartFragment(bundle);
        }
        super.onNewIntent(intent);
    }


    private void putBundleStartFragment(Bundle bundle) {
        NewsFragment newsFragment = new NewsFragment();
        bundle.putString(FRAGMENT_TITLE_KEY,
                getResources().getString(DEFAULT_FRAGMENT_FROM_NOTIFICATION));
        newsFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, newsFragment)
                .commit();
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
            case R.id.drawer_news:
                return new NewsFragment();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}