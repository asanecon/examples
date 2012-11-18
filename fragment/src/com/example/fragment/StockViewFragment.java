package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.R;

public class StockViewFragment extends Fragment {
    WebView viewer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //Uses a WebView layout
        viewer = (WebView) inflater.inflate(R.layout.sview, container, false);
        viewer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return viewer;
    }

    public void updateUrl(String url) {
        if(viewer != null) {
            viewer.loadUrl(url);
        }
    }
}
