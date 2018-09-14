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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
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
    private Button buttonLoad;
    private FeedPresenter feedPresenter;
    private List<FeedViewModel> feed = new ArrayList<>();

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
        buttonLoad = view.findViewById(R.id.button_load);

        // initialization
        if (bundle != null) {
            output.setText(bundle.getString("list", ""));
        }

        // set handler
        buttonLoad.setOnClickListener(v -> {
            //feedPresenter.onButtonLoadClicked();
        });
    }

    private void initFeedList() {
        feedListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        feedListView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FeedAdapter(this, feed);
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
