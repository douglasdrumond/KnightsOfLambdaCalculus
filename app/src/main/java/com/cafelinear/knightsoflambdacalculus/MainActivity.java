package com.cafelinear.knightsoflambdacalculus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_simple).setOnClickListener(
                v -> startActivity(new Intent(this, HelloWorldActivity.class)));
        findViewById(R.id.button_simpler).setOnClickListener(
                v -> startActivity(new Intent(this, HelloWorld2Activity.class)));
        findViewById(R.id.button_operators).setOnClickListener(
                v -> startActivity(new Intent(this, OperatorsActivity.class)));
        findViewById(R.id.button_textwatcher).setOnClickListener(
                v -> startActivity(new Intent(this, TextWatcherActivity.class)));
        findViewById(R.id.button_github).setOnClickListener(
                v -> startActivity(new Intent(this, GitHubActivity.class)));
    }
}
