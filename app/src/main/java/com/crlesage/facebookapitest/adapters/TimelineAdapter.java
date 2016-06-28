package com.crlesage.facebookapitest.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crlesage.facebookapitest.R;
import com.crlesage.facebookapitest.activities.PostActivity;
import com.crlesage.facebookapitest.dataModels.Post;
import com.crlesage.facebookapitest.interfaces.ApiInterface;
import com.crlesage.facebookapitest.interfaces.Constants;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chris on 2/19/2016.
 * A recycler view adapter to display the feed.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.PostViewHolder> {

    private static final String TAG = "FacebookApi";
    private ArrayList<Post> mPostData = new ArrayList<Post>();
    private String mProfileIcon;
    private Activity mActivity;
    public Typeface robotoFont;

    public TimelineAdapter(Activity activity) {
        this.mActivity = activity;
        AssetManager am = mActivity.getApplicationContext().getApplicationContext().getAssets();
        this.robotoFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Roboto-Regular.ttf"));
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postIcon;
        TextView postTitle;
        TextView postTime;
        TextView postDescription;
        ImageView postImage;
        LinearLayout postLike;
        TextView postLikeText;

        PostViewHolder(View itemView) {
            super(itemView);
            postIcon = (ImageView) itemView.findViewById(R.id.post_icon);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postTime = (TextView) itemView.findViewById(R.id.post_time);
            postDescription = (TextView) itemView.findViewById(R.id.post_description);
            postImage = (ImageView) itemView.findViewById(R.id.post_image);
            postLike = (LinearLayout) itemView.findViewById(R.id.post_like_layout);
            postLikeText = (TextView) postLike.findViewById(R.id.post_like_text);
        }
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_timeline, viewGroup, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, final int position) {
        // Get reference to the post's data
        final Post post = mPostData.get(position);
        String postIcon = mProfileIcon;
        String postTitle = post.getFrom().getName();
        String postTime = post.getCreated_time();
        String convertedPostTime = convertTime(postTime);
        String postDescription = post.getName();
        String postImage = post.getSource();

        // Get reference to the view holder's views
        TextView postTitleTextView = postViewHolder.postTitle;
        TextView postTimeTextView = postViewHolder.postTime;
        TextView postDescriptionTextView = postViewHolder.postDescription;
        TextView postLikeTextView = postViewHolder.postLikeText;
        ImageView postImageView = postViewHolder.postImage;
        ImageView postIconImageView = postViewHolder.postIcon;

        // Initialize and set up fonts for post's text
        postTitleTextView.setTypeface(robotoFont);
        postTimeTextView.setTypeface(robotoFont);
        postDescriptionTextView.setTypeface(robotoFont);
        postLikeTextView.setTypeface(robotoFont);

        // Set the post's data
        postTitleTextView.setText(postTitle);
        postTimeTextView.setText(convertedPostTime);
        postDescriptionTextView.setText(postDescription);
        Picasso.with(mActivity.getApplicationContext())
                .load(postIcon)
                .fit().centerCrop()
                .into(postIconImageView);
        Picasso.with(mActivity.getApplicationContext())
                .load(postImage)
                .fit().centerCrop()
                .into(postImageView);

        // Set onClickListener for clicking a particular post
        postViewHolder.postIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = mPostData.get(position);
                Intent intent = new Intent(mActivity, PostActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("profileIcon", mProfileIcon);
                mActivity.startActivity(intent);
            }
        });

        // Set onClickListener for clicking "Like"
        postViewHolder.postLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity)
                        .setSingleChoiceItems(Constants.items, 0, null)
                        .setTitle("Add Reaction to Post")
                        .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                likeFacebookPost(mPostData.get(position).getId(), Constants.items[selectedPosition].toString());

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostData.size();
    }

    // Remove all posts from feed
    public void removeAllItems() {
        mPostData.clear();
        notifyDataSetChanged();
    }

    // Add single post to the feed
    public void addItem(Post post) {
        post.position = mPostData.size();
        mPostData.add(post);
        notifyDataSetChanged();
    }

    // Add the feed to the adapter
    public void addFeed(ArrayList<Post> posts, String profileIcon) {
        mPostData = posts;
        mProfileIcon = profileIcon;
        notifyDataSetChanged();
    }

    // Add more posts to the feed (Paging)
    public void addMoreFeed(ArrayList<Post> posts) {
        for (Post post : posts) {
            mPostData.add(post);
        }
        notifyDataSetChanged();
    }

    // Convert a date stamp into an absolute time stamp for each post
    public String convertTime(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd", Locale.US);
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mmaa", Locale.US);

        String fullTime = "Time";
        try {
            Date date = format.parse(time);
            String monthDay = format1.format(date);
            String hourMinute = format2.format(date).toLowerCase();
            fullTime = monthDay + " at " + hourMinute;

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
        return fullTime;
    }

    // Retrofit call to get profile picture icon
    public void likeFacebookPost(String object_id, final String choice) {
        ApiInterface apiService = ApiInterface.retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiService.likeFacebookPost(object_id, Constants.accessToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // Response is successful, read in feed to get profile picture
                if (response.isSuccess()) {
                    JsonObject result = response.body();
                    Toast.makeText(mActivity, choice + "!", Toast.LENGTH_SHORT).show();

                } else {
                    int statusCode = response.code();
                    // Handle Error
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle execution failures like no internet connectivity
            }
        });
    }
}
