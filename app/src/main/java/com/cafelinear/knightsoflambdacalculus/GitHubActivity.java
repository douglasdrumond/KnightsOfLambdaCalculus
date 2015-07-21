package com.cafelinear.knightsoflambdacalculus;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.cafelinear.knightsoflambdacalculus.retrofit.GithubApi;
import com.cafelinear.knightsoflambdacalculus.retrofit.User;

import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.String.format;

public class GitHubActivity extends Activity {

    private static final String LOG_TAG = GitHubActivity.class.getSimpleName();

    private EditText mUsername;

    private GithubApi mApi;

    private TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        mOutput = (TextView) findViewById(R.id.text_output);

        mApi = createGithubApi();

        mUsername = (EditText) findViewById(R.id.username);

        findViewById(R.id.button_rx).setOnClickListener(v -> mApi.user(mUsername.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.d(LOG_TAG, "onError " + e.getMessage());
                        mOutput.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(final User user) {
                        mOutput.setText(format("%s has %d followers", user.name, user.followers));
                    }
                }));
    }

    private GithubApi createGithubApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("https://api.github.com/");
        //.setLogLevel(RestAdapter.LogLevel.FULL);

        final String githubToken = getResources().getString(R.string.github_oauth_token);
        if (!TextUtils.isEmpty(githubToken)) {
            builder.setRequestInterceptor(
                    request -> request.addHeader("Authorization", format("token %s", githubToken)));
        }

        return builder.build().create(GithubApi.class);
    }
}
