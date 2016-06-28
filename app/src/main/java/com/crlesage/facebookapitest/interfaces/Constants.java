package com.crlesage.facebookapitest.interfaces;

/**
 * Created by Chris on 2/18/2016.
 * Constants found throughout the application
 */
public interface Constants {
    // Event page id
    // TODO need to add an event id
    String eventId = "0";

    // Base URL for Facebook API calls
    String URL = "https://graph.facebook.com";

    // Access Token
    // TODO need to add an accessToken or use Facebook SDK to grab accessToken
    String accessToken = "accessToken";

    // "Like" enhanced features
    CharSequence[] items = {"Like", "Love", "Haha", "Wow", "Sad", "Angry"};

    // Limit count on photo calls
    int limit = 15;

    // Type of photos
    String type = "uploaded";
}
