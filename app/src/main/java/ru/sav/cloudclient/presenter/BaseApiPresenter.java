package ru.sav.cloudclient.presenter;

import com.arellomobile.mvp.MvpPresenter;

import rx.Observer;


abstract class BaseApiPresenter<T> extends MvpPresenter<BaseApiView> implements Observer<T> {

    @Override
    public void onCompleted() {
        getViewState().hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e.getLocalizedMessage());
    }

    @Override
    public void onNext(T t) {

    }
}
