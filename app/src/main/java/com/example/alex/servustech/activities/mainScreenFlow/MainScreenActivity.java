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
import com.example.alex.servustech.fragments.details.DetailsFragment;
import com.example.alex.servustech.fragments.randomFacts.RandomFactsFragment;
import com.example.alex.servustech.fragments.recycleView.RecycleViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainScreenActivity extends AppCompatActivity {
    private static final int DEFAULT_FRAGMENT_POSITION = 0;
    private static final String FRAGMENT_NUMBER_KEY = "current_fragment_position";
    public static final String FRAGMENT_TITLE_KEY = "fragment_title";

    private Unbinder mUnbinder;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_list)
    ListView mDrawerList;

    private String[] mDrawerOptions;
    private int mSelectedFragment;



    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main_drawer);
        mUnbinder = ButterKnife.bind(this);

        mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mDrawerOptions));

        // Listener for the click events
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                changeFragment(position); // change the fragment
                mDrawerLayout.closeDrawers(); // close the drawer
            }
        });

        // If there is no saved instance, we display the default fragment
        if (savedInstance == null) {
            changeFragment(DEFAULT_FRAGMENT_POSITION);
        } else { // We have a position previously saved
            mSelectedFragment = savedInstance.getInt(FRAGMENT_NUMBER_KEY);
            changeFragment(mSelectedFragment);
        }
    }



    private void changeFragment(int position) {
        mSelectedFragment = position;
        Fragment fragment = getSelectedFragment(mDrawerOptions[position]);
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TITLE_KEY, mDrawerOptions[position]);
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
            case "Recycle View":
                return new RecycleViewFragment();
            default:
                return  new DetailsFragment();
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
        outState.putInt(FRAGMENT_NUMBER_KEY, mSelectedFragment);
    }

}