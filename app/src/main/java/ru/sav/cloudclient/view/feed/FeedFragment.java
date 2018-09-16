package ru.sav.cloudclient.view.feed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.feed.FeedPresenter;
import ru.sav.cloudclient.presenter.feed.FeedView;


public class FeedFragment extends MvpAppCompatFragment implements FeedView {
    private final String TAG = "FeedFragment";
    private FeedAdapter adapter;
    private RecyclerView feedListView;
    private TextView output;
    private TextView emptyFeedText;
    private Button buttonLoad;
    private TextView statusBar;
    private ProgressBar progressBar;
    List<FeedItem> items = new ArrayList<>();

    @InjectPresenter
    FeedPresenter feedPresenter;



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
        statusBar = view.findViewById(R.id.status_bar);
        progressBar = view.findViewById(R.id.progress_bar);

        // initialization
        if (bundle != null) {
            // ...
        }

        // set handler
        buttonLoad.setOnClickListener(v -> feedPresenter.onButtonLoadClicked());
    }

    private void initFeedList() {
        feedListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        feedListView.setItemAnimator(new DefaultItemAnimator());

        Log.d(TAG,"initFeedList: " + this.items.size());

        adapter = new FeedAdapter(this);
        feedListView.setAdapter(adapter);

        checkEmptyList();
    }

    private void checkEmptyList() {
        Log.d(TAG,"checkEmptyList: " + adapter.getItemCount());

        if (adapter.getItemCount() > 0) {
            emptyFeedText.setVisibility(View.GONE);
        } else {
            emptyFeedText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setItems(List<FeedItem> items) {
        Log.d(TAG,"setItems: " + "1) this.items - " + this.items.size() + " items - " + items.size());

        this.items = items;
        checkEmptyList();

        Log.d(TAG,"2) this.items - " + this.items.size() + " items - " + items.size());
        adapter.notifyDataSetChanged();
        Integer n = adapter.getItemCount();
        if (n > 0) {
            statusBar.setText(MessageFormat.format(getString(R.string.loaded_items), adapter.getItemCount()));
        } else {
            statusBar.setText(R.string.no_items);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        feedListView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        feedListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
        statusBar.setText(msg);
        progressBar.setVisibility(View.GONE);
    }

}
