package ru.sav.cloudclient.presenter;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class MainApiPresenter extends BaseApiPresenter<String> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {

    }

    public void update() {
        getViewState().showLoading();
        Observable.just("1", "2", "3", "4").subscribe(this);
    }
}
