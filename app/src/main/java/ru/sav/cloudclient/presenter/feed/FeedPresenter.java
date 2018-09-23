package ru.sav.cloudclient.presenter.feed;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;
import ru.sav.cloudclient.data.DataLoader;
import ru.sav.cloudclient.data.FeedItemRealmModel;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.BaseApiPresenter;


@InjectViewState
public class FeedPresenter extends BaseApiPresenter<List<FeedItem>, FeedView>
        implements Subscriber<List<FeedItem>> {
    private static final String TAG = "FeedPresenter";
    private DataLoader dataLoader = new DataLoader();
    private Disposable disposable;
    //
    public static final String MSG_LOADING_NEW_PHOTOS = "Loading new photos from flickr.com…";
    public static final String MSG_DELETING_ITEMS = "Deleting items from DB…";
    public static final String MSG_LOADING_FROM_DB = "Loading items from DB…";

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        //onButtonLoadClicked();
    }

    public void onButtonLoadClicked() { // загрузка всего списка из БД
        getViewState().showLoading();
        getViewState().setMessage(MSG_LOADING_FROM_DB);

        dataLoader.loadItemsFromDB()
                .subscribe(new SingleObserver<RealmResults<FeedItemRealmModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(RealmResults<FeedItemRealmModel> results) {
                        Log.d(TAG, "loadItemsFromDB.onSuccess: ");

                        List<FeedItem> items = toFeedViewModel(results);
                        getViewState().hideLoading();
                        getViewState().setMessage("");
                        getViewState().setItems(items);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideLoading();
                        getViewState().setItems(new ArrayList<>());
                        getViewState().addMessage(e.getMessage());
                    }
                });
    }

    public void onButtonDeleteClicked() { // удаление всего списка из БД
        getViewState().showLoading();
        getViewState().setMessage(MSG_DELETING_ITEMS);
        dataLoader.deleteAll().subscribe(new SingleObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "loadItemsFromDB.onSuccess: ");

                getViewState().hideLoading();
                getViewState().setMessage("");
                getViewState().setItems(new ArrayList<>());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().hideLoading();
                getViewState().setMessage(e.getMessage());
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
        dataLoader.saveItemsToDB(new ArrayList<>()).subscribe(new SingleObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "saveItemsToDB.onSuccess: "+s);

                if (!s.isEmpty()) {
                    getViewState().addMessage(s);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        new DataLoader().loadData(this);
    }

    @Override
    public void onNext(List<FeedItem> items) {
        Log.d(TAG,"onNext: ");
        for (int i = 0; i < items.size(); i++) {
            Log.d(TAG, "item"+i+": "+items.get(i).title);
        }

        getViewState().setItems(items);
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

    private List<FeedItem> toFeedViewModel(RealmResults<FeedItemRealmModel> results) {
        List<FeedItem> items = new ArrayList<>();

        if (results.isValid()) {
            Log.d(TAG, "toFeedViewModel (size): "+results.size());

            for (int i = results.size() - 1; i >= 0; i--) {
            //for (FeedItemRealmModel realmItem : results) {
                FeedItem item = new FeedItem();
                FeedItemRealmModel realmItem = results.get(i);
                if (realmItem != null) {
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