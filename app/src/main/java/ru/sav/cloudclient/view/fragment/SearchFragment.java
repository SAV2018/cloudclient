package ru.sav.cloudclient.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.sav.cloudclient.R;
import rx.Observable;
import rx.Observer;

public class SearchFragment extends android.support.v4.app.Fragment {
    final String TAG = "SearchFragment";
    TextView output;
    EditText input;
    Button button;
    Observable<String> observable;
    Observer<String> observer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        output = view.findViewById(R.id.text_view);
        input = view.findViewById(R.id.edit_text);
        button = view.findViewById(R.id.button_send);

        initObservation();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initObservation() {
        observable = Observable.just("test", "test2");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"send: " + input.getText().toString());
                observable = Observable.just(input.getText().toString());
                input.setText("");
                observable.subscribe(observer);
            }
        });

        observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                output.setText(String.format("%s, %s", output.getText(), s));
                Log.d(TAG,"onNext: " + s);
            }

            @Override
            public void onError(Throwable msg) {
                Log.d(TAG,"onError: " + msg);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }
        };


    }
}
