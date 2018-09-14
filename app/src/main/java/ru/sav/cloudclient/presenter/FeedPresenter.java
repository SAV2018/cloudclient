package ru.sav.cloudclient.presenter;

import java.util.ArrayList;
import java.util.List;

import ru.sav.cloudclient.model.FeedViewModel;


public class FeedPresenter extends BaseApiPresenter<Object, FeedView> {

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void attachView(FeedView view) {
        super.attachView(view);

        update();
    }

    public void onButtonLoadClicked() {
        update();
    }

    private void update(){
        List<FeedViewModel> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FeedViewModel feed = new FeedViewModel();
            feed.imageUrl = "http://site.ru/img/" + i + ".png";
            feed.imageDescription = "image #" + i + " description";
            items.add(feed);
        }

        getViewState().setItems(items);
    }
}
