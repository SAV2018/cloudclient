package ru.sav.cloudclient.data;

import android.util.Log;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.sav.cloudclient.data.model.FeedItem;


public class DataLoader {
    private final static String TAG = "DataLoader";
    private Realm realm;


    public Flowable<List<FeedItem>> loadData() {
        Log.d(TAG,"loadData: ");

        return FlickrApiClient.getInstance().getItems();
    }

    public Completable saveItemsToDB(List<FeedItem> items) {
        Log.d(TAG, "saveItemsToDB: ");

        try {
            realm = Realm.getDefaultInstance();
            for (int i = items.size() - 1; i >=0; i--) {
                FeedItem item = items.get(i);
                //Log.d(TAG, "onNext-RealmIterator: " + item.title);
                realm.beginTransaction();
                FeedItemRealmModel realmItem = realm.createObject(FeedItemRealmModel.class);
                realmItem.setLink(item.link);
                realmItem.setTitle(item.title);
                realmItem.setDate(item.date);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            Log.d(TAG, "saveItemsToDB: error" + e.getMessage());
        }
        return Completable.complete();
    }

    public Single<RealmResults<FeedItemRealmModel>> loadItemsFromDB() {
        Log.d(TAG, "loadItemsFromDB: ");

        RealmResults<FeedItemRealmModel> result = null;
        try {
            realm = Realm.getDefaultInstance();
            result = realm.where(FeedItemRealmModel.class).findAll();

            Log.d(TAG,"loadItemsFromDB (size): "+result.size());
        } catch (Exception e) {
            Log.d(TAG, "loadItemsFromDB: error "+e.getMessage());
        }
        return Single.just(result);
    }

    public Completable deleteAll() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<FeedItemRealmModel> tempList = realm.where(FeedItemRealmModel.class).findAll();
            realm.executeTransaction(realm -> tempList.deleteAllFromRealm());
        } catch (Exception e) {
            Log.d(TAG, "deleteAll: error" + e.getMessage());
        }
        return Completable.complete();
    }

    public Single<Long> getCountItemsInDB() {
        Long count = 0L;

        try {
            realm = Realm.getDefaultInstance();
            count = realm.where(FeedItemRealmModel.class).count();
        } catch (Exception e) {
            Log.d(TAG, "getCountItemsInDB: error" + e.getMessage());
        }
        Log.d(TAG, "getCount (n): " + count + " items in DB.");
        return Single.just(count);
    }
}