package com.crlesage.facebookapitest.interfaces;

import com.crlesage.facebookapitest.dataModels.Feed;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Chris on 2/18/2016.
 * API interface for Facebook services.
 */
public interface ApiInterface {

    // Grab list of posts
    @GET("/{event_id}/photos")
    Call<Feed> getFacebookFeed(
            @Path("event_id") String event_id,
            @Query("limit") int limit,
            @Query("type") String type,
            @Query("access_token") String accessToken
    );

    // Grabs the event page's icon
    @GET("/{event_id}/photos")
    Call<Feed> getProfilePicture(
            @Path("event_id") String event_id,
            @Query("access_token") String accessToken
    );

    // Gets the next page of a facebook feed
    // https://futurestud.io/blog/retrofit-2-how-to-use-dynamic-urls-for-requests
    @GET()
    Call<Feed> getFacebookFeedNextPage(
            @Url String url
    );

    @POST("/v2.6/{object-id}/likes")
    Call<JsonObject> likeFacebookPost(
            @Path("object-id") String object_id,
            @Query("access_token") String accessToken
    );

    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
}
