package ru.sav.cloudclient.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import ru.sav.cloudclient.data.model.Feed;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.feed.FeedApi;


public class FlickrApiClient {
    private static final FlickrApiClient instance = new FlickrApiClient();
    private FeedApi feedApi = new ApiClientBuilder().createService(FeedApi.class);

    private FlickrApiClient() { }

    public static FlickrApiClient getInstance() {
        return instance;
    }

    public Single<List<FeedItem>> getItems() {
        return feedApi.getFeed("json",1) // set response type: json, no wrapper
                .flatMap((Function<Feed, Flowable<List<Feed.Item>>>) feed -> Flowable.just(feed.items))
                .flatMapIterable((Function<List<Feed.Item>, Iterable<Feed.Item>>) items -> items)
                .map(FeedItem::new)
                .toList()
                // .doOnSuccess(items -> new DataLoader().saveItemsToDB(items))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}