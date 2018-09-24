package ru.sav.cloudclient.view.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.data.model.FeedItem;
import ru.sav.cloudclient.presenter.feed.FeedPresenter;
import ru.sav.cloudclient.presenter.feed.FeedView;
import ru.sav.cloudclient.view.MainActivity;


public class FeedFragment extends MvpAppCompatFragment implements FeedView {
    private final String TAG = "FeedFragment";
    private FeedAdapter adapter;
    private RecyclerView feedListView;
    private TextView emptyFeedText;
    private Button buttonReload;
    private TextView statusBar;
    private ProgressBar progressBar;
    List<FeedItem> items = new ArrayList<>();
    private Button buttonLoad, buttonDelete;

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
        feedListView = view.findViewById(R.id.feed_list);
        emptyFeedText = view.findViewById(R.id.empty_feed);
        buttonReload = view.findViewById(R.id.button_load);
        statusBar = view.findViewById(R.id.status_bar);
        progressBar = view.findViewById(R.id.progress_bar);
        buttonLoad = view.findViewById(R.id.button_load_bd);
        buttonDelete = view.findViewById(R.id.button_delete);

        // initialization
        if (bundle != null) {
            // ...
        }

        // set handler
        buttonReload.setOnClickListener(v -> {
            Toast.makeText(FeedFragment.this.getActivity(), FeedPresenter.MSG_LOADING_NEW_PHOTOS,
                    Toast.LENGTH_LONG).show();
            if (((MainActivity) FeedFragment.this.getActivity()).isInternetConnection()) {
                feedPresenter.onButtonReloadClicked();
            }
        });

        buttonLoad.setOnClickListener(v -> {
            Toast.makeText(FeedFragment.this.getActivity(), FeedPresenter.MSG_LOADING_FROM_DB,
                    Toast.LENGTH_LONG).show();
            feedPresenter.onButtonLoadClicked();
        });

        buttonDelete.setOnClickListener(v -> new AlertDialog.Builder(
                Objects.requireNonNull(this.getActivity())).
                setMessage(R.string.msg_dialog_on_delete)
                .setPositiveButton(R.string.dialog_ok_button,
                        (dialog, id) -> {
                            Toast.makeText(FeedFragment.this.getActivity(),
                                    FeedPresenter.MSG_DELETING_ITEMS,
                                    Toast.LENGTH_LONG).show();
                            feedPresenter.onButtonDeleteClicked();
                        })
                .setNegativeButton(R.string.dialog_cancel_button, null).create().show());
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
        //Log.d(TAG,"setItems: " + "1) this.items - " + this.items.size() + " items - " + items.size());

        this.items = items;
        checkEmptyList();

        //Log.d(TAG,"2) this.items - " + this.items.size() + " items - " + items.size());
        adapter.notifyDataSetChanged();
        Integer n = adapter.getItemCount();
    }

    @Override
    public void addMessage(String message) {
        statusBar.setText(String.format("%s %s", statusBar.getText(), message));
    }

    @Override
    public void setMessage(String message) {
        statusBar.setText(message);
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
        addMessage(msg);
        progressBar.setVisibility(View.GONE);
    }
}