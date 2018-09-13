package ru.sav.cloudclient.presenter;

import com.arellomobile.mvp.MvpView;

public interface BaseApiView extends MvpView {

    void showLoading();
    void hideLoading();
    void showError(String msg);
}
