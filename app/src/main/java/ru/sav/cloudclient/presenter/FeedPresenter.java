package ru.sav.cloudclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.sav.cloudclient.model.FeedViewModel;


@InjectViewState
public class FeedPresenter extends BaseApiPresenter<Object, FeedView> {
    private static final String TAG = "FeedPresenter";

    @Override
    public void onNext(Object o) {

    }

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

        List<FeedViewModel> items = new ArrayList<>();

        for (int i = 0; i < (int) (Math.random() * 10); i++) {
            FeedViewModel feed = new FeedViewModel();
            feed.imageUrl = "http://site.ru/img/" + i + ".png";
            feed.imageDescription = "image #" + i + " description";
            items.add(feed);
        }

        getViewState().setItems(items);
    }
}
