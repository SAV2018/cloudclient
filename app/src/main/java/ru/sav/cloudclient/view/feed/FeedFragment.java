package ru.sav.cloudclient.view.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.model.FeedViewModel;
import ru.sav.cloudclient.presenter.FeedPresenter;
import ru.sav.cloudclient.presenter.FeedView;


public class FeedFragment extends android.support.v4.app.Fragment {
    private FeedAdapter adapter;
    private RecyclerView feedListView;
    private TextView output;
    private TextView emptyFeedText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_feed, container,false);

        bindViews(view, bundle);
        initFeedList();
        return view;
    }

    private void bindViews(View view, Bundle bundle) {
        // binding
        output = view.findViewById(R.id.text_view);
        feedListView = view.findViewById(R.id.feed_list);
        emptyFeedText = view.findViewById(R.id.empty_feed);

        // initialization
        if (bundle != null) {
            output.setText(bundle.getString("list", ""));
        }
    }

    private void initFeedList() {
        feedListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        feedListView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FeedAdapter(this);
        feedListView.setAdapter(adapter);

        checkEmptyList();
    }

    private void checkEmptyList() {
        if (adapter.getItemCount() == 0) {
            emptyFeedText.setVisibility(View.VISIBLE);
        } else {
            emptyFeedText.setVisibility(View.GONE);
        }
    }
}
