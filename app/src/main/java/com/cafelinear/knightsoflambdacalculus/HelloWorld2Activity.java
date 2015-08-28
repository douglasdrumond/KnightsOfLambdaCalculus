package com.cafelinear.knightsoflambdacalculus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;

public class HelloWorld2Activity extends Activity {

    private TextView mTextView;

    private Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> sub) {
            sub.onNext("Test");
            sub.onCompleted();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        mTextView = (TextView) findViewById(R.id.text_output);
        findViewById(R.id.button_rx).setOnClickListener(v -> myObservable.subscribe(mTextView::setText));
    }
}
