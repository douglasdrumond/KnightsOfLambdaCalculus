package com.cafelinear.knightsoflambdacalculus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;

public class TextWatcherActivity extends Activity {

    private static final String LOG_TAG = TextWatcherActivity.class.getSimpleName();

    private EditText mUsername;
    private Subscription mSubscription;
    private TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        mUsername = (EditText) findViewById(R.id.username);
        mOutput = (TextView) findViewById(R.id.text_output);

        Observable<OnTextChangeEvent> textChangeObservable = WidgetObservable.text(mUsername);

        mSubscription = AppObservable.bindActivity(this,
                textChangeObservable.debounce(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()))
                .subscribe(getSearchObserver());
    }

    private Observer<OnTextChangeEvent> getSearchObserver() {
        return new Observer<OnTextChangeEvent>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(OnTextChangeEvent onTextChangeEvent) {
                mOutput.setText("Searching for " + onTextChangeEvent.text().toString());
            }
        };
    }
}
