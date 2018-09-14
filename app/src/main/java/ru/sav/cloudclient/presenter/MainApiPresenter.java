package ru.sav.cloudclient.presenter;

import io.reactivex.Flowable;


public class MainApiPresenter extends BaseApiPresenter<String, BaseApiView> {

    @Override
    public void onNext(String s) {

    }

    public void update() {
        getViewState().showLoading();
        Flowable.just("this is test:", "1", "2", "3", "4").subscribe(this);
    }
}
