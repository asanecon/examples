package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.R;

public class StockViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewfrag);

        Intent launchingIntent = getIntent();
        String content = launchingIntent.getData().toString();

        StockViewFragment viewer = (StockViewFragment) getFragmentManager()
                .findFragmentById(R.id.viewFrag);
        viewer.updateUrl(content);
    }
}
