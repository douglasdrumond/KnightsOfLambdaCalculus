package com.cafelinear.knightsoflambdacalculus;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class TextWatcherActivity extends RxActivity {

    private static final String LOG_TAG = TextWatcherActivity.class.getSimpleName();

    private EditText mUsername;
    private TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textwatcher);

        mUsername = (EditText) findViewById(R.id.username);
        mOutput = (TextView) findViewById(R.id.text_output);

        final Observable<TextViewTextChangeEvent> textChangeObservable = RxTextView.textChangeEvents(mUsername);

        textChangeObservable.compose(RxLifecycle.bindActivity(lifecycle()))
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }

    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                mOutput.setText("Searching for " + onTextChangeEvent.text().toString());
            }
        };
    }
}
