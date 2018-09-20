package ru.sav.cloudclient.data;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.sav.cloudclient.data.model.FeedItem;


public class DataLoader {
    private final static String TAG = "DataLoader";
    private Realm realm;
    private List<FeedItem> items = new ArrayList<>();

    public void loadData() {
        Log.d(TAG,"loadData: ");

    }

    void saveData() {
        try {
            realm = Realm.getDefaultInstance();
            for (FeedItem item : items) {
                try {
                    realm.beginTransaction();
                    RealmModel realmModel = realm.createObject(RealmModel.class);
                    realmModel.setLink(item.link);
                    realmModel.setTitle(item.title);
                    realmModel.setDate(item.date);
                    realm.commitTransaction();
                } catch (Exception e) {
                    realm.cancelTransaction();
                }
            }

            long count = realm.where(RealmModel.class).count();
            realm.close();
        } catch (Exception e) {

        }
    }

    public List<FeedItem> loadAll() {
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<RealmModel> items = realm.where(RealmModel.class).findAll();
            realm.close();
        } catch (Exception e) {

        }
        return items;
    }

    void deleteAll() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<RealmModel> tempList = realm.where(RealmModel.class).findAll();
            realm.executeTransaction(realm -> tempList.deleteAllFromRealm());
            realm.close();
        } catch (Exception e) {

        }
    }
}