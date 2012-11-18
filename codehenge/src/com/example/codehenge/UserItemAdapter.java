package com.example.codehenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.R;

import java.util.ArrayList;
import java.util.List;

public class UserItemAdapter extends ArrayAdapter<UserRecord> {
    private List<UserRecord> users;

    public UserItemAdapter(Context context, int textViewResourceId, List<UserRecord> users) {
        super(context, textViewResourceId, users);
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.hlistitem, null);
        }

        UserRecord user = users.get(position);
        if (user != null) {
            TextView username = (TextView) v.findViewById(R.id.username);
//            TextView email = (TextView) v.findViewById(R.id.email);

            if (username != null) {
                username.setText(user.username);
            }

//            if(email != null) {
//                email.setText("Email: " + user.email );
//            }
        }
        return v;
    }
}
