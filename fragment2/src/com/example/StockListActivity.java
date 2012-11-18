package com.example;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class StockListActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(ArrayAdapter.createFromResource(getApplicationContext().getApplicationContext(),
                                                       R.array.stockName,
                                                       R.layout.list));

        final String[] links = getResources().getStringArray(R.array.stockValues);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String content = links[i];
                Intent showContent = new Intent(getApplicationContext(), StockViewActivity.class);
                showContent.setData(Uri.parse(content));
                startActivity(showContent);
            }
        });
    }
}
