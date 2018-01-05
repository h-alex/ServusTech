package com.servustech.alex.servustech.activities.itemdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.fragments.recycleView.RecycleViewFragment;
import com.servustech.alex.servustech.model.category.ParcelableCategory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.iv_category_image)
    ImageView mIcon;
    @BindView(R.id.tv_category_id)
    TextView mId;
    @BindView(R.id.tv_category_name)
    TextView mCategoryName;
    @BindView(R.id.tv_number_of_subcategories)
    TextView mNumberOfSubcategories;

    @BindView(R.id.tb_category)
    Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();
        ParcelableCategory category = (ParcelableCategory) data.getParcelable(RecycleViewFragment.KEY_TO_PARCELABLE);
        displayData(category);
    }

    private void displayData(ParcelableCategory category) {
        setImageIfExist(category.getIconURL());
        mId.setText(category.getId());
        mCategoryName.setText(category.getName());
        String numberOfSub = Integer.toString(category.getNumberOfSubcategories());
        mNumberOfSubcategories.setText(numberOfSub);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    public void setImageIfExist(String iconURL) {
        if (!iconURL.isEmpty()) {
            Picasso.with(mIcon.getContext())
                    .load(iconURL)
                    .error(R.drawable.ic_broken_image_black_48dp)
                    .into(mIcon);
        } else {
            mIcon.setImageResource(R.drawable.ic_broken_image_black_48dp);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
