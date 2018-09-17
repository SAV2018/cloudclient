package ru.sav.cloudclient.presenter.feed;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import ru.sav.cloudclient.data.FlickrApiClient;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.data.model.Feed;
import ru.sav.cloudclient.presenter.BaseApiPresenter;


@InjectViewState
public class FeedPresenter extends BaseApiPresenter<Feed, FeedView> implements Subscriber<Feed> {
    private static final String TAG = "FeedPresenter";

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        update();
    }

    public void onButtonLoadClicked() {
        Log.d(TAG,"onButtonLoadClicked: ");

        update();
    }

    private void update() {
        Log.d(TAG,"update: ");

        // ...проверка
        getViewState().showLoading();

//        List<FeedItem> items = new ArrayList<>();
//
//        for (int i = 0; i < (int) (Math.random() * 10); i++) {
//            FeedItem feed = new FeedItem();
//            feed.link = "http://site.ru/img/" + i + ".png";
//            feed.title = "image #" + i + " description";
//            items.add(feed);
//        }

        FlickrApiClient.getInstance().getFeed().subscribe(this);
    }

    @Override
    public void onNext(Feed feed) {
        Log.d(TAG,"onNext: " + feed.title + feed.link);

        List<FeedItem> items = new ArrayList<>();
        for (int i = 0; i < feed.items.size(); i++) {
            FeedItem item = new FeedItem(feed.items.get(i));
            items.add(item);
        }

        getViewState().setItems(items);
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e.toString());
    }

    @Override
    public void onComplete() {
        getViewState().hideLoading();
    }
}