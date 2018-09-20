package ru.sav.cloudclient.presenter.feed;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscriber;
import java.util.ArrayList;
import java.util.List;

import ru.sav.cloudclient.data.DataLoader;
import ru.sav.cloudclient.data.FlickrApiClient;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.data.model.Feed;
import ru.sav.cloudclient.presenter.BaseApiPresenter;


@InjectViewState
public class FeedPresenter extends BaseApiPresenter<Feed, FeedView>
        implements Subscriber<Feed> {
    private static final String TAG = "FeedPresenter";

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        update();
    }

    public void onButtonLoadClicked() {
        // загрузка списка из БД
        DataLoader loader = new DataLoader();
        loader.loadAll();
    }

    public void onButtonSaveClicked() {
        // сохранение списка в БД

    }

    public void onButtonReloadClicked() {
        Log.d(TAG,"onButtonReloadClicked: ");

        update();
    }

    private void update() {
        Log.d(TAG,"update: ");

        getViewState().showLoading();

        FlickrApiClient.getInstance().getFeed().subscribe(this);
    }

    @Override
    public void onNext(Feed feed) {
        Log.d(TAG,"onNext: ");

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