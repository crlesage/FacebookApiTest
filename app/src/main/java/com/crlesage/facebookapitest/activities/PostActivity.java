package com.crlesage.facebookapitest.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.crlesage.facebookapitest.R;
import com.crlesage.facebookapitest.dataModels.Post;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 2/19/2016.
 * A more descriptive and larger view of an individual post.
 */
public class PostActivity extends AppCompatActivity {

    @BindView(R.id.post_icon)
    protected ImageView mPost_icon;
    @BindView(R.id.post_title)
    protected TextView mPost_title;
    @BindView(R.id.post_time)
    protected TextView mPost_time;
    @BindView(R.id.post_description)
    protected TextView mPost_description;
    @BindView(R.id.post_image)
    protected ImageView mPost_image;

    private String mProfileIcon;
    private Post mPost;
    Activity mActivity = this;
    public Typeface robotoFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        // Add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialize and set fonts to text
        initializeTypeFace();

        // Grab photo data that was clicked on
        getIntentData();

        // Set up the post's images and texts
        setUpPostUI();
    }

    // Override the "home" back button to go back to timeline
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // Get the data from the intent from the certain post clicked
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        mPost = (Post) data.getParcelable("post");
        mProfileIcon = (String) data.get("profileIcon");
    }

    // Initialize Typeface
    public void initializeTypeFace() {
        AssetManager am = getApplicationContext().getApplicationContext().getAssets();
        robotoFont = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Roboto-Regular.ttf"));

        mPost_description.setTypeface(robotoFont);
        mPost_time.setTypeface(robotoFont);
        mPost_title.setTypeface(robotoFont);
    }

    // Set up the post detail UI from the intent's data
    private void setUpPostUI() {
        mPost_title.setText(mPost.getFrom().getName());
        mPost_time.setText(mPost.getCreated_time());
        mPost_description.setText(mPost.getName());
        Picasso.with(mActivity.getApplicationContext())
                .load(mPost.getSource())
                .fit().centerCrop()
                .into(mPost_image);
        Picasso.with(mActivity.getApplicationContext())
                .load(mProfileIcon)
                .fit().centerCrop()
                .into(mPost_icon);
    }
}
