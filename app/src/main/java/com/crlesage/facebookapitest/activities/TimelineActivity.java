package com.crlesage.facebookapitest.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crlesage.facebookapitest.R;
import com.crlesage.facebookapitest.adapters.TimelineAdapter;
import com.crlesage.facebookapitest.customUI.SimpleDividerItemDecoration;
import com.crlesage.facebookapitest.dataModels.Feed;
import com.crlesage.facebookapitest.interfaces.ApiInterface;
import com.crlesage.facebookapitest.interfaces.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chris on 2/19/2016.
 * A feed of posts from a particular event page from Facebook.
 */
public class TimelineActivity extends AppCompatActivity {

    private static final String TAG = "FacebookApi";
    @BindView(R.id.recyclerViewTimeline)
    protected RecyclerView mRecyclerViewTimeline;

    private String mProfileIcon;
    private  Feed mFeed;
    protected TimelineAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    Activity mActivity = this;
    public Typeface robotoFont;

    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Bind views
        ButterKnife.bind(this);

        // Set up recycler view
        setUpRecyclerView();

        // Start grabbing data from Facebook
        getProfileIcon();
    }

    private void setUpRecyclerView(){
        // Use a Linear layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewTimeline.setLayoutManager(mLayoutManager);

        // Initialize Adapter for recycler view
        mAdapter = new TimelineAdapter(mActivity);

        // Attach adapter to recycler view
        mRecyclerViewTimeline.setAdapter(mAdapter);

        // Add line between posts via item decoration
        mRecyclerViewTimeline.addItemDecoration(new SimpleDividerItemDecoration(this));

        // Add endless scrolling to recycler view (detect when needing to fetch more data from
        // pagination
        addEndListScrolling();
    }

    // Got code from:
    // http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
    private void addEndListScrolling(){
        mRecyclerViewTimeline.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            //Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            if(mFeed.getPaging() == null){
                                return;
                            }
                            if(mFeed.getPaging().getNext() == null){
                                return;
                            }
                            if(mFeed.getPaging().getNext() != null || !mFeed.getPaging().getNext().equals("")) {
                                getFacebookFeedNewPage();
                            }
                        }
                    }
                }
            }
        });
    }

    // Retrofit call to get profile picture icon
    public void getProfileIcon() {
        ApiInterface apiService = ApiInterface.retrofit.create(ApiInterface.class);
        Call<Feed> call = apiService.getProfilePicture(Constants.eventId, Constants.accessToken);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                // Response is successful, read in feed to get profile picture
                if (response.isSuccess()) {
                    Feed feed = response.body();
                    mProfileIcon = feed.getData().get(0).getPicture();

                    getFacebookFeed();
                } else {
                    int statusCode = response.code();
                    // Handle Error
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                // Handle execution failures like no internet connectivity
            }
        });
    }

    // Retrofit call to get Feed from Facebook Page
    public void getFacebookFeed() {
        ApiInterface apiService = ApiInterface.retrofit.create(ApiInterface.class);
        Call<Feed> call = apiService.getFacebookFeed(Constants.eventId, Constants.limit, Constants.type, Constants.accessToken);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                // Response is successful, read in feed to get posts
                if (response.isSuccess()) {
                    Feed feed = response.body();
                    mFeed = feed;

                    // Add the data to the adapter to populate the recycler view
                    mAdapter.addFeed(mFeed.getData(), mProfileIcon);
                } else {
                    int statusCode = response.code();
                    // Handle Error
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                // Handle execution failures like no internet connectivity
            }
        });
    }

    // Retrofit call to get Feed from Facebook Page (From a new page of data)
    public void getFacebookFeedNewPage() {
        ApiInterface apiService = ApiInterface.retrofit.create(ApiInterface.class);
        Call<Feed> call = apiService.getFacebookFeedNextPage(mFeed.getPaging().getNext());
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                // Response is successful, read in feed to get posts
                if (response.isSuccess()) {
                    Feed feed = response.body();
                    mFeed = feed;

                    // Add the data to the adapter to populate the recycler view
                    mAdapter.addMoreFeed(mFeed.getData());

                    // Set loading to true for next pagination
                    loading = true;
                } else {
                    int statusCode = response.code();
                    // Handle Error
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                // Handle execution failures like no internet connectivity
            }
        });
    }
}
