package com.example.fragment;

import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.R;

public class StockListFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in stockName array and populate list.
        //Uses a text view.
        setListAdapter(ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.stockName,
                R.layout.list));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String[] links = getResources().getStringArray(R.array.stockValues);
        String content = links[position];
        ((StockListActivity)(getActivity())).selected(content);
    }

    public interface StockListFragmentListener {
        public void selected(String url);
    }
}
