package ru.sav.cloudclient.view.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import ru.sav.cloudclient.R;


public class FeedAdapter extends RecyclerView.Adapter {
    private List<Object> items;
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
        return 0;
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView imageDescription;
        private final TextView imageHeader;
        private final TextView imageUrl;

        ItemHolder(View view) {
            super(view);

            // binding item's views
            imageHeader = itemView.findViewById(R.id.image_header);
            imageDescription = itemView.findViewById(R.id.image_description);
            imageUrl = itemView.findViewById(R.id.image_url);
        }

        void bindItem(int position) {
            Object item = items.get(position);

            imageDescription.setText("Some description");
            imageUrl.setText("http://anysite.com/images/somefile.png");
        }
    }
}
