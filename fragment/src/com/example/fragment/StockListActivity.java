package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.example.R;


/**
 * The activity always points to a layout that has the fragments in it.
 *
 * The fragment points to a layout that actually defines the layout within the fragment.
 */
public class StockListActivity  extends Activity implements StockListFragment.StockListFragmentListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * For portrait, listfrag has one fragment that points to StockListFragment
         *
         * For landscape, listfrag has two fragments in a linear layout. One points
         * to StockListFragment and the other points to StockViewFragment.
         */
        setContentView(R.layout.listfrag);


        /**
         * StockListFragment is clickable!
         */
    }

    public void selected(String url) {
        StockViewFragment viewer = (StockViewFragment) getFragmentManager()
                .findFragmentById(R.id.viewFrag);

        if (viewer == null || !viewer.isInLayout()) {
            Intent showContent = new Intent(getApplicationContext(),
                    StockViewActivity.class);
            showContent.setData(Uri.parse(url));
            startActivity(showContent);
        } else {
                viewer.updateUrl(url);
        }
    }
}
