package ru.sav.cloudclient.data;

import android.util.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.sav.cloudclient.data.model.FeedItem;


public class DataLoader implements Subscriber<List<FeedItem>> {
    private final static String TAG = "DataLoader";
    private Realm realm;
    private List<FeedItem> items = new ArrayList<>();
    private Subscriber<List<FeedItem>> subscriber;


    public Flowable<List<FeedItem>> loadData(Subscriber<List<FeedItem>> subscriber) {
        Log.d(TAG,"loadData: ");

        this.subscriber = subscriber;
        FlickrApiClient.getInstance().getItems().subscribe(this);

        return Flowable.create((FlowableOnSubscribe<List<FeedItem>>) emitter -> {
            emitter.onNext(items);
            emitter.onComplete();
        }, BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void onSubscribe(Subscription s) { s.request(Long.MAX_VALUE); }

    @Override
    public void onNext(List<FeedItem> items) {
        Log.d(TAG,"onNext: "+items.getClass());
        for (int i = 0; i < items.size(); i++) {
            Log.d(TAG, "item"+i+": "+items.get(i).title+" "+items.get(i).date+" "+items.get(i).link);
        }

        // отправляем данные в presenter
        subscriber.onNext(items);
        subscriber.onComplete();
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    public Single<String> saveItemsToDB(List<FeedItem> items) {
        String s;
        Log.d(TAG, "saveItemsToDB: ");

        try {
            realm = Realm.getDefaultInstance();

            //Collections.reverse(items);
            for (FeedItem item : items) {
                //Log.d(TAG, "onNext-RealmIterator: " + item.title);
                realm.beginTransaction();
                FeedItemRealmModel realmItem = realm.createObject(FeedItemRealmModel.class);
                realmItem.setLink(item.link);
                realmItem.setTitle(item.title);
                realmItem.setDate(item.date);
                realm.commitTransaction();
            }

            Long count = realm.where(FeedItemRealmModel.class).count();
            s = String.format(Locale.ROOT, "%d items in DB.", count);
            Log.d(TAG, "saveItemsToDB (n): "+count);
        } catch (Exception e) {
            s = e.getMessage();
        }
        return Single.just(s);
    }

    public Single<RealmResults<FeedItemRealmModel>> loadItemsFromDB() {
        Log.d(TAG, "loadItemsFromDB: ");

        RealmResults<FeedItemRealmModel> result = null;
        try {
            realm = Realm.getDefaultInstance();
            result = realm.where(FeedItemRealmModel.class).findAll();
            realm.close();

            Log.d(TAG,"loadItemsFromDB (size): "+result.size());
        } catch (Exception e) {
            //Log.d(TAG, "loadItemsFromDB: error "+e.getMessage());
        }
        return Single.just(result);
    }

    public Single<String> deleteAll() {
        String s;

        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<FeedItemRealmModel> tempList = realm.where(FeedItemRealmModel.class).findAll();
            realm.executeTransaction(realm -> tempList.deleteAllFromRealm());
            realm.close();
            s = "Items deleted.";
        } catch (Exception e) {
            s = e.getMessage();
        }
        return Single.just(s);
    }
}