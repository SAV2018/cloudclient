package ru.sav.cloudclient.data;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.sav.cloudclient.data.model.Feed;
import ru.sav.cloudclient.presenter.feed.FeedApi;


public class FlickrApiClient {
    private static final FlickrApiClient instance = new FlickrApiClient();
    private FeedApi feedApi = new ApiClientBuilder().createService(FeedApi.class);

    private FlickrApiClient() { }

    public static FlickrApiClient getInstance() {
        return instance;
    }

    public Flowable<Feed> getFeed(){
        return feedApi.getFeed("json",1)   // set response type: json, no wrapper
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}