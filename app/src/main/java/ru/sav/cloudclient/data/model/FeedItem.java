package ru.sav.cloudclient.data.model;

public class FeedItem {
    public String title;
    public String link;
    public String date;

    public FeedItem() {}

    public FeedItem(Feed.Item item) {
        final String noTitle = "<no_title>";

        link = item.media.m;
        title = ((item.title.trim()).isEmpty()) ? noTitle : item.title;
        date = item.published.substring(0,10);
    }
}
