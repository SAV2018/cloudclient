package ru.sav.cloudclient.presenter.feed;

import java.util.List;

import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.BaseApiView;


public interface FeedView extends BaseApiView {

    void setItems(List<FeedItem> items);
}
