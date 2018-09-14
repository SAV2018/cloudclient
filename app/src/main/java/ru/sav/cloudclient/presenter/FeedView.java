package ru.sav.cloudclient.presenter;

import java.util.List;

import ru.sav.cloudclient.model.FeedViewModel;


public interface FeedView extends BaseApiView {

    void setItems(List<FeedViewModel> items);
}
