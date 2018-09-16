package ru.sav.cloudclient.view.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.data.model.FeedItem;


public class FeedAdapter extends RecyclerView.Adapter  {
    private FeedFragment feedFragment;

    FeedAdapter(FeedFragment feedFragment) {
        this.feedFragment = feedFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(feedFragment.getActivity());
        View view = layoutInflater.inflate(R.layout.list_item, parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder) holder).bindItem(position);
    }

    @Override
    public int getItemCount() {
        return feedFragment.items.isEmpty() ? 0 : feedFragment.items.size();
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        private final LinearLayout imageHeader;
        private final TextView imageLink, imageTitle, imageDate;
        private final ImageView image;

        ItemHolder(View view) {
            super(view);

            // binding item's views
            imageHeader = itemView.findViewById(R.id.image_header);
            imageTitle = itemView.findViewById(R.id.image_title);
            imageLink = itemView.findViewById(R.id.image_link);
            imageDate = itemView.findViewById(R.id.image_date);
            image = itemView.findViewById(R.id.image);
        }

        void bindItem(int position) {
            FeedItem item = feedFragment.items.get(position);

            Glide.with(Objects.requireNonNull(feedFragment.getActivity())).load(item.link).into(image);

            imageTitle.setText(item.title);
            imageLink.setText(item.link);
            imageDate.setText(item.date);
        }
    }
}
