package ru.sav.cloudclient.view.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.sav.cloudclient.R;


public class SearchFragment extends android.support.v4.app.Fragment {
    final String TAG = "SearchFragment";
    private TextView output;
    private EditText input;
    private Button button;
    private Observable<String> observable;
    private Observer<String> observer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        bindViews(view, bundle);
        initObservation();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("list", output.getText().toString());
    }

    private void initObservation() {
        observable = Observable.just("test", "test2");

        button.setOnClickListener(v -> {
            Log.d(TAG,"send: " + input.getText().toString());
            observable = Observable.just(input.getText().toString());
            input.setText("");
            observable.subscribe(observer);
        });

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String s) {
                if (output.getText().length() > 0) {
                    output.setText(String.format("%s, %s", output.getText(), s));
                } else {
                    output.setText(s);
                }
                Log.d(TAG,"onNext: " + s);
            }

            @Override
            public void onError(Throwable msg) {
                Log.d(TAG,"onError: " + msg);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onCompleted");
            }
        };
    }

    private void bindViews(View view, Bundle bundle) {
        // binding
        output = view.findViewById(R.id.text_view);
        input = view.findViewById(R.id.edit_text);
        button = view.findViewById(R.id.button_send);

        // initialization
        if (bundle != null) {
            output.setText(bundle.getString("list", ""));
        }
    }
}
