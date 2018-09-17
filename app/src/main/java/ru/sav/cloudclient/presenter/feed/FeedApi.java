package ru.sav.cloudclient.presenter.feed;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.sav.cloudclient.data.model.Feed;


public interface FeedApi {

    @GET("services/feeds/photos_public.gne")
    Flowable<Feed> getFeed(@Query("format") String format, @Query("nojsoncallback") int value);
    // https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1
}