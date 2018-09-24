package ru.sav.cloudclient.presenter.feed;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;
import ru.sav.cloudclient.data.DataLoader;
import ru.sav.cloudclient.data.FeedItemRealmModel;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.BaseApiPresenter;


@InjectViewState
public class FeedPresenter extends BaseApiPresenter<List<FeedItem>, FeedView> {
    private static final String TAG = "FeedPresenter";
    private DataLoader dataLoader = new DataLoader();

    public static final String MSG_LOADING_NEW_PHOTOS = "Loading new photos from flickr.com…";
    public static final String MSG_DELETING_ITEMS = "Deleting items from DB…";
    public static final String MSG_LOADING_FROM_DB = "Loading items from DB…";
    private static final String MSG_PATTERN_ITEMS_LOADED = "Loaded {0} items.";
    private static final String MSG_PATTERN_ITEMS_IN_DB = "{0} items in DB.";
    private static final String MSG_PATTERN_NEW_ITEMS_LOADED = "Loaded {0} new items.";

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        onButtonLoadClicked();
    }

    public void onButtonLoadClicked() { // загрузка всего списка из БД
        getViewState().showLoading();
        getViewState().setMessage(MSG_LOADING_FROM_DB);

        dataLoader.loadItemsFromDB()
                .subscribe(new SingleObserver<RealmResults<FeedItemRealmModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(RealmResults<FeedItemRealmModel> results) {
                        Log.d(TAG, "loadItemsFromDB.onSuccess: ");

                        getViewState().hideLoading();
                        getViewState().setItems(toFeedViewModel(results));
                        getViewState().setMessage(MessageFormat.format(MSG_PATTERN_ITEMS_LOADED,
                                results.size()));
                        getCount();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideLoading();
                        getViewState().setItems(new ArrayList<>());
                        getViewState().addMessage(e.getMessage());
                        getCount();
                    }
                });
    }

    public void onButtonDeleteClicked() { // удаление всего списка из БД
        getViewState().showLoading();
        getViewState().setMessage(MSG_DELETING_ITEMS);
        dataLoader.deleteAll().subscribe(new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "deleteItemsFromDB.onComplete: ");

                getViewState().hideLoading();
                getViewState().setItems(new ArrayList<>());
                getViewState().setMessage("");
                getCount();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().hideLoading();
                getViewState().setMessage(e.getMessage());
                getCount();
            }
        });
    }

    public void onButtonReloadClicked() {
        Log.d(TAG,"onButtonReloadClicked: ");

        update();
    }

    private void update() {
        Log.d(TAG,"update: ");

        getViewState().showLoading();
        getViewState().setMessage(MSG_LOADING_NEW_PHOTOS);
        dataLoader.saveItemsToDB(new ArrayList<>()).subscribe(new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "saveItemsToDB.onComplete: saving to DB done.");
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        new DataLoader().loadData().subscribe(new Subscriber<List<FeedItem>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<FeedItem> items) {
                Log.d(TAG,"onNext: ");
                for (int i = 0; i < items.size(); i++) {
                    Log.d(TAG, "item"+i+": "+items.get(i).title);
                }

                getViewState().setItems(items);
                getViewState().setMessage(MessageFormat.format(MSG_PATTERN_NEW_ITEMS_LOADED,
                        items.size()));
                getCount();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().addMessage(e.getMessage());
                getViewState().showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                getViewState().hideLoading();
            }
        });
    }

    private void getCount() { // получить кол-во записей в БД
        dataLoader.getCountItemsInDB().subscribe(new SingleObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Long count) {
                getViewState().addMessage(MessageFormat.format(MSG_PATTERN_ITEMS_IN_DB, count));
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private List<FeedItem> toFeedViewModel(RealmResults<FeedItemRealmModel> results) {
        List<FeedItem> items = new ArrayList<>();

        if (results.isValid()) {
            Log.d(TAG, "toFeedViewModel (size): "+results.size());

            for (int i = results.size() - 1; i >= 0; i--) {
                FeedItemRealmModel realmItem = results.get(i);
                FeedItem item = new FeedItem();

                if (realmItem != null) {
                    //Log.d(TAG, "for ("+i+"): "+realmItem.getTitle());
                    item.title = realmItem.getTitle();
                    item.date = realmItem.getDate();
                    item.link = realmItem.getLink();
                }
                items.add(item);
            }
        }
        return items;
    }
}