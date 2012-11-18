package com.example.codehenge;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.R;

import java.util.ArrayList;
import java.util.List;

public class HangeListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hlist);

        UserRecord ur1 = new UserRecord("Sarah", "sch@gl.com");
        UserRecord ur2 = new UserRecord("Aditya", "ak@ibm.com");

        List<UserRecord> items = new ArrayList<UserRecord>();
        items.add(ur1);
        items.add(ur2);

        UserItemAdapter adapter = new UserItemAdapter(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(adapter);
    }
}
