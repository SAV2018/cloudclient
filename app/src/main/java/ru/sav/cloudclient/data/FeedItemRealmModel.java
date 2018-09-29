package ru.sav.cloudclient.data;

import io.realm.RealmObject;


public class FeedItemRealmModel extends RealmObject {
    private String link;
    private String date;
    private String title;

    public void setLink(String link){
        this.link = link;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getLink(){
        return link;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }
}