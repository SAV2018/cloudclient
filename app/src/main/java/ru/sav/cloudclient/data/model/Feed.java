package ru.sav.cloudclient.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Feed {

        @Expose
        public String title;

        @Expose
        public String link;

        @Expose
        public String description;

        @Expose
        public String modified;

        @Expose
        public String generator;

        @Expose
        public List<Item> items = null;


    public class Item {

        @Expose
        public String title;

        @Expose
        public String link;

        @Expose
        public Media media;

        @SerializedName("date_taken")
        @Expose
        public String dateTaken;

        @Expose
        public String description;

        @Expose
        public String published;

        @Expose
        public String author;

        @SerializedName("author_id")
        @Expose
        public String authorId;

        @Expose
        public String tags;
    }

    public class Media {

        @Expose
        public String m;
    }
}



