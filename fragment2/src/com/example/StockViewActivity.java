package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class StockViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sview);

        Intent launchingIntent = getIntent();
        String content = launchingIntent.getData().toString();

        WebView viewer = (WebView) findViewById(R.id.stockView);
        viewer.loadUrl(content);
    }
}
