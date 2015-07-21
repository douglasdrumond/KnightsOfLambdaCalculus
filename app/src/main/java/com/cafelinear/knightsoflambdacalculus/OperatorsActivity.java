package com.cafelinear.knightsoflambdacalculus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;

public class OperatorsActivity extends Activity {

    private TextView mTextView;

    private Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> sub) {
            sub.onNext("World     ");
            sub.onCompleted();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        mTextView = (TextView) findViewById(R.id.text_output);
        findViewById(R.id.button_rx).setOnClickListener(v -> {
            myObservable.map(s -> "Hello " + s)
                    .map(String::trim)
                    .map(String::length)
                    .map(i -> Integer.toString(i))
                    .map(s -> "Chars = " + s)
                    .subscribe(mTextView::setText);
        });
    }
}
