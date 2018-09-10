package ru.sav.cloudclient.presenter;

import rx.Observable;

public class MainApiPresenter extends BaseApiPresenter<String> {

    @Override
    public void onNext(String s) {

    }

    public void update() {
        getViewState().showLoading();
        Observable.just("1", "2", "3", "4").subscribe(this);
    }
}
