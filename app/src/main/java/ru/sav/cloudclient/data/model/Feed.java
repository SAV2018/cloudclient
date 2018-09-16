package ru.sav.cloudclient.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Feed {

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("link")
        @Expose
        public String link;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("modified")
        @Expose
        public String modified;

        @SerializedName("generator")
        @Expose
        public String generator;

        @SerializedName("items")
        @Expose
        public List<Item> items = null;


    public class Item {

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("link")
        @Expose
        public String link;

        @SerializedName("media")
        @Expose
        public Media media;

        @SerializedName("date_taken")
        @Expose
        public String dateTaken;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("published")
        @Expose
        public String published;

        @SerializedName("author")
        @Expose
        public String author;

        @SerializedName("author_id")
        @Expose
        public String authorId;

        @SerializedName("tags")
        @Expose
        public String tags;
    }

    public class Media {

        @SerializedName("m")
        @Expose
        public String m;
    }
}



